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


// Fungsi utilitas untuk tap elemen yang ready
def waitForReadyAndTap(TestObject to, int timeout = 5) {
	Mobile.waitForElementPresent(to, timeout)
	Mobile.waitForElementAttributeValue(to, 'enabled', 'true', timeout)
	Mobile.tap(to, timeout)
}

// ===== Homepage =====
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Home'), 3)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Delivery'), 3)

// ===== Search Location =====
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), 3)

ErrorHandlingManager.doubledWaitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), 5)
ErrorHandlingManager.doubledTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), 5)

// Optional clear text
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/button.ClearText'), 3, FailureHandling.OPTIONAL)

// Pickup Location
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/button.EditPickup'))
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/button.EditPickup'), 'miesol kosambi', 3)
Mobile.hideKeyboard()
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/textView.PickUpAddress (1)'))

// Destination Location
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/button.EditDestination'), 'smp negeri 120 jakarta', 3)
Mobile.hideKeyboard()
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/textView.DestinationAddress (1)'), 5)
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/textView.DestinationAddress (1)'), 5)

// ===== Sender Detail =====
waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.PackageType'))

if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), 3)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.Continue.Sender'))

// ===== Recipient Detail =====
if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), 3)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.Continue.Recipient'))
Mobile.delay(5)

// ===== Select Payment Method =====
Mobile.tapAtPosition(363, 1830)
Mobile.delay(2)
Mobile.swipe(500, 1600, 500, 400)
Mobile.delay(1)

waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.AddPayment'), 3)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.AddCC'), 3)

def cardNumber = '5211 1111 1111 1117'
def expiredDate = '0529'
def cvv = '123'

Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCardNumber'), 3)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCardNumber'), cardNumber, 3)
Mobile.delay(1)

Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditExpiryDate'), 3)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditExpiryDate'), expiredDate, 3)
Mobile.delay(1)

Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCVV'), 3)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCVV'), cvv, 3)
Mobile.delay(2)

// ===== Tap Save New Card (multi-language support) =====
def button_idn = 'Simpan kartu kredit'
def button_eng = 'Save new card'

if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.SaveNewCard', [('name') : button_idn]), 5)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.SaveNewCard', [('name') : button_idn]), 3)
} else {
	Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.SaveNewCard', [('name') : button_eng]), 3)
}

Mobile.delay(3)

// ===== Input Password (3D Secure / Bank Auth) =====
def password = '112233'

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditPasswordCC'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditPasswordCC'), 3)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditPasswordCC'), password, 3)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.OK'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.OK'), 3)
Mobile.takeScreenshot()
