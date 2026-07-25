import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC003
 * Test Name      : 003. Create order immediate - finding driver cancel
 * Description    : Testing flow pembatalan pesanan pengiriman instan pada saat aplikasi berada di halaman 
 *                  pencarian pengemudi (Finding Driver state) pasca sukses pembuatan order.
 */


// 1. Verify Presence of Finding Driver Screen
boolean isFindingDriverReady = Mobile.waitForElementPresent(
	findTestObject('Object Repository/Delivery/6. On Trip/button.CancelFinding'), 
	GlobalVariable.TIMEOUT_LONG, 
	FailureHandling.OPTIONAL
)

// 2. Perform Cancellation Process on Finding Driver Page
if (isFindingDriverReady) {
	// Tap 'Batalkan' button on Finding Driver screen
	Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelFinding'), GlobalVariable.TIMEOUT_SHORT)
	
	// Select Cancellation Reason from Bottom Sheet / Dialog
	if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelReason'), GlobalVariable.TIMEOUT_SHORT, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelReason'), GlobalVariable.TIMEOUT_SHORT)
	}
	
	// Submit Cancellation
	if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), GlobalVariable.TIMEOUT_SHORT, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), GlobalVariable.TIMEOUT_SHORT)
	}
	
	// Take Screenshot Evidence
Mobile.delay(3)
Mobile.takeScreenshot()
} else {
	KeywordUtil.markFailed("Halaman Finding Driver / Tombol Cancel Finding tidak ditemukan!")
}