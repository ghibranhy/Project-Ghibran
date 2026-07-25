import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import custom_library.MobileHelper
import custom_library.TransactionalManager
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC016
 * Test Name      : 016. Create completed order with ECV input trip purpose
 * Description    : Testing flow pemesanan ECV dengan mengisi Trip Purpose hingga transaksi diselesaikan via simulator.
 * Pre-condition  : Berada di Confirmation Page dengan pembayaran ECV (Lanjutan dari TC015).
 */

// Helper Function: Handle element readiness before performing tap action
def waitForReadyAndTap(TestObject testObject, int timeout = GlobalVariable.TIMEOUT_SHORT) {
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.tap(testObject, timeout)
}

Mobile.delay(3)

// 1. Open Trip Purpose Form via Object Repository langsung
TestObject tripPurposeBtn = findTestObject('Object Repository/Delivery/5. Confirmation Page/Trip Purpose/buttonTripPurpose')
waitForReadyAndTap(tripPurposeBtn, GlobalVariable.TIMEOUT_SHORT)
Mobile.delay(2) // Jeda stabilisasi animasi bottom sheet Trip Purpose

// 2. Input Text & Save Trip Purpose
TestObject tripPurposeInput = findTestObject('Object Repository/Delivery/5. Confirmation Page/Trip Purpose/textView.TripPurpose')
TestObject saveTripBtn      = findTestObject('Object Repository/Delivery/5. Confirmation Page/Trip Purpose/button.SaveTripPurpose')

if (Mobile.waitForElementPresent(tripPurposeInput, GlobalVariable.TIMEOUT_SHORT, FailureHandling.OPTIONAL)) {
	Mobile.tap(tripPurposeInput, GlobalVariable.TIMEOUT_SHORT)
	Mobile.setText(tripPurposeInput, 'test 123', GlobalVariable.TIMEOUT_SHORT)
	Mobile.hideKeyboard(FailureHandling.OPTIONAL)
	Mobile.delay(1)
	
	if (Mobile.waitForElementPresent(saveTripBtn, GlobalVariable.TIMEOUT_SHORT, FailureHandling.OPTIONAL)) {
		Mobile.tap(saveTripBtn, GlobalVariable.TIMEOUT_SHORT)
	}
	KeywordUtil.logInfo("✅ Trip Purpose berhasil diisi dan disimpan.")
} else {
	KeywordUtil.markFailed("❌ Field input Trip Purpose (textView.TripPurpose) tidak muncul setelah buttonTripPurpose di-tap!")
}

Mobile.delay(2)

// 3. Execute Create Order
MobileHelper.tapCreateOrderButton()

// Beri jeda/wait sampai order ter-submit ke backend
Mobile.delay(10)

// 4. Menyiapkan Parameter Simulator
String testCaseName = TransactionalManager.getMyBBTestCaseName()?.toLowerCase() ?: ''
String vehicleType  = 'Delivery BB'

// 5. Get Token & Order ID
WS.callTestCase(findTestCase('Test Cases/Simulator/Get Token'), ['vehicle_type': vehicleType], FailureHandling.STOP_ON_FAILURE)

// Beri delay 3 detik tambahan sebelum query Get Order ID
Mobile.delay(3)
WS.callTestCase(findTestCase('Test Cases/Simulator/Get Order ID'), ['vehicle_type': vehicleType, 'test_case_name': testCaseName], FailureHandling.STOP_ON_FAILURE)

// 6. Update Status via Simulator
Mobile.comment("Mengubah status orderan: ENROUTE (1)")

// --- DEBUG LOG KHUSUS ENROUTE ---
KeywordUtil.logInfo("🔍 [DEBUG] SEBELUM HIT ENROUTE - Order ID: " + TransactionalManager.getOrderID())

WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status Enroute'), ['epay_value': '1'], FailureHandling.STOP_ON_FAILURE)

KeywordUtil.logInfo("🔍 [DEBUG] SESUDAH HIT ENROUTE - Cek apakah di HP sudah switch ke Cash?")
// ---------------------------------

Mobile.delay(6)

Mobile.comment("Mengubah status orderan: ARRIVED (4)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status Arrived'), ['epay_value': '1'], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(3)

Mobile.comment("Mengubah status orderan: ON TRIP (6)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status On Trip'), ['epay_value': '1'], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(5)

Mobile.comment("Mengubah status orderan: COMPLETE ECV (8)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status Complete Order Non Cash'), ['epay_value': '1'], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(5)

// 7. Capture Screenshot Evidence
Mobile.takeScreenshot()