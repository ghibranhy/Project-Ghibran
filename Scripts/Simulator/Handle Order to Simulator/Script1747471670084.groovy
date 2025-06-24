import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonSlurper
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import custom_library.TransactionalManager
import custom_library.SimulatorDataManager
import internal.GlobalVariable

// =============================================
// 🔐 Ambil token auth dari endpoint BBD
// =============================================

RequestObject authRequest = new RequestObject()
authRequest.setRestUrl('https://regressapi.bluebird.id/token/auth')
authRequest.setRestRequestMethod('POST')
authRequest.setHttpHeaderProperties([
	new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json")
])
String body = '''{
	"clientid": "https://regressapi.bluebird.id",
	"response_type": "id_token",
	"scope": "openid",
	"user_id": "superjkt",
	"user_secret": "superjkt"
}'''
authRequest.setBodyContent(new HttpTextBodyContent(body, "UTF-8", "application/json"))

ResponseObject authResp = WS.sendRequest(authRequest)
if (WS.getResponseStatusCode(authResp) != 200) {
	KeywordUtil.markFailedAndStop("❌ Gagal mendapatkan BBD Token. Status Code: " + WS.getResponseStatusCode(authResp))
}

def parsedResp = new JsonSlurper().parseText(authResp.getResponseBodyContent())
String token = parsedResp.id_token
if (!token) {
	KeywordUtil.markFailedAndStop("❌ id_token tidak ditemukan di response.")
}

GlobalVariable.bbdAuthToken = token
WS.comment("✅ BBD Token berhasil diambil dan disimpan.")

// Ambil Job ID dari TransactionalManager

String job_id = TransactionalManager.getOrderID()
if (!job_id || job_id.toString().isEmpty()) {
	KeywordUtil.markFailedAndStop("❌ Job ID kosong.")
}

long timeoutLimit = new Date().getTime() + 120000
String status = null

WS.comment("🔄 Cek status order ${job_id}...")

while (new Date().getTime() < timeoutLimit) {
	WS.delay(5)

	ResponseObject orderDetailResp = WS.sendRequest(findTestObject(
		'Object Repository/Simulator/Order Detail (input - token, order_id)',
		[('token') : token, ('order_id') : job_id]
	))

	int statusCode = WS.getResponseStatusCode(orderDetailResp)
	if (statusCode != 200) {
		KeywordUtil.markWarning("❌ Status code bukan 200: ${statusCode}")
		continue
	}

	status = WS.getElementPropertyValue(orderDetailResp, 'status')
	WS.comment("ℹ️ Status sekarang: ${status}")

	if (status == '-4') {
		WS.comment("⌛ Order timeout. Lanjut ke Direct Assign.")
		WS.callTestCase(findTestCase('Test Cases/Simulator/Direct Assign'), [:], FailureHandling.STOP_ON_FAILURE)
		break
	} else if (status == '2') {
		WS.comment("✅ Order sudah dapat driver.")
		break
	} else if (status == '6' || status == '1') {
		WS.comment("⏳ Order masih dalam proses.")
		// lanjut loop
	} else {
		KeywordUtil.markWarning("❗ Order dalam status tidak dikenali: ${status}")
		break
	}
}

if (status != '2') {
	KeywordUtil.markFailed("❌ Order belum dapat driver. Final status: ${status}")
}