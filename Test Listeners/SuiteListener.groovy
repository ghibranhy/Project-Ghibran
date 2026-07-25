import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import internal.GlobalVariable as GlobalVariable

class SuiteListener {

	@BeforeTestSuite
	def sampleBeforeTestSuite() {
		// Gunakan APP_ID yang ada di Global Variable kamu
		Mobile.startExistingApplication(GlobalVariable.APP_ID)
	}

	@AfterTestSuite
	def sampleAfterTestSuite() {
		// Tutup aplikasi di akhir Test Suite
		Mobile.closeApplication()
	}

}

