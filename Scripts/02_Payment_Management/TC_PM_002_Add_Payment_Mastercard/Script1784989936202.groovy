import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import custom_library.MobileHelper
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC009
 * Test Name      : 009. Add Payment Mastercard
 * Description    : Testing flow penambahan metode pembayaran baru berupa Kartu Kredit/Debit berlogo Mastercard.
 * Pre-condition  : Berada di Confirmation Page (Lanjutan dari TC008).
 */

// Helper Function: Handle element readiness before performing tap action
def waitForReadyAndTap(TestObject testObject, int timeout = GlobalVariable.TIMEOUT_SHORT) {
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.tap(testObject, timeout)
}
Mobile.delay(5)

// 1. Select & Add Payment Method (Lanjutan dari Confirmation Page TC008)
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

// 2. Input Credit Card Credentials
def cardNumber  = '5211 1111 1111 1117'
def expiredDate = '1226'
def cvv         = '123'
def password    = '112233'

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

// 3. Verify & Input Password Authentication
waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditPasswordCC'), GlobalVariable.TIMEOUT_SHORT)
Mobile.setText(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.EditPasswordCC'), password, GlobalVariable.TIMEOUT_SHORT)

waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.OK'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(1)
Mobile.takeScreenshot()