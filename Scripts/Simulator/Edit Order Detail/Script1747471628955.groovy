import java.text.SimpleDateFormat

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import custom_library.TransactionalManager
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


///**
//* the class below is modified from the source:
//* https://docs.katalon.com/docs/create-tests/test-objects/api-test-objects/rest-request/create-rest-api-requests-manually-in-katalon-studio
//*/

// Class for Get Order Detail
public class GetRequestObject {
	String endpoint = "${GlobalVariable.envBbd1}/order/getdetail/${TransactionalManager.getOrderID()}"
	String requestMethod = "GET"
	String authHeader = "bearer ${TransactionalManager.getBBDAuthToken()}"
	 
	TestObjectProperty header1 = new TestObjectProperty("Authorization", ConditionType.EQUALS, authHeader)
	TestObjectProperty header2 = new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json")
	TestObjectProperty header3 = new TestObjectProperty("Accept", ConditionType.EQUALS, "application/json")
	ArrayList defaultHeaders = Arrays.asList(header1, header2, header3)
	 
	String body = '{}'
	 
	public ResponseObject buildApiRequest() {
		RequestObject ro = new RequestObject("objectId")
		ro.setRestUrl(endpoint)
		ro.setHttpHeaderProperties(defaultHeaders)
		ro.setRestRequestMethod(requestMethod)
		 
		ResponseObject respObj = WS.sendRequest(ro)
		return respObj
	}
}

// Get Order Detail by Order ID
GetRequestObject get_req = new GetRequestObject()
ResponseObject get_resp = get_req.buildApiRequest()

session_id = TransactionalManager.getBBDSimulatorSession()

original_resp_body = get_resp.getResponseBodyContent()

// Edit Order Detail
original_time_pickup = WS.getElementPropertyValue(get_resp, 'time_pickup')
WS.comment("original_time_pickup: $original_time_pickup")
edited_time_pickup = Long.valueOf(original_time_pickup+'000')
final_time_pickup = (long)edited_time_pickup-25200000
WS.comment("final_time_pickup: $final_time_pickup")
Date date_object =  new Date((final_time_pickup))
def clean_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date_object)
WS.comment("clean_date: $clean_date")

WS.comment("SESSION ID from TransactionalManager: " + session_id)
edited_resp_body = original_resp_body.replace('"instruction":"",', '"instruction":"trafficsimulator:'+session_id+'",')
final_resp_body = edited_resp_body.replace('"time_pickup":"'+original_time_pickup+'",', '"time_pickup":"'+clean_date+'.000Z",')

// Class for Edit Order Detail
public class PostRequestObject {
	String endpoint = "${GlobalVariable.envBbd1}/order/edit"
	String requestMethod = "POST"
	String authHeader = "bearer ${TransactionalManager.getBBDAuthToken()}"
	 
	TestObjectProperty header1 = new TestObjectProperty("Authorization", ConditionType.EQUALS, authHeader)
	TestObjectProperty header2 = new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json")
	TestObjectProperty header3 = new TestObjectProperty("Accept", ConditionType.EQUALS, "application/json")
	ArrayList defaultHeaders = Arrays.asList(header1, header2, header3)
	 
	public ResponseObject buildApiRequest(String body) {
		RequestObject ro = new RequestObject("objectId")
		ro.setRestUrl(endpoint)
		ro.setHttpHeaderProperties(defaultHeaders)
		ro.setRestRequestMethod(requestMethod)
		ro.setBodyContent(new HttpTextBodyContent(body))
		 
		ResponseObject respObj = WS.sendRequest(ro)
		return respObj
	}
}

// Post Edited Order Detail
PostRequestObject post_req = new PostRequestObject()
ResponseObject post_resp = post_req.buildApiRequest(final_resp_body)

if (post_resp.getStatusCode() != 200 || post_resp.getResponseBodyContent() != '{}') {
	KeywordUtil.markFailed("Status code is not 200 as expected. It is " + post_resp.getStatusCode())
	KeywordUtil.markErrorAndStop("Response body content is " + post_resp.getResponseBodyContent())
}
