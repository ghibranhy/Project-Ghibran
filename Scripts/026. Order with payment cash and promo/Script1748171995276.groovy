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


// Helper function: tunggu elemen hadir & enabled, lalu tap
def waitForReadyAndTap(TestObject to, int timeout = 5) {
	Mobile.waitForElementPresent(to, timeout)
	Mobile.waitForElementAttributeValue(to, 'enabled', 'true', timeout)
	Mobile.tap(to, timeout)
}

//Mobile.startExistingApplication('com.seatech.bluebird.regress')

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
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/textView.DestinationAddress (1)'), 10)
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
Mobile.tapAtPosition(363, 1830)
Mobile.delay(2)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/select.PaymentCash'))
Mobile.delay(2)
Mobile.takeScreenshoot()

// Select Promo
Mobile.tapAtPosition(901, 1830)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.OkayPromo'))


//// Create Order
//Mobile.tapAtPosition(769, 2124)
//Mobile.takeScreenshot()