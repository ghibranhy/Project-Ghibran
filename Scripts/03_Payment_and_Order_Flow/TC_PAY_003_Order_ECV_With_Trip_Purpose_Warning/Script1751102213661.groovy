import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import custom_library.ErrorHandlingManager
import custom_library.MobileHelper
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC015
 * Test Name      : 015. Create Order with ECV without trip purpose
 * Description    : Validasi pembuatan order menggunakan e-Voucher (ECV) tanpa mengisi Trip Purpose (harus gagal/terhalang).
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
Mobile.delay(3)

// 5. Select ECV Payment Method with Scroll & Trigger Retry Handler
TestObject ecvPaymentObj    = findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/selectPaymentECV')
TestObject closeListBtn     = findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/button.CloseListPayment')
TestObject cancelCreateBtn  = findTestObject('Object Repository/Delivery/6. On Trip/button.CancelCreate')

int maxRetry = 4
int attempt = 0
boolean isECVFound = false

while (attempt < maxRetry) {
	attempt++
	KeywordUtil.logInfo("🔄 [Attempt ${attempt}/${maxRetry}] Buka list payment & cek opsi ECV...")
	
	// 1. Open Payment List
	MobileHelper.tapPaymentOption()
	Mobile.delay(2)
	
	// 2. Cek awal di bagian atas
	if (Mobile.waitForElementPresent(ecvPaymentObj, 2, FailureHandling.OPTIONAL)) {
		isECVFound = true
	} else {
		// 3. Scroll ke bawah buat mastiin opsi ECV enggak ngumpet di bawah
		KeywordUtil.logInfo("📜 Scrolling down list payment...")
		Mobile.swipe(500, 1800, 500, 800) // Scroll dari bawah ke atas
		Mobile.delay(1)
		
		if (Mobile.waitForElementPresent(ecvPaymentObj, 2, FailureHandling.OPTIONAL)) {
			isECVFound = true
		}
	}
	
	// Jika ketemu, tap dan keluar dari loop
	if (isECVFound) {
		waitForReadyAndTap(ecvPaymentObj, GlobalVariable.TIMEOUT_SHORT)
		KeywordUtil.logInfo("✅ Opsi pembayaran ECV berhasil ditemukan dan dipilih pada percobaan ke-${attempt}.")
		break
	}
	
	KeywordUtil.logInfo("⚠️ ECV beneran gaada setelah di-scroll. Lanjut: Close -> Create Order -> Cancel Order...")
	
	// 4. Close Payment List
	if (Mobile.waitForElementPresent(closeListBtn, 2, FailureHandling.OPTIONAL)) {
		Mobile.tap(closeListBtn, GlobalVariable.TIMEOUT_SHORT, FailureHandling.OPTIONAL)
		Mobile.delay(1)
	}
	
	// 5. Trigger Create Order
	MobileHelper.tapCreateOrderButton()
	
	// 6. Immediate Cancel Create Order
	if (Mobile.waitForElementPresent(cancelCreateBtn, 3, FailureHandling.OPTIONAL)) {
		Mobile.tap(cancelCreateBtn, GlobalVariable.TIMEOUT_SHORT)
		KeywordUtil.logInfo("✅ Order creation berhasil dibatalkan via button.CancelCreate")
	}
	
	Mobile.delay(2)
}

if (!isECVFound) {
	KeywordUtil.markFailed("❌ Opsi pembayaran ECV / Corporate Voucher tetap tidak muncul setelah ${maxRetry} kali percobaan (termasuk scroll)!")
}

Mobile.delay(2)