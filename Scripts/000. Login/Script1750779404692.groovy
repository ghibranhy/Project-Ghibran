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
import com.kms.katalon.core.util.KeywordUtil

Mobile.startExistingApplication('com.seatech.bluebird.regress')

Mobile.waitForElementPresent(findTestObject('Object Repository/Login Page/editText.InputPhoneNumber'), 5)

Mobile.tap(findTestObject('Object Repository/Login Page/editText.PhoneNumber'), 3)
Mobile.delay(3)
Mobile.setText(findTestObject('Object Repository/Login Page/editText.InputPhoneNumber'), '895351529742', 3)
Mobile.tap(findTestObject('Object Repository/Login Page/button.Continue'), 3)

Mobile.waitForElementPresent(findTestObject('Object Repository/Login Page/editText.Password'), 5)

if (Mobile.waitForElementPresent(findTestObject('Object Repository/Login Page/textView.PasswordIncorrect'), 5)) {
	def message = 'Terdapat Pesan Peringatan Kata Sandi Salah'
	KeywordUtil.markFailed(message)
	Mobile.takeScreenshot()
}

Mobile.setText(findTestObject('Object Repository/Login Page/editText.InputPhoneNumber'), 'mybb1234', 3)
Mobile.tap(findTestObject('Object Repository/Login Page/button.Login'), 3)

Mobile.takeScreenshot()
KeywordUtil.markPassed("User Berhasil Login")
 


