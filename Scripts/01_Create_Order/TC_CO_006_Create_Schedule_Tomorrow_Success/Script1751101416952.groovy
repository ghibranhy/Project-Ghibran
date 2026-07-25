import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import custom_library.ErrorHandlingManager
import custom_library.MobileHelper
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC006
 * Test Name      : 006. Create order schedule tomorrow success
 * Description    : Testing flow pemesanan Delivery dengan penjadwalan pengiriman H+1 (Schedule Tomorrow).
 */

// Helper Function: Handle element readiness before tap
def waitForReadyAndTap(TestObject testObject, int timeout = GlobalVariable.TIMEOUT_SHORT) {
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.tap(testObject, timeout)
}
Mobile.delay(3)
// 1. Navigation to Delivery Module via Homepage
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Home'))
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Delivery'))

// 2. Search & Input Location Process
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'))

// Double tap for Pickup Location field
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), GlobalVariable.TIMEOUT_SHORT)
ErrorHandlingManager.doubledTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(1)

// Clear existing text input if clear button is present
if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/buttonClearTeks'), 2, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/buttonClearTeks'), GlobalVariable.TIMEOUT_SHORT, FailureHandling.OPTIONAL)
	Mobile.delay(1)
}

// Input Pickup Location
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Current location'), GlobalVariable.PICKUP_LOC_DEFAULT, GlobalVariable.TIMEOUT_SHORT)
Mobile.hideKeyboard(FailureHandling.OPTIONAL)
Mobile.delay(1)
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/TextView_PickUpLocationUpdate'))

// Input Destination Location
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Deliver to'), GlobalVariable.TIMEOUT_SHORT)
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/android.widget.EditText - Deliver to'), GlobalVariable.DEST_LOC_DEFAULT, GlobalVariable.TIMEOUT_SHORT)
Mobile.hideKeyboard(FailureHandling.OPTIONAL)
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/TextView_DestinationLocation'), GlobalVariable.TIMEOUT_SHORT)
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/TextView_DestinationLocation'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(3)

// 3. Fill Sender Detail
waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.PackageType'))

if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), GlobalVariable.TIMEOUT_SHORT)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.Continue.Sender'))

// 4. Fill Recipient Detail
if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), GlobalVariable.TIMEOUT_SHORT)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.Continue.Recipient'))
Mobile.delay(3)

// 5. Set Tomorrow Schedule
MobileHelper.tapScheduleOption()
Mobile.delay(2)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Schedule/textView.HourlyTime'), GlobalVariable.TIMEOUT_SHORT)

// Select 'Tomorrow' option
waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Schedule/button.Tomorrow'))

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/5. Confirmation Page/Schedule/button.SaveSchedule'), GlobalVariable.TIMEOUT_SHORT)
Mobile.tap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Schedule/button.SaveSchedule'), GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(5)

// 6. Execute Scheduled Order & Capture Screenshot Evidence
MobileHelper.tapCreateOrderButton()
Mobile.delay(5)
Mobile.takeScreenshot()