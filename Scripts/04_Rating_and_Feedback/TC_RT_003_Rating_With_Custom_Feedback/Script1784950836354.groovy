import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import custom_library.MobileHelper
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC_RT_003
 * Test Name      : TC_RT_003_Rating_With_Custom_Feedback
 * Description    : Pengujian pemberian rating bintang 5 dengan mengisi custom feedback teks.
 */

def waitForReadyAndTap(TestObject testObject, int timeout = GlobalVariable.TIMEOUT_SHORT) {
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.tap(testObject, timeout)
}

// 1. Stabilisasi render UI awal
Mobile.delay(3)

// 2. Eksekusi rating bintang 5 via kalkulasi koordinat dinamis
MobileHelper.tapRatingStar(5)
Mobile.delay(2)

// 3. Input Custom Feedback
TestObject customFeedbackBtn = findTestObject('Object Repository/Delivery/7. Complete Order/buttonCustomFeedback')
TestObject saveFeedbackBtn   = findTestObject('Object Repository/Delivery/7. Complete Order/buttonSaveCustomFeedback')

Mobile.scrollToText("Excellent", FailureHandling.OPTIONAL)

if (Mobile.waitForElementPresent(customFeedbackBtn, 5, FailureHandling.OPTIONAL)) {
	Mobile.tap(customFeedbackBtn, GlobalVariable.TIMEOUT_SHORT)
	Mobile.setText(customFeedbackBtn, FEEDBACK_COMPLETE, GlobalVariable.TIMEOUT_SHORT)
	Mobile.hideKeyboard(FailureHandling.OPTIONAL)
	Mobile.delay(1)
	
	if (Mobile.waitForElementPresent(saveFeedbackBtn, 3, FailureHandling.OPTIONAL)) {
		Mobile.tap(saveFeedbackBtn, GlobalVariable.TIMEOUT_SHORT)
	}
	KeywordUtil.logInfo("Custom feedback berhasil disimpan.")
}

// 4. Penyelenggaraan aksi submit (Done)
TestObject doneBtn = findTestObject('Object Repository/Delivery/7. Complete Order/buttonDoneCompleteOrder')

Mobile.scrollToText("Done", FailureHandling.OPTIONAL)
waitForReadyAndTap(doneBtn, GlobalVariable.TIMEOUT_SHORT)

// 5. Dokumentasi bukti pengujian
Mobile.delay(1)
Mobile.takeScreenshot()