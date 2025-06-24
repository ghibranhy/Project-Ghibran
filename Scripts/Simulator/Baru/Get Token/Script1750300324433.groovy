import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import custom_library.TransactionalManager
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable as GlobalVariable
import internal.GlobalVariable

mybb_version = GlobalVariable.mybb_app_version
mobile_phone = GlobalVariable.activePhoneNumber
password = GlobalVariable.activePassword
token = ''

// Step 1: Validate User
resp = WS.sendRequest(findTestObject('Object Repository/Simulator/Validate User', 
	[
		'phone_number': mobile_phone,
		'app_version': mybb_version
	]))

if (WS.getResponseStatusCode(resp) != 200) {
	KeywordUtil.markErrorAndStop("❌ Status code bukan 200. Respon: ${WS.getResponseStatusCode(resp)}")
} else {
	try {
		token = WS.getElementPropertyValue(resp, 'data.session_token')
		WS.comment("Session Token: ${token}")
	} catch (Exception e) {
		KeywordUtil.markErrorAndStop("\n\nERROR saat ambil session_token: $e\n")
	}
}

// Step 2: Auth
resp_auth = WS.sendRequest(findTestObject('Object Repository/Simulator/Auth',
	[
		'password': password,
		'app_version': mybb_version,
		'session_token': token
	]))

if (WS.getResponseStatusCode(resp_auth) != 200) {
	KeywordUtil.markErrorAndStop("❌ Status code bukan 200. Respon: ${WS.getResponseStatusCode(resp_auth)}")
} else {
	try {
		token = WS.getElementPropertyValue(resp_auth, 'data.token')
		TransactionalManager.setMyBBToken(token)
		WS.comment("✅ Final Token: ${token}")

		bbid = WS.getElementPropertyValue(resp_auth, 'data.user_id')
		TransactionalManager.setMyBBID(bbid)
		WS.comment("📱 BB ID: ${bbid}")

		customer_name = WS.getElementPropertyValue(resp_auth, 'data.name')
		WS.comment("👤 Nama Customer: ${customer_name}")
	} catch (Exception e) {
		KeywordUtil.markErrorAndStop("\n\nERROR saat ambil data auth: $e\n")
	}
}
