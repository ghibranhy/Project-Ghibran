import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import custom_library.ErrorHandlingManager
import com.kms.katalon.core.util.KeywordUtil


int maxAttempts = 10
boolean timeoutButtonVisible = false

for (int attempt = 1; attempt <= maxAttempts; attempt++) {
	// Trigger refresh: Back dulu
	Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.BackFinding'), 3)
	Mobile.delay(3)

	// Masuk lagi ke halaman Active Order
	Mobile.tap(findTestObject('Object Repository/Delivery/1. Home Page/Select.ActiveOrderHomePage'), 3)
	Mobile.delay(5) // kasih waktu loading

	// Cek apakah tombol Cancel Timeout sudah muncul
	if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelTimeout'), 5, FailureHandling.OPTIONAL)) {
		timeoutButtonVisible = true
		break
	}

	// Log untuk debug
	KeywordUtil.logInfo("Percobaan ke-${attempt}: Tombol belum muncul, ulangi.")
}

// Kalau sudah muncul, lanjut proses cancel
if (timeoutButtonVisible) {
	Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelTimeout'), 3)

	Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelReason'), 10)
	Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelReason'), 5)

	Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), 10)
	Mobile.waitForElementAttributeValue(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), 'enabled', 'true', 5)
	Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), 5)

	Mobile.takeScreenshot()
} else {
	KeywordUtil.markFailed("Gagal: Tombol Cancel Timeout tidak muncul setelah ${maxAttempts} kali percobaan.")
}

