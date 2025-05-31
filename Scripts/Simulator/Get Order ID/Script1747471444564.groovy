import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import custom_library.TransactionalManager
import com.kms.katalon.core.util.KeywordUtil

def order_id
def job_id
def resp
//def vehicle_type = vehicle_type.toLowerCase()
//String test_case_name = test_case_name
KeywordUtil.logInfo("Test Case Name: " + test_case_name)


/* get job_id from on-going order */
if (test_case_name.contains('schedule'.toLowerCase())) {
	resp = WS.sendRequest(findTestObject('Object Repository/Simulator/On Going Order, Schedule',
		[
			'token':TransactionalManager.getMyBBToken(),
			'app_version':GlobalVariable.mybb_app_version
		]))
} else {
	resp = WS.sendRequest(findTestObject('Object Repository/Simulator/On Going Order',
	[
		'token':TransactionalManager.getMyBBToken(),
		'app_version':GlobalVariable.mybb_app_version
	]))
}

if (WS.getResponseStatusCode(resp) != 200 && WS.getResponseStatusCode(resp) != 201) {
	KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
} else {
	try {
		order_id = WS.getElementPropertyValue(resp, 'data.records[0].order_id')
		order_id_str = order_id.toString()
		WS.comment("order_id_str:"+order_id_str)
		job_id = WS.getElementPropertyValue(resp, 'data.records[0].job_id')
		TransactionalManager.setOrderIDMybb(order_id_str)
		TransactionalManager.setOrderID(job_id)
	}  catch (Exception error) {
		KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
	} 
}
/*
//get external_order_id from order_detail mybb
order_detail = WS.sendRequest(findTestObject('Object Repository/MyBB/_API/Get Order Id',
	[
		'order_id': order_id,
		'token': TransactionalManager.getMyBBToken(),
		'app_version': GlobalVariable.mybb_app_version
	]))

if (WS.getResponseStatusCode(order_detail) != 200 && WS.getResponseStatusCode(order_detail) != 201) {
	KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(order_detail))
} else {
	try {
		//calculate to create job id bbd
		external_order_id = WS.getElementPropertyValue(order_detail, 'data.external_order_id')
		KeywordUtil.logInfo('External Order Id: ' + external_order_id)
		job_id = external_order_id + 9000000000
		KeywordUtil.logInfo('Job Id: ' + job_id)
		TransactionalManager.setOrderID(job_id.toString())
		
	} catch (Exception error) {
		KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
	}
}

if (!(vehicle_type.contains('delivery bb')) && !(vehicle_type.contains('ezpay'))) {

//Memastikan job id sama dengan orderan yg terbaru dengan mencocokan session id simulator
order_detail_bbd = WS.sendRequest(findTestObject('Object Repository/BB Dispatch/_ API/Order Detail (input - token, order_id)',
	[
		'order_id': job_id,
		'token': TransactionalManager.getBBDAuthToken(),
	]))

if (WS.getResponseStatusCode(order_detail_bbd) != 200 && WS.getResponseStatusCode(order_detail_bbd) != 201) {
	KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(order_detail_bbd))
} else {
		try {
		//calculate to create job id bbd
		pickup_instruction = WS.getElementPropertyValue(order_detail_bbd, 'instruction')
		KeywordUtil.logInfo('Pickup Instraction is: ' + pickup_instruction)
		
		if (pickup_instruction.contains(TransactionalManager.getBBDSimulatorSession())) {
			KeywordUtil.markPassed('TRUE, BBD order id is same as mybb order id')
			KeywordUtil.logInfo('TRUE, BBD order id is the same as the mybb order id')
		} else {
			KeywordUtil.markFailedAndStop('FALSE, BBD order id is not same as mybb order id')
		}
	
	} catch (Exception error) {
		KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
	}
}
}
*/