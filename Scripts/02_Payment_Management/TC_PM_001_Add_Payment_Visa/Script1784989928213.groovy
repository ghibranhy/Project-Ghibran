import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import custom_library.ErrorHandlingManager
import custom_library.MobileHelper
import internal.GlobalVariable as GlobalVariable

/**
* Test Case ID : TC008
* Test Name : 008. Add Payment Visa
* Description : Testing flow penambahan metode pembayaran baru berupa Kartu Kredit/Debit berlogo Visa.
*/

// Helper Function: Handle element readiness before performing tap action
def waitForReadyAndTap(TestObject testObject, int timeout = GlobalVariable.TIMEOUT_SHORT) {
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.tap(testObject, timeout)
}

Mobile.delay(3)

// 1. Navigation to Delivery Module via Homepage
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Home'), GlobalVariable.TIMEOUT_SHORT)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Delivery'), GlobalVariable.TIMEOUT_SHORT)

// 2. Search & Input Location Process
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), GlobalVariable.TIMEOUT_SHORT)

// Double tap for Pickup Location field
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), GlobalVariable.TIMEOUT_SHORT)
ErrorHandlingManager.doubledTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(1)

// Clear existing text input if present
if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/buttonClearTeks'), 2, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/buttonClearTeks'), GlobalVariable.TIMEOUT_SHORT, FailureHandling.OPTIONAL)
	Mobile.delay(1)
}

// Input Pickup Location
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Current location'), GlobalVariable.PICKUP_LOC_DEFAULT, GlobalVariable.TIMEOUT_SHORT)
Mobile.hideKeyboard(FailureHandling.OPTIONAL)
Mobile.delay(1)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/TextViewPickUpLocation'), GlobalVariable.TIMEOUT_SHORT)

// Input Destination Location
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Deliver to'), GlobalVariable.TIMEOUT_SHORT)
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Deliver to'), GlobalVariable.DEST_LOC_DEFAULT, GlobalVariable.TIMEOUT_SHORT)
Mobile.hideKeyboard(FailureHandling.OPTIONAL)
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/TextView_DestinationLocation'), GlobalVariable.TIMEOUT_SHORT)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/TextView_DestinationLocation'), GlobalVariable.TIMEOUT_SHORT)

// 3. Fill Sender Detail
Mobile.delay(2)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.PackageType'), GlobalVariable.TIMEOUT_SHORT)

if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), GlobalVariable.TIMEOUT_SHORT)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.Continue.Sender'), GlobalVariable.TIMEOUT_SHORT)

// 4. Fill Recipient Detail
if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), GlobalVariable.TIMEOUT_SHORT)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.Continue.Recipient'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(5)

// 5. Select & Add Payment Method (Menggunakan Keyword Custom Library)
MobileHelper.tapPaymentOption()
Mobile.delay(2)

TestObject addPaymentBtn = findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.AddPayment')
boolean isAddPaymentVisible = MobileHelper.swipeUntilElementFound(addPaymentBtn, 3)

if (isAddPaymentVisible) {
	waitForReadyAndTap(addPaymentBtn, GlobalVariable.TIMEOUT_SHORT)
} else {
	KeywordUtil.markFailed("Tombol Add Payment tidak ditemukan di layar!")
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.AddCC'), GlobalVariable.TIMEOUT_SHORT)

// 6. Input Credit Card Credentials
def cardNumber = '4811 1111 1111 1114'
def expiredDate = '1226'
def cvv = '123'
def password = '112233'

waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCardNumber'), GlobalVariable.TIMEOUT_SHORT)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCardNumber'), cardNumber, GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(1)

waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditExpiryDate'), GlobalVariable.TIMEOUT_SHORT)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditExpiryDate'), expiredDate, GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(1)

waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCVV'), GlobalVariable.TIMEOUT_SHORT)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditCVV'), cvv, GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(1)

Mobile.hideKeyboard(FailureHandling.OPTIONAL)
Mobile.delay(2)

// Save Card Action (Handling Multi-language Name Attribute)
def buttonIdn = 'Simpan kartu kredit'
def buttonEng = 'Save new card'

if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.SaveNewCard', [('name') : buttonIdn]), 5, FailureHandling.OPTIONAL)) {
	waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.SaveNewCard', [('name') : buttonIdn]), GlobalVariable.TIMEOUT_SHORT)
} else {
	waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.SaveNewCard', [('name') : buttonEng]), GlobalVariable.TIMEOUT_SHORT)
}

Mobile.delay(3)

// 7. Verify & Input Password Authentication
waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditPasswordCC'), GlobalVariable.TIMEOUT_SHORT)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditPasswordCC'), password, GlobalVariable.TIMEOUT_SHORT)

waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.OK'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(1)
Mobile.takeScreenshot()