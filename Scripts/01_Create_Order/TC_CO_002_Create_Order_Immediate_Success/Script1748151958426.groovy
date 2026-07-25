import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil

import custom_library.MobileHelper
import internal.GlobalVariable


/**
 * Test Case ID   : TC002
 * Test Name      : 002. Create order immediate success
 * Description    : Testing kelanjutan pemesanan Delivery instan pasca pembatalan pada fase Order Creation (TC001). 
 *                  Melakukan re-submit / hit ulang pembuatan order dari halaman Confirmation Order 
 *                  hingga pesanan berhasil dibentuk dan masuk ke status Finding Driver.
 */


// Delay singkat memastikan halaman Confirmation Order sudah stabil pasca cancel di TC001
Mobile.delay(3)

// 1. Re-execute Order Creation (Hit ulang tombol 'Pesan Sekarang' di Confirmation Page)
MobileHelper.tapCreateOrderButton()

// 2. Verify Finding Driver Screen & Capture Screenshot Evidence
TestObject cancelFindingBtn = findTestObject('Object Repository/Delivery/6. On Trip/button.CancelFinding')

if (Mobile.waitForElementPresent(cancelFindingBtn, GlobalVariable.TIMEOUT_MEDIUM, FailureHandling.OPTIONAL)) {
	Mobile.delay(1)
	Mobile.takeScreenshot()
} else {
	KeywordUtil.markFailed("Gagal masuk ke halaman Finding Driver setelah hit ulang order dari Confirmation Page!")
}