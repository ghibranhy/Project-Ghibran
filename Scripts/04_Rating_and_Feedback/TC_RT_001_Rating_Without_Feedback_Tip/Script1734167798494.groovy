import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import custom_library.MobileHelper
import internal.GlobalVariable as GlobalVariable

/**
 * Test Case ID   : TC_RT_001
 * Test Name      : TC_RT_001_Rating_Without_Feedback_Tip
 * Description    : Pengujian pemberian rating bintang 5 tanpa memilih feedback dan tanpa pemberian tip.
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

// 3. Penyelenggaraan aksi submit (Done)
TestObject doneBtn = findTestObject('Object Repository/Delivery/7. Complete Order/buttonDoneCompleteOrder')

Mobile.scrollToText("Done", FailureHandling.OPTIONAL)
waitForReadyAndTap(doneBtn, GlobalVariable.TIMEOUT_SHORT)

// 4. Dokumentasi bukti pengujian
Mobile.delay(1)
Mobile.takeScreenshot()