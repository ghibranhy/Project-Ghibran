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

//Mobile.startExistingApplication('com.seatech.bluebird.regress')

// Helper function: tunggu elemen hadir & enabled, lalu tap
def waitForReadyAndTap(TestObject to, int timeout = 5) {
	Mobile.waitForElementPresent(to, timeout)
	Mobile.waitForElementAttributeValue(to, 'enabled', 'true', timeout)
	Mobile.tap(to, timeout)
}

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

//set schedule
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.Schedule'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/button.Schedule'), 3)

left_position = Mobile.getElementLeftPosition(findTestObject('Object Repository/Delivery/5. Confirmation Page/Schedule/textView.HourlyTime'), 5)
top_position = Mobile.getElementTopPosition(findTestObject('Object Repository/Delivery/5. Confirmation Page/Schedule/textView.HourlyTime'), 5)
Mobile.swipe(left_position, top_position, left_position, top_position-100)

left_position = Mobile.getElementLeftPosition(findTestObject('Object Repository/Delivery/5. Confirmation Page/Schedule/button.NumberPicker'), 3)
top_position = Mobile.getElementTopPosition(findTestObject('Object Repository/Delivery/5. Confirmation Page/Schedule/button.NumberPicker'), 3)
Mobile.swipe(left_position, top_position, left_position, top_position-200)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Schedule/button.SaveSchedule'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Schedule/button.SaveSchedule'), 5)
Mobile.delay (3)

Mobile.tapAtPosition(769, 2124)
Mobile.takeScreenshot()






