import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import custom_library.ErrorHandlingManager
import custom_library.MobileHelper
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC012
 * Test Name      : 012. Create order with cash with promo failed
 * Description    : Testing flow validasi kegagalan penerapan Promo pada metode pembayaran Tunai (Cash),
 *                  memastikan bottom sheet peringatan "Promo is only for non-cash" muncul dengan benar.
 */

// Helper Function: Handle element readiness before performing tap action
def waitForReadyAndTap(TestObject testObject, int timeout = GlobalVariable.TIMEOUT_SHORT) {
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.tap(testObject, timeout)
}

// Jeda napas awal untuk stabilisasi UI
Mobile.delay(5)

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

// 5. Select Cash Payment Method
MobileHelper.tapPaymentOption()
Mobile.delay(2)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/selectPaymentCash'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(2)

// 6. Open Promo List & Select Promo
MobileHelper.tapPromoOption()
Mobile.delay(2)

// 8. Validate & Close Bottom Sheet Warning ("Promo is only for non-cash")
waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.OkayPromo'), GlobalVariable.TIMEOUT_SHORT)

// 9. Capture Screenshot Evidence
Mobile.delay(1)
Mobile.takeScreenshot()