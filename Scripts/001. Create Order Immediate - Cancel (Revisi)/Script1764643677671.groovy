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
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.util.KeywordUtil

KeywordUtil.logInfo("Launching application")
Mobile.startExistingApplication(GlobalVariable.APP_ID)

def waitForReadyAndTap(TestObject obj, int timeout = GlobalVariable.TIMEOUT_SHORT) {
    Mobile.waitForElementPresent(obj, timeout)
    Mobile.waitForElementAttributeValue(obj, 'enabled', 'true', timeout)
    Mobile.tap(obj, timeout)
}

KeywordUtil.logInfo("Opening Delivery menu")
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Home'))
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Delivery'))

KeywordUtil.logInfo("Setting pickup location: ${GlobalVariable.PICKUP_LOC_DEFAULT}")
TestObject pickupField = findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup')
waitForReadyAndTap(pickupField)

// stabilize focus
ErrorHandlingManager.doubledTap(pickupField, GlobalVariable.TIMEOUT_SHORT)

// clear existing text
TestObject clearBtn = findTestObject('Object Repository/Delivery/2. Search Location/button.ClearText')
Mobile.waitForElementPresent(clearBtn, GlobalVariable.TIMEOUT_SHORT)
Mobile.tap(clearBtn, GlobalVariable.TIMEOUT_SHORT)

// input pickup
Mobile.setText(
    findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Current location'),
    GlobalVariable.PICKUP_LOC_DEFAULT,
    GlobalVariable.TIMEOUT_SHORT
)
Mobile.hideKeyboard()

waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/TextView_PickUpLocationUpdate'))
KeywordUtil.markPassed("Pickup location selected")

KeywordUtil.logInfo("Setting destination: ${GlobalVariable.DEST_LOC_DEFAULT}")
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Deliver to'), 5)
Mobile.setText(
    findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Deliver to'),
    GlobalVariable.DEST_LOC_DEFAULT,
    GlobalVariable.TIMEOUT_SHORT
)
Mobile.hideKeyboard()

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/TextView_DestinationLocation'), 5)
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/TextView_DestinationLocation'), 5)
KeywordUtil.markPassed("Destination selected")

KeywordUtil.logInfo("Filling sender details")
waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.PackageType'))

if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), 3, FailureHandling.OPTIONAL)) {
    Mobile.tap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), 3)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.Continue.Sender'))

KeywordUtil.logInfo("Filling recipient details")
if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), 3, FailureHandling.OPTIONAL)) {
    Mobile.tap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), 3)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.Continue.Recipient'))

Mobile.delay(5) // small wait for transition

KeywordUtil.logInfo("Submitting order")
Mobile.tapAtPosition(769, 2124) // temporary workaround

KeywordUtil.logInfo("Cleaning up: cancelling order")
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelCreate'), 2)
Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelCreate'), GlobalVariable.TIMEOUT_SHORT)

Mobile.takeScreenshot()
KeywordUtil.markPassed("Test completed")