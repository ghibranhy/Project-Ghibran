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

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.Home'), 10)
Mobile.tap(findTestObject('Object Repository/Delivery/button.Home'), 5)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.Order.Delivery'), 3)
Mobile.waitForElementAttributeValue(findTestObject('Object Repository/Delivery/button.Order.Delivery'),'enabled', 'true', 3)
Mobile.tap(findTestObject('Object Repository/Delivery/button.Order.Delivery'), 5)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.Input.Pickup'), 3)
Mobile.verifyElementExist(findTestObject('Object Repository/Delivery/button.Input.Pickup'), 5)
Mobile.tap(findTestObject('Object Repository/Delivery/button.Input.Pickup'), 5)
Mobile.takeScreenshot()

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.Input.Pickup'), 5)
Mobile.tap(findTestObject('Object Repository/Delivery/button.Input.Pickup'), 5)
ErrorHandlingManager.doubledWaitForElementPresent(findTestObject('Object Repository/Delivery/button.Input.Pickup'), 5)
ErrorHandlingManager.doubledTap(findTestObject('Object Repository/Delivery/button.Input.Pickup'), 5)
Mobile.tap(findTestObject('Object Repository/Delivery/button.ClearText'), 2, FailureHandling.OPTIONAL)
//Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.ClearText'), 5)
//Mobile.tap(findTestObject('Object Repository/Delivery/button.ClearText'), 5)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.EditPickup'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/button.EditPickup'), 5)
Mobile.setText(findTestObject('Object Repository/Delivery/button.EditPickup'), 'KFC Shell Mampang', 3)
Mobile.hideKeyboard()

Mobile.tap(findTestObject('Object Repository/Delivery/textView.PickupAddress'), 5)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.EditDestination'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/button.EditDestination'), 5)
Mobile.setText(findTestObject('Object Repository/Delivery/button.EditDestination'), 'hokben mampang', 3)
Mobile.hideKeyboard()

Mobile.tap(findTestObject('Object Repository/Delivery/textView.DestinationAddress'), 5)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.PackageType'), 3)
Mobile.waitForElementAttributeValue(findTestObject('Object Repository/Delivery/button.PackageType'),'enabled', 'true', 3)
Mobile.tap(findTestObject('Object Repository/Delivery/button.PackageType'), 5)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.UseMyContact.Sender'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/button.UseMyContact.Sender'), 5)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.Continue.Sender'), 3)
Mobile.waitForElementAttributeValue(findTestObject('Object Repository/Delivery/button.Continue.Sender'),'enabled', 'true', 3)
Mobile.tap(findTestObject('Object Repository/Delivery/button.Continue.Sender'), 5)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.UseMyContact.Recipient'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/button.UseMyContact.Recipient'), 5)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.Continue.Recipient'), 3)
Mobile.waitForElementAttributeValue(findTestObject('Object Repository/Delivery/button.Continue.Recipient'),'enabled', 'true', 3)
Mobile.tap(findTestObject('Object Repository/Delivery/button.Continue.Recipient'), 5)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.Create.Order'), 1)
Mobile.tap(findTestObject('Object Repository/Delivery/button.Create.Order'), 3)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/button.Continue.Recipient'), 3)
Mobile.waitForElementAttributeValue(findTestObject('Object Repository/Delivery/button.Continue.Recipient'),'enabled', 'true', 3)
Mobile.tap(findTestObject('Object Repository/Delivery/button.Continue.Recipient'), 5)


