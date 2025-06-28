import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import custom_library.TransactionalManager
import internal.GlobalVariable

WS.delay(3)

token = TransactionalManager.getBBDAuthToken()
order_id = TransactionalManager.getOrderID()

order_status = null
timeout = new Date().getTime() + (18000 * GlobalVariable.globalLoading)

while(order_status != 4 && new Date().getTime() < timeout) {
	order_detail = WS.sendRequest(findTestObject('Object Repository/Simulator/Order Detail (input - token, order_id)', [('token') : token, ('order_id'): order_id]))
	if(WS.getResponseStatusCode(order_detail) != 200) {
		KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(order_detail))
	} else {
		try {
			order_status = WS.getElementPropertyValue(order_detail, 'status')
			WS.comment("Order status: $order_status")
		} catch (Exception error) {
			KeywordUtil.markWarning("\n\nERROR: $error\n")
		}
	WS.delay(3)
	}
}

if (order_status != 4) {
	WS.comment("Order status: $order_status")
	KeywordUtil.markErrorAndStop("Order Status is not 4 (Completed) as expected. It is " + order_status)
	System.exit(0)
}



