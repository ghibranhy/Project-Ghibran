import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import custom_library.TransactionalManager
import custom_library.SimulatorDataManager
import internal.GlobalVariable

String vehicle_type = vehicle_type

WS.delay(3)

token = TransactionalManager.getBBDAuthToken()
job_id = TransactionalManager.getOrderID()

order_status = null
timeout = new Date().getTime() + (16000 * GlobalVariable.globalLoading)
//retry_count = 0 //0 for First Timeout Order and 1 for Second Timeout Order

while(order_status != 2 && new Date().getTime() < timeout) {
	WS.delay(1)
	order_detail = WS.sendRequest(findTestObject('Object Repository/Simulator/Order Detail (input - token, order_id)', [('token') : token, ('order_id'): job_id]))
	order_status = WS.getElementPropertyValue(order_detail, 'status')
	WS.comment("Order status: $order_status")
	if (WS.getResponseStatusCode(order_detail) != 200) {
		KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(order_detail))
	} else {
		try {
			order_status = WS.getElementPropertyValue(order_detail, 'status')
			WS.comment("Order status: $order_status")		
			if (order_status == 1) { //On Bidding
				WS.comment('continue')
//			} else if (order_status == 2 || order_status == 3) { //En Route & On Trip
			} else if (order_status == 2) { //En Route 
				break
			} else if (order_status == -4){ //Time Out
				WS.callTestCase(findTestCase('Test Cases/Simulator/Direct Assign'), [:], FailureHandling.STOP_ON_FAILURE)
				WS.comment('continue')
			} else if (order_status == 6){ //On Assign
				WS.comment('continue')
			} else {
				KeywordUtil.markErrorAndStop("Order status is: $order_status")
				break
			}
		} catch (Exception error) {
			KeywordUtil.markWarning("\n\nERROR: $error\n")
		}
	}
}

Mobile.delay(3)
order_detail = WS.sendRequest(findTestObject('Object Repository/Simulator/Order Detail (input - token, order_id)', [('token') : token, ('order_id'): job_id]))
if (WS.getResponseStatusCode(order_detail) != 200) {
	KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(order_detail))
} else {
	order_status = WS.getElementPropertyValue(order_detail, 'status')
	WS.comment("Order status: $order_status")
}

//if (order_status != 2 && order_status != 3) {	
if (order_status != 2) {
	WS.comment("Order status: $order_status")
	KeywordUtil.markError("Order Status is not as expected. It is " + order_status)
}