import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import custom_library.MobileHelper
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC_RT_005
 * Test Name      : TC_RT_005_Rating_With_Minimum_Tip
 * Description    : Pengujian pemberian custom tip dengan nominal batas minimum (MIN_TIP) dan rating bintang 5.
 */

def waitForReadyAndTap(TestObject testObject, int timeout = GlobalVariable.TIMEOUT_SHORT) {
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.tap(testObject, timeout)
}

// 1. Stabilisasi render UI awal
Mobile.delay(3)

// 2. Input Custom Minimum Tip
TestObject customTipBtn = findTestObject('Object Repository/Delivery/7. Complete Order/buttonCustomTip')
TestObject saveTipBtn   = findTestObject('Object Repository/Delivery/7. Complete Order/buttonSaveCustomTip')

if (Mobile.waitForElementPresent(customTipBtn, 5, FailureHandling.OPTIONAL)) {
	Mobile.tap(customTipBtn, GlobalVariable.TIMEOUT_SHORT)
	Mobile.setText(customTipBtn, MIN_TIP.toString(), GlobalVariable.TIMEOUT_SHORT)
	Mobile.hideKeyboard(FailureHandling.OPTIONAL)
	Mobile.delay(1)
	
	if (Mobile.waitForElementPresent(saveTipBtn, 3, FailureHandling.OPTIONAL)) {
		Mobile.tap(saveTipBtn, GlobalVariable.TIMEOUT_SHORT)
		KeywordUtil.logInfo("Minimum tip berhasil disimpan.")
	}
}

// 3. Eksekusi rating bintang 5 via kalkulasi koordinat dinamis
MobileHelper.tapRatingStar(5)
Mobile.delay(2)

// 4. Penyelenggaraan aksi submit (Done)
TestObject doneBtn = findTestObject('Object Repository/Delivery/7. Complete Order/buttonDoneCompleteOrder')

Mobile.scrollToText("Done", FailureHandling.OPTIONAL)
waitForReadyAndTap(doneBtn, GlobalVariable.TIMEOUT_SHORT)

// 5. Dokumentasi bukti pengujian
Mobile.delay(1)
Mobile.takeScreenshot()