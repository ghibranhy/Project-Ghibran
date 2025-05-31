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
import custom_library.ErrorHandlingManager

Mobile.startExistingApplication('com.seatech.bluebird.regress')

// Helper function: tunggu elemen hadir & enabled, lalu tap
def waitForReadyAndTap(TestObject to, int timeout = 5) {
	Mobile.waitForElementPresent(to, timeout)
	Mobile.waitForElementAttributeValue(to, 'enabled', 'true', timeout)
	Mobile.tap(to, timeout)
}

Mobile.startExistingApplication('com.seatech.bluebird.regress')

// Homepage
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Home'))
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Delivery'))

// Search Location
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'))

// Double tap pake custom ErrorHandlingManager
ErrorHandlingManager.doubledWaitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), 5)
ErrorHandlingManager.doubledTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), 5)

// Optional clear text
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/button.ClearText'), 3, FailureHandling.OPTIONAL)

// Isi Pickup Location
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/button.EditPickup'))
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/button.EditPickup'), 'miesol kosambi', 3)
Mobile.hideKeyboard()
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/textView.PickUpAddress (1)'))

// Isi Destination Location
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/button.EditDestination'), 'smp negeri 120 jakarta', 3)
Mobile.hideKeyboard()
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/textView.DestinationAddress (1)'), 5)
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/textView.DestinationAddress (1)'), 5)

// Sender Detail
waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.PackageType'))

if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), 5)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.Continue.Sender'))

// Recipient Detail
if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), 5)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.Continue.Recipient'))
Mobile.delay(5) 

// Select Payment
//Mobile.tapAtPosition(363, 1830)
//Mobile.delay(2)
//waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/selectPayment.Visa'))
//Mobile.delay(3)

// Create Order
Mobile.tapAtPosition(769, 2124)
Mobile.takeScreenshot()

//On Trip
def expected_status = 'start'
Mobile.callTestCase(findTestCase('Test Cases/Simulator/Set Status Simulator'), 
                   ['expected_status': expected_status], FailureHandling.STOP_ON_FAILURE)

// Ubah status ke 'complete' dan panggil kembali test case simulator
expected_status = 'complete'
Mobile.callTestCase(findTestCase('Test Cases/Simulator/Set Status Simulator'), 
                   ['expected_status': expected_status], FailureHandling.STOP_ON_FAILURE)


WS.delay(3)

token = TransactionalManager.getBBDAuthToken()
order_id = TransactionalManager.getOrderID()

order_status = null
KeywordUtil.logInfo("GlobalVariable.globalLoading: " + GlobalVariable.globalLoading)
long timeout = new Date().getTime() + (9000 * (GlobalVariable.globalLoading ?: 1))

while(order_status != 4 && new Date().getTime() < timeout) {
	order_detail = WS.sendRequest(findTestObject('BB Dispatch/_ API/Order Detail (input - token, order_id)', [('token') : token, ('order_id'): order_id]))
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

Mobile.pressHome()




