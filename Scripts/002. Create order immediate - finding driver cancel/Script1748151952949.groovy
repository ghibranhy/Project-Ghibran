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

// Delay awal
Mobile.delay(5)

// Create Order 
Mobile.tapAtPosition(769, 2124)

/// Tunggu sampai tombol CancelFinding muncul (maks. 30 detik)
TestObject cancelFindingBtn = findTestObject('Object Repository/Delivery/6. On Trip/button.CancelFinding')
int retry = 0
int maxRetry = 30 // maks. tunggu 30 detik
boolean isFound = false

while (retry < maxRetry) {
    if (Mobile.waitForElementPresent(cancelFindingBtn, 1, FailureHandling.OPTIONAL)) {
        isFound = true
        break
    }
    retry++
    Mobile.delay(1)
}

if (!isFound) {
    KeywordUtil.markFailed("Tombol CancelFinding tidak muncul dalam waktu yang ditentukan.")
    return
}

// Lanjut cancel order
Mobile.tap(cancelFindingBtn, 3)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelReason'), 3)
Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.CancelReason'), 5)

Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), 3)
Mobile.waitForElementAttributeValue(
    findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'),
    'enabled',
    'true',
    3
)
Mobile.tap(findTestObject('Object Repository/Delivery/6. On Trip/button.SubmitCancel'), 5)

// Take Screenshot after cancel
Mobile.takeScreenshot()
KeywordUtil.markPassed("User berhasil melakukan immediate cancel pada order.")