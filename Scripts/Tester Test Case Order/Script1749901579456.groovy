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
import custom_library.TransactionalManager
import internal.GlobalVariable

Mobile.startExistingApplication('com.seatech.bluebird.regress')

// Helper function: tunggu elemen hadir & enabled, lalu tap
def waitForReadyAndTap(TestObject to, int timeout = 5) {
	Mobile.waitForElementPresent(to, timeout)
	Mobile.waitForElementAttributeValue(to, 'enabled', 'true', timeout)
	Mobile.tap(to, timeout)
}

// Homepage
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Home'))
waitForReadyAndTap(findTestObject('Object Repository/Delivery/1. Home Page/button.Delivery'))

// Search Location
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'))

// Double tap pake custom ErrorHandlingManager
ErrorHandlingManager.doubledWaitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), 5)
ErrorHandlingManager.doubledTap(findTestObject('Object Repository/Delivery/2. Search Location/button.Input.Pickup'), 5)

// Optional clear text
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/button.ClearText'), 3, FailureHandling.OPTIONAL)

// Isi Pickup Location
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/button.EditPickup'))
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/button.EditPickup'), 'miesol kosambi', 3)
Mobile.hideKeyboard()
waitForReadyAndTap(findTestObject('Object Repository/Delivery/2. Search Location/textView.PickUpAddress (1)'))

// Isi Destination Location
Mobile.setText(findTestObject('Object Repository/Delivery/2. Search Location/button.EditDestination'), 'smp negeri 120 jakarta', 3)
Mobile.hideKeyboard()
Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/2. Search Location/textView.DestinationAddress (1)'), 5)
Mobile.tap(findTestObject('Object Repository/Delivery/2. Search Location/textView.DestinationAddress (1)'), 5)

// Sender Detail
waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.PackageType'))

if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.UseMyContact.Sender'), 5)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/3. Sender Detail/button.Continue.Sender'))

// Recipient Detail
if (Mobile.waitForElementPresent(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.UseMyContact.Recipient'), 5)
}

waitForReadyAndTap(findTestObject('Object Repository/Delivery/4. Recepient Detail/button.Continue.Recipient'))
Mobile.delay(5) 

// Select Payment
//Mobile.tapAtPosition(363, 1830)
//Mobile.delay(2)
//waitForReadyAndTap(findTestObject('Object Repository/Delivery/5. Confirmation Page/Payment/select.PaymentVisa'))
//Mobile.delay(3)

// Create Order
Mobile.tapAtPosition(769, 2124)
Mobile.delay (12)

// Helper: versi string path
def waitForReadyAndTap(String objectPath, int timeout = 5) {
	def testObject = findTestObject(objectPath)
	Mobile.waitForElementPresent(testObject, timeout)
	Mobile.waitForElementAttributeValue(testObject, 'enabled', 'true', timeout)
	Mobile.tap(testObject, timeout)
}

int maxRetry = 5
int retry = 0
boolean timeoutStillExists = true

while (retry < maxRetry && timeoutStillExists) {
    println(">>> Loop ke-${retry + 1}: Back & buka ulang Active Order")

    // Step 1: Back dari halaman Finding
    waitForReadyAndTap('Object Repository/Delivery/6. On Trip/button.BackFinding')

    // Step 2: Klik Active Order dari Home
    waitForReadyAndTap('Object Repository/Delivery/1. Home Page/Select.ActiveOrderHomePage')

    // Step 3: Cek apakah muncul tombol Timeout
    timeoutStillExists = Mobile.waitForElementPresent(
        findTestObject('Object Repository/Delivery/6. On Trip/button.CancelTimeout'),
        5,
        FailureHandling.OPTIONAL
    )

    if (timeoutStillExists) {
        println("⚠️ Timeout muncul, klik Try Again")

        // Klik tombol Try Again
        waitForReadyAndTap('Object Repository/Delivery/6. On Trip/button.TryAgainTimeout')

        // Kembali dan ulangi proses buka Active Order
        waitForReadyAndTap('Object Repository/Delivery/6. On Trip/button.BackFinding')
        waitForReadyAndTap('Object Repository/Delivery/1. Home Page/Select.ActiveOrderHomePage')

        retry++
    } else {
        println("✅ Timeout tidak muncul. Finding dianggap berhasil.")
    }
}

if (timeoutStillExists) {
    KeywordUtil.markFailed("❌ Timeout masih muncul setelah ${maxRetry} kali refresh.")
} else {
    println("✅ Lanjut ke step berikutnya.")
}

String test_case_name = TransactionalManager.getMyBBTestCaseName()?.toLowerCase() ?: ''
String folder_case_name = TransactionalManager.getMyBBFolderCaseName()?.toLowerCase() ?: ''
String vehicle_type

if (test_case_name.contains('Order'.toLowerCase())) {
	vehicle_type = 'Delivery BB'
}

//WS.callTestCase(findTestCase('Test Cases/Simulator/Baru/Get Token'), ['vehicle_type':vehicle_type], FailureHandling.STOP_ON_FAILURE)
//
//WS.callTestCase(findTestCase('Test Cases/Simulator/Get Order ID'), ['vehicle_type':vehicle_type,'test_case_name':test_case_name], FailureHandling.STOP_ON_FAILURE)

//WS.callTestCase(findTestCase('Test Cases/Simulator/Edit Order Detail'), [:], FailureHandling.STOP_ON_FAILURE)

WS.callTestCase(findTestCase('Test Cases/Simulator/Handle Order to Simulator'), ['vehicle_type':vehicle_type], FailureHandling.STOP_ON_FAILURE)

WS.callTestCase(findTestCase('Test Cases/Simulator/Baru/Wait for status on trip'), [:], FailureHandling.STOP_ON_FAILURE)

WS.callTestCase(findTestCase('Test Cases/Simulator/Baru/Complete Order'), [:], FailureHandling.STOP_ON_FAILURE)

WS.callTestCase(findTestCase('Test Cases/Simulator/Baru/Wait for status complete'), [:], FailureHandling.STOP_ON_FAILURE)

Mobile.callTestCase(findTestCase('Test Cases/Simulator/Baru/Continue MyBB' + ' ' + vehicle_type), [:], FailureHandling.STOP_ON_FAILURE)



