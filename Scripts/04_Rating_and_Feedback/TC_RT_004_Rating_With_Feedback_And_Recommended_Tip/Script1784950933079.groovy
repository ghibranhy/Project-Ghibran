import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import custom_library.MobileHelper
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC_RT_004
 * Test Name      : TC_RT_004_Rating_With_Feedback_And_Recommended_Tip
 * Description    : Pengujian pemberian rating bintang 5, feedback rekomendasi, serta rekomendasi tipping.
 */

def waitForReadyAndTap(TestObject testObject, int timeout = GlobalVariable.TIMEOUT_SHORT) {
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.tap(testObject, timeout)
}

// 1. Stabilisasi render UI awal
Mobile.delay(3)

// 2. Pemilihan Opsi Recommended Tipping
TestObject tippingBtn = findTestObject('Object Repository/Delivery/7. Complete Order/buttonRecomendationTipping')

if (Mobile.waitForElementPresent(tippingBtn, 5, FailureHandling.OPTIONAL)) {
	Mobile.tap(tippingBtn, GlobalVariable.TIMEOUT_SHORT)
	KeywordUtil.logInfo("Elemen tipping rekomendasi berhasil di-tap.")
	Mobile.delay(1)
}

// 3. Eksekusi rating bintang 5 via kalkulasi koordinat dinamis
MobileHelper.tapRatingStar(5)
Mobile.delay(2)

// 4. Scroll viewport dan pemilihan feedback rekomendasi
TestObject feedbackObj = findTestObject('Object Repository/Delivery/7. Complete Order/buttonFeedbackRating')

Mobile.scrollToText("Excellent", FailureHandling.OPTIONAL)

if (Mobile.waitForElementPresent(feedbackObj, 5, FailureHandling.OPTIONAL)) {
	Mobile.tap(feedbackObj, GlobalVariable.TIMEOUT_SHORT)
	KeywordUtil.logInfo("Opsi feedback rekomendasi berhasil di-tap.")
	Mobile.delay(1)
}

// 5. Penyelenggaraan aksi submit (Done)
TestObject doneBtn = findTestObject('Object Repository/Delivery/7. Complete Order/buttonDoneCompleteOrder')

Mobile.scrollToText("Done", FailureHandling.OPTIONAL)
waitForReadyAndTap(doneBtn, GlobalVariable.TIMEOUT_SHORT)

// 6. Dokumentasi bukti pengujian
Mobile.delay(1)
Mobile.takeScreenshot()