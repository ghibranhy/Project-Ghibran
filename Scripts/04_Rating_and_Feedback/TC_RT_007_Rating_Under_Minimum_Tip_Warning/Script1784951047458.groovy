import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC_RT_007
 * Test Name      : TC_RT_007_Rating_Under_Minimum_Tip_Warning
 * Description    : Pengujian validasi peringatan nominal tip di bawah batas minimum (UNDER_MIN_TIP) dan pengambilan screenshot bukti tidak bisa save.
 */

// 1. Stabilisasi render UI awal
Mobile.delay(3)

// 2. Input Custom Tip Di Bawah Minimum
TestObject customTipBtn = findTestObject('Object Repository/Delivery/7. Complete Order/buttonCustomTip')

if (Mobile.waitForElementPresent(customTipBtn, 5, FailureHandling.OPTIONAL)) {
	Mobile.tap(customTipBtn, GlobalVariable.TIMEOUT_SHORT)
	Mobile.setText(customTipBtn, UNDER_MIN_TIP.toString(), GlobalVariable.TIMEOUT_SHORT)
	Mobile.hideKeyboard(FailureHandling.OPTIONAL)
	KeywordUtil.logInfo("Input nominal tip di bawah minimum berhasil dimasukkan.")
	Mobile.delay(2)
}

// 3. Dokumentasi screenshot bukti validasi warning under minimum tip (Button Save Disable/Error)
Mobile.takeScreenshot()