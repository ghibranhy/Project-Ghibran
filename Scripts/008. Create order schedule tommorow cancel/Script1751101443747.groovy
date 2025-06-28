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

Mobile.swipe(500, 1600, 500, 400) 
Mobile.delay(1)

// Tunggu tombol muncul & tap
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelSchedule'), 5)
Mobile.verifyElementExist(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelSchedule'), 5)
Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelSchedule'), 1)

// Pilih alasan pembatalan
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelReason'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelReason'), 5)

// Submit cancel
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), 3)
Mobile.waitForElementAttributeValue(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), 'enabled', 'true', 3)
Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), 5)

// Screenshot hasil
Mobile.takeScreenshot()



