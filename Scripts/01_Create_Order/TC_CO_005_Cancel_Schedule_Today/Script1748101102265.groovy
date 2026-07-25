import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import custom_library.MobileHelper
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC005
 * Test Name      : 005. Create order schedule today cancel
 * Description    : Testing flow pembatalan pesanan Delivery yang sudah terjadwal untuk hari ini (Schedule Today) 
 *                  dari halaman detail trip / pesanan aktif pasca sukses pembuatan order di TC004.
 */

// Helper Function: Handle element readiness before performing tap action
def waitForReadyAndTap(TestObject testObject, int timeout = GlobalVariable.TIMEOUT_SHORT) {
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.tap(testObject, timeout)
}

// 1. Wait for Screen Stabilization
Mobile.delay(3)

// 2. Swipe until 'Cancel Schedule' button is found
boolean isFound = MobileHelper.swipeUntilElementFound(
	findTestObject('Object Repository/Delivery/6. On Trip/button.CancelSchedule'), 
	3
)

// 3. Perform Cancellation Process on Active Trip Page
if (isFound) {
	// Tap Cancel Schedule Button
	Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelSchedule'), GlobalVariable.TIMEOUT_SHORT)
	
	// Select Cancellation Reason
	if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelReason'), GlobalVariable.TIMEOUT_SHORT, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelReason'), GlobalVariable.TIMEOUT_SHORT)
	}
	
	// Submit Cancellation
	if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), GlobalVariable.TIMEOUT_SHORT, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), GlobalVariable.TIMEOUT_SHORT)
	}
	
	// Capture Evidence
Mobile.delay(3)
Mobile.takeScreenshot()
} else {
	KeywordUtil.markFailed("Tombol CancelSchedule tidak ditemukan setelah swipe!")
}