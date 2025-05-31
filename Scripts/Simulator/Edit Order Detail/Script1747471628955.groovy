import java.text.SimpleDateFormat

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import custom_library.TransactionalManager
import internal.GlobalVariable

///**
//* the class below is modified from the source:
//* https://docs.katalon.com/docs/create-tests/test-objects/api-test-objects/rest-request/create-rest-api-requests-manually-in-katalon-studio
//*/
//
//// Class for Get Order Detail
//public class GetRequestObject {
//	String endpoint = "https://regressapi.bluebird.id/order/getdetail/${TransactionalManager.getOrderID()}"
//	String requestMethod = "GET"
//	String authHeader = "bearer ${TransactionalManager.getBBDAuthToken()}"
//	 
//	TestObjectProperty header1 = new TestObjectProperty("Authorization", ConditionType.EQUALS, authHeader)
//	TestObjectProperty header2 = new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json")
//	TestObjectProperty header3 = new TestObjectProperty("Accept", ConditionType.EQUALS, "application/json")
//	ArrayList defaultHeaders = Arrays.asList(header1, header2, header3)
//	 
//	String body = '{}'
//	 
//	public ResponseObject buildApiRequest() {
//		RequestObject ro = new RequestObject("objectId")
//		ro.setRestUrl(endpoint)
//		ro.setHttpHeaderProperties(defaultHeaders)
//		ro.setRestRequestMethod(requestMethod)
//		 
//		ResponseObject respObj = WS.sendRequest(ro)
//		return respObj
//	}
//}
//
//// Get Order Detail by Order ID
//GetRequestObject get_req = new GetRequestObject()
//ResponseObject get_resp = get_req.buildApiRequest()
//
//session_id = TransactionalManager.getBBDSimulatorSession()
//
//original_resp_body = get_resp.getResponseBodyContent()
//
//// Edit Order Detail
//original_time_pickup = WS.getElementPropertyValue(get_resp, 'time_pickup')
//WS.comment("original_time_pickup: $original_time_pickup")
//edited_time_pickup = Long.valueOf(original_time_pickup+'000')
//final_time_pickup = (long)edited_time_pickup-25200000
//WS.comment("final_time_pickup: $final_time_pickup")
//Date date_object =  new Date((final_time_pickup))
//def clean_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date_object)
//WS.comment("clean_date: $clean_date")
//
////edited_resp_body = original_resp_body.replace('"instruction":"",', '"instruction":"trafficsimulator:'+session_id+'",')
////final_resp_body = edited_resp_body.replace('"time_pickup":"'+original_time_pickup+'",', '"time_pickup":"'+clean_date+'.000Z",')

// Class for Edit Order Detail
public class PostRequestObject {
	String endpoint = "https://regressapi4.bluebird.id/simulator-director/invalid-session"
	String requestMethod = "POST"

	TestObjectProperty headerContentType = new TestObjectProperty("Content-Type", ConditionType.EQUALS, "text/plain")
	ArrayList<TestObjectProperty> headers = Arrays.asList(headerContentType)

	public ResponseObject buildApiRequest(String body) {
		RequestObject ro = new RequestObject("objectId")
		ro.setRestUrl(endpoint)
		ro.setHttpHeaderProperties(headers)
		ro.setRestRequestMethod(requestMethod)
		ro.setBodyContent(new HttpTextBodyContent(body, "UTF-8", "text/plain"))

		ResponseObject respObj = WS.sendRequest(ro)
		return respObj
	}
}

// Contoh penggunaan:
PostRequestObject post_req = new PostRequestObject()

String final_resp_body = '''
{
    "session_id": 9396
}
'''

ResponseObject post_resp = post_req.buildApiRequest(final_resp_body)

if (post_resp.getStatusCode() != 200 || post_resp.getResponseBodyContent() != '{}') {
	KeywordUtil.markFailed("Status code is not 200 as expected. It is " + post_resp.getStatusCode())
	KeywordUtil.markErrorAndStop("Response body content is " + post_resp.getResponseBodyContent())
}
