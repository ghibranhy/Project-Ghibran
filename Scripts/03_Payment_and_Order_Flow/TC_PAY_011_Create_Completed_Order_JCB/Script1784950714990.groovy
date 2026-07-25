import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import custom_library.ErrorHandlingManager
import custom_library.MobileHelper
import custom_library.TransactionalManager
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC030
 * Test Name      : 030. Create completed order with JCB
 * Description    : Testing flow pemesanan pengiriman dengan metode pembayaran JCB hingga status transaksi diselesaikan via simulator.
 * Pre-condition  : Berada di Homepage aplikasi MyBluebird.
 */

def waitForReadyAndTap(TestObject testObject, int timeout = GlobalVariable.TIMEOUT_SHORT) {
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.tap(testObject, timeout)
}

Mobile.delay(5)

// 1. Navigation to Delivery Module
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Home'), GlobalVariable.TIMEOUT_SHORT)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Delivery'), GlobalVariable.TIMEOUT_SHORT)

// 2. Location Process
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), GlobalVariable.TIMEOUT_SHORT)
ErrorHandlingManager.doubledTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(1)

if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/buttonClearTeks'), 2, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/buttonClearTeks'), GlobalVariable.TIMEOUT_SHORT, FailureHandling.OPTIONAL)
	Mobile.delay(1)
}

Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Current location'), GlobalVariable.PICKUP_LOC_DEFAULT, GlobalVariable.TIMEOUT_SHORT)
Mobile.hideKeyboard(FailureHandling.OPTIONAL)
Mobile.delay(1)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/TextViewPickUpLocation'), GlobalVariable.TIMEOUT_SHORT)

waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Deliver to'), GlobalVariable.TIMEOUT_SHORT)
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Deliver to'), GlobalVariable.DEST_LOC_DEFAULT, GlobalVariable.TIMEOUT_SHORT)
Mobile.hideKeyboard(FailureHandling.OPTIONAL)
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/TextView_DestinationLocation'), GlobalVariable.TIMEOUT_SHORT)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/TextView_DestinationLocation'), GlobalVariable.TIMEOUT_SHORT)

// 3. Sender Detail
Mobile.delay(2)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.PackageType'), GlobalVariable.TIMEOUT_SHORT)
if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), GlobalVariable.TIMEOUT_SHORT)
}
waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.Continue.Sender'), GlobalVariable.TIMEOUT_SHORT)

// 4. Recipient Detail
if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), GlobalVariable.TIMEOUT_SHORT)
}
waitForReadyAndTap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.Continue.Recipient'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(5)

// 5. Select Payment JCB
MobileHelper.tapPaymentOption()
Mobile.delay(2)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/selectPaymentJCB'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(2)

// 6. Submit Order
MobileHelper.tapCreateOrderButton()
Mobile.delay(8)

// 7. Get Token & Order ID
String testCaseName = TransactionalManager.getMyBBTestCaseName()?.toLowerCase() ?: ''
String vehicleType  = 'Delivery BB'

WS.callTestCase(findTestCase('Test Cases/Simulator/Get Token'), ['vehicle_type': vehicleType], FailureHandling.STOP_ON_FAILURE)
WS.callTestCase(findTestCase('Test Cases/Simulator/Get Order ID'), ['vehicle_type': vehicleType, 'test_case_name': testCaseName], FailureHandling.STOP_ON_FAILURE)

// 8. Update Status Simulator (Credit Card = 2)
Mobile.comment("Mengubah status orderan: ENROUTE (1)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status Enroute'), ['epay_value': '2'], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(6)

Mobile.comment("Mengubah status orderan: ARRIVED (4)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status Arrived'), ['epay_value': '2'], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(3)

Mobile.comment("Mengubah status orderan: ON TRIP (6)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status On Trip'), ['epay_value': '2'], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(5)

Mobile.comment("Mengubah status orderan: COMPLETE NON CASH (8)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status Complete Order Non Cash'), ['epay_value': '2'], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(5)

Mobile.takeScreenshot()