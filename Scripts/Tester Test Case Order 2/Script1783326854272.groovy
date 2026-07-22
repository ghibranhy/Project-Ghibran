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
import custom_library.TransactionalManager
import internal.GlobalVariable

Mobile.startExistingApplication('com.seatech.bluebird.regress')


// Helper function: tunggu elemen hadir & enabled, lalu tap
def waitForReadyAndTap(TestObject to, int timeout = 5) {
	Mobile.waitForElementPresent(to, timeout)
	Mobile.waitForElementAttributeValue(to, 'enabled', 'true', timeout)
	Mobile.tap(to, timeout)
}

// Homepage
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Home'), 3)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Delivery'), 3)

// Search Location
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), 3)

// Double tap pake custom ErrorHandlingManager
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), 3)
ErrorHandlingManager.doubledTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), 5)
Mobile.delay(2)

//clear text
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/buttonClearTeks'), 3,)
Mobile.delay(2)

// Isi Pickup Location
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Current location'), 'bandara city mall', 3)
Mobile.hideKeyboard()
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/TextViewPickUpLocation'))

//Isi Destination Location
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Deliver to'), 5)
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Deliver to'), 'smp negeri 120 jakarta', 3)
Mobile.hideKeyboard()
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/TextView_DestinationLocation'), 5)
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/TextView_DestinationLocation'), 5)

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
//waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/select.PaymentVisa'))
//Mobile.delay(3)

// Create Order
Mobile.tapAtPosition(769, 2124)
Mobile.delay (5)

String test_case_name = TransactionalManager.getMyBBTestCaseName()?.toLowerCase() ?: ''
String folder_case_name = TransactionalManager.getMyBBFolderCaseName()?.toLowerCase() ?: ''
String vehicle_type

vehicle_type = vehicle_type ?: 'Delivery BB' // default fallback

WS.callTestCase(findTestCase('Test Cases/Simulator/Baru/Get Token'), ['vehicle_type':vehicle_type], FailureHandling.STOP_ON_FAILURE)

WS.callTestCase(findTestCase('Test Cases/Simulator/Get Order ID'), ['vehicle_type':vehicle_type,'test_case_name':test_case_name], FailureHandling.STOP_ON_FAILURE)

// 1. Jalankan State Enroute (Mobil Jalan)
Mobile.comment("Mengubah status orderan: ENROUTE (1)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Baru/Set Status Enroute'), [:], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(6) // Beri waktu apps mendeteksi socket state terbaru

// 2. Jalankan State Arrived (Mobil Sampai)
Mobile.comment("Mengubah status orderan: ARRIVED (4)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Baru/Set Status Arrived'), [:], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(6)

// 3. Jalankan State On Trip (Perjalanan Dimulai)
Mobile.comment("Mengubah status orderan: ON TRIP (6)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Baru/Set Status On Trip'), [:], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(5)

// 4. Selesaikan Orderan (Complete Cash)
Mobile.comment("Mengubah status orderan: COMPLETE CASH (7)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Baru/Set Status Complete Order Cash'), [:], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(5)

waitForReadyAndTap(findTestObject('Object Repository/Delivery/7. Complete Order/buttonClose'))