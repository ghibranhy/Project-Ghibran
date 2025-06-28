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

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.Payment'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.Payment'), 3)

int maxSwipe = 5
int swipeCount = 0
while (!Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.AddPayment'), 2, FailureHandling.OPTIONAL) && swipeCount < maxSwipe) {
    Mobile.swipe(500, 1600, 500, 1500)
    swipeCount++
}

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.AddPayment'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.AddPayment'), 3)


Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.AddCC'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.AddCC'), 3)

def card_number = '5211 1111 1111 1117'
def expired_date = '0529'
def cvv = '123'

Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCardNumber'), 3)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCardNumber'), card_number, 3)
Mobile.delay(1)

Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditExpiryDate'), 3)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditExpiryDate'), expired_date, 3)
Mobile.delay(1)

Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCVV'), 3)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCVV'), cvv, 3)
Mobile.takeScreenshot()
Mobile.pressBack()
	
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.SaveCC'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.SaveCC'), 3)

def button_idn = 'Simpan kartu kredit'
def button_eng = 'Save new card'

Mobile.delay(3)

def password = '112233'

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditPasswordCC'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditPasswordCC'), 3)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditPasswordCC'), password, 3)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.OK'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.OK'), 3)
Mobile.takeScreenshot()

