import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import custom_library.TransactionalManager
import parameters.AccountRelated
import parameters.OrderRelated

String expected_driver = TransactionalManager.getExpectedAndroidDriver()
String vehicle_type_info = expected_driver.split(' ')[1]

/**
* the code below is modified from the source:
* https://docs.katalon.com/docs/create-tests/test-objects/api-test-objects/rest-request/create-rest-api-requests-manually-in-katalon-studio
*/
public class CustomRequestObject {
	String endpoint = "https://regressapi.bluebird.id/order/assignspecific"
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

token = TransactionalManager.getBBDAuthToken()
String vehicle_no
String driver_id
if (vehicle_type_info == 'BB') {
	vehicle_no = OrderRelated.MyBB_BluebirdTaxiNumber()
	driver_id = AccountRelated.DA_BB_LoginNip()
} else if (vehicle_type_info == 'SB') {
	vehicle_no = OrderRelated.MyBB_SilverbirdTaxiNumber()
	driver_id = AccountRelated.DA_SB_LoginNip()
}

vehicle_detail = WS.sendRequest(findTestObject('Object Repository/Simulator/Vehicle Detail (input - token, nomor_lambung)', [('token') : token, ('vehicle_no'): vehicle_no]))
if(WS.getResponseStatusCode(vehicle_detail) != 200) {
	KeywordUtil.markFailed("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(vehicle_detail))
} else {
	try {
		vehicle_id = WS.getElementPropertyValue(vehicle_detail, 'vehicle_id')
		vehicle_type = WS.getElementPropertyValue(vehicle_detail, 'vehicle_type')
		String body = """{"order_id": ${TransactionalManager.getOrderID()},"vehicles": {"vehicle_no": "$vehicle_no","vehicle_id": $vehicle_id,"vehicle_type": $vehicle_type,"driver_id": "$driver_id"}}"""
		CustomRequestObject req = new CustomRequestObject()
		ResponseObject resp = req.buildApiRequest(body)
		if(resp.getStatusCode() != 200) {
			KeywordUtil.markFailed("Status code is not 200 as expected. It is " + resp.getStatusCode())
		}
	} catch (Exception error) {
		KeywordUtil.markFailed("\n\nERROR: $error\n")
	}
}


