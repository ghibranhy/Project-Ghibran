import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import custom_library.ErrorHandlingManager
import custom_library.MobileHelper
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC001
 * Test Name      : 001. Create order immediate cancel
 * Description    : Melakukan pembuatan order immediate (instan) lalu langsung melakukan pembatalan saat proses pemesanan/pencarian driver.
 */

// Helper Function: Handle element readiness before performing tap action
def waitForReadyAndTap(TestObject testObject, int timeout = GlobalVariable.TIMEOUT_SHORT) {
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.tap(testObject, timeout)
}

// 1. Navigation to Delivery Module via Homepage
Mobile.delay(3)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Home'), GlobalVariable.TIMEOUT_SHORT)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Delivery'), GlobalVariable.TIMEOUT_SHORT)

// 2. Search & Input Location Process
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), GlobalVariable.TIMEOUT_SHORT)
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), GlobalVariable.TIMEOUT_SHORT)
ErrorHandlingManager.doubledTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(1)

if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/buttonClearTeks'), 2, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/buttonClearTeks'), GlobalVariable.TIMEOUT_SHORT, FailureHandling.OPTIONAL)
	Mobile.delay(1)
}

Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Current location'), GlobalVariable.PICKUP_LOC_DEFAULT, GlobalVariable.TIMEOUT_SHORT)
Mobile.hideKeyboard(FailureHandling.OPTIONAL)
Mobile.delay(1)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/TextViewPickUpLocation'), GlobalVariable.TIMEOUT_SHORT)

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

// 5. Execute Immediate Order Creation
MobileHelper.tapCreateOrderButton()
Mobile.delay(2)

// 6. Trigger Immediate Cancel Order & Capture Screenshot Evidence
if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelCreate'), 5, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelCreate'), GlobalVariable.TIMEOUT_SHORT)
}

Mobile.delay(2)
Mobile.takeScreenshot()