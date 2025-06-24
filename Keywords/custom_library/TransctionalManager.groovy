package custom_library

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.testobject.ResponseObject
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import internal.GlobalVariable

public class TransactionalManager {
	static String MyBBPasscode = null
	static String MyBBDateSchedule = null
	static String OrderStatus = null
	static String StatusBeforeLoading = null
	static String ExpectedAndroidDriver = null
	static String MyBBTestCaseName = null
	static String getMyBBTestCaseName = null
	static String MyBBFolderCaseName = null
	static String getMyBBFolderCaseName = null
	static String MyBBTestSuitesName = null
	static String ExpectedToastMessageEng = null
	static String ExpectedToastMessageIdn = null
	static String MyBBPaymentTypeHistory = null
	static String MyBBServiceTypeHistory = null
	static String MyBBPaymentStatusHistory = null
	static String IotFareInfo = null
	static String IotExtraFareInfo = null
	static String BBDAuthToken = null
	static String getBBDAuthToken = null
	static String CPAuthToken = null
	static String BBDSimulatorSession = null
	static String BBDSimulatorNomorLambung = null
	static String BBDSimulatorDriverID = null
	static String BBDSimulatorVehicleID = null
	static String BBDSimulatorDriverPass = null
	static String BBDSimulatorDeviceID = null
	static String OrderID = null
	static String getOrderID = null
	static String PriceBeforePromo = null
	static String PriceAfterPromo = null
	static String EvoucherBalance = null
	static String AmountTrips = null
	static String MyBBToken = null
	static String MyBBLanguage = 'English'
	static String MyBBID = null
	static String HandlingFeeActualFare = null
	static String HandlingFee = null
	static String HandlingFeeTotal = null
	static String OrderIDMybb = null
	static String getOrderIDMybb = null
	static String IotState = null
	static String getIotState = null
	static String PairingDriver = null
	static String getPairingDriver = null


	@Keyword public static void setMyBBPasscode(String passcode) { MyBBPasscode = passcode }
	@Keyword public static void setMyBBLanguage(String value) { MyBBLanguage = value }
	@Keyword public static void setMyBBID(String value) { MyBBID = value }
	@Keyword public static void setMyBBDateSchedule(String date_schedule) { MyBBDateSchedule = date_schedule }

	@Keyword public static void setMyBBTestcaseName(String name) { MyBBTestCaseName = name }
	@Keyword public static String getMyBBTestCaseName() { return MyBBTestCaseName }

	@Keyword public static void setMyBBFolderCaseName(String name) { MyBBFolderCaseName = name }
	@Keyword public static String getMyBBFolderCaseName() { return MyBBFolderCaseName }

	@Keyword public static void setMyBBTestSuitesName(String name) { MyBBTestSuitesName = name }

	@Keyword public static void setMyBBPaymentStatusHistory(String value) { MyBBPaymentStatusHistory = value }
	@Keyword public static void setMyBBPaymentTypeHistory(String value) { MyBBPaymentTypeHistory = value }
	@Keyword public static void setMyBBServiceTypeHistory(String value) { MyBBServiceTypeHistory = value }

	@Keyword public static void setIotFareInfo(String value) { IotFareInfo = value }
	@Keyword public static void setIotExtraFareInfo(String value) { IotExtraFareInfo = value }

	@Keyword
	public static void setOrderStatus(String order_status) {
		if (order_status == 'Job Detail') {
			OrderStatus = 'driver_on_the_way'
		} else if (order_status == 'START') {
			OrderStatus = 'driver_arrived'
		} else if (order_status == 'FINISH') {
			OrderStatus = 'on_trip'
		} else if (order_status == 'Order Complete') {
			OrderStatus = 'completed'
		} else {
			OrderStatus = 'ERROR'
		}
	}

	@Keyword public static void setStatusBeforeLoading(String value) { StatusBeforeLoading = value }
	@Keyword public static void setExpectedAndroidDriver(String value) { ExpectedAndroidDriver = value }

	@Keyword public static void setExpectedToastMessageEng(String value) { ExpectedToastMessageEng = value }
	@Keyword public static void setExpectedToastMessageIdn(String value) { ExpectedToastMessageIdn = value }

	@Keyword
	public static void verifyActualText(String actual, String expected_eng, String expected_idn, FailureHandling priority = FailureHandling.STOP_ON_FAILURE) {
		Mobile.takeScreenshot()
		if (actual.equals(expected_eng)) {
			Mobile.verifyMatch(actual, expected_eng, false, priority)
		} else {
			Mobile.verifyMatch(actual, expected_idn, false, priority)
		}
	}

	@Keyword
	public static void verifyActualContainsText(String actual, String expected_eng, String expected_idn, FailureHandling priority = FailureHandling.STOP_ON_FAILURE) {
		Mobile.takeScreenshot()
		if (actual.contains(expected_eng) || actual.contains(expected_idn)) {
			KeywordUtil.markPassed(actual)
		} else {
			KeywordUtil.markFailed('not match')
		}
	}

	@Keyword public static void setBBDAuthToken(String value) { BBDAuthToken = value }
	@Keyword public static void setCPAuthToken(String value) { CPAuthToken = value }
	@Keyword public static void setBBDSimulatorSession(String value) { BBDSimulatorSession = value }

	@Keyword
	public static String getBBDSimulatorNomorLambung() {
	    return BBDSimulatorNomorLambung ?: GlobalVariable.BBDSimulatorNomorLambung
	}
	
	@Keyword
	public static String getBBDSimulatorDriverID() {
	    return BBDSimulatorDriverID ?: GlobalVariable.BBDSimulatorDriverID
	}
	
	@Keyword
	public static String getBBDSimulatorVehicleID() {
	    return BBDSimulatorVehicleID ?: GlobalVariable.BBDSimulatorVehicleID
	}

	@Keyword public static void setBBDSimulatorDriverPass(String value) { BBDSimulatorDriverPass = value }
	@Keyword public static void setBBDSimulatorDeviceID(String value) { BBDSimulatorDeviceID = value }

	@Keyword public static void setOrderID(String value) { OrderID = value }
	@Keyword public static String getOrderID() { return OrderID }

	@Keyword public static void setPriceBeforePromo(String value) { PriceBeforePromo = value }
	@Keyword public static void setPriceAfterPromo(String value) { PriceAfterPromo = value }

	@Keyword public static void setEvoucherBalance(String value) { EvoucherBalance = value }
	@Keyword public static void setAmountTrips(String value) { AmountTrips = value }

	@Keyword public static void setMyBBToken(String value) { MyBBToken = value }
	@Keyword public static String getBBDAuthToken() {
	if (!GlobalVariable.bbdAuthToken || GlobalVariable.bbdAuthToken.toString().isEmpty()) {
		KeywordUtil.markFailedAndStop("❌ GlobalVariable.bbdAuthToken kosong. Pastikan token sudah di-set sebelumnya.")
	}
	return GlobalVariable.bbdAuthToken
}

	@Keyword public static void setHandlingFeeActualFare(String value) { HandlingFeeActualFare = value }
	@Keyword public static void setHandlingFee(String value) { HandlingFee = value }
	@Keyword public static void setHandlingFeeTotal(String value) { HandlingFeeTotal = value }

	@Keyword public static void setOrderIDMybb(String value) { OrderIDMybb = value }
	@Keyword public static String getOrderIDMybb() { return OrderIDMybb }

	@Keyword public static void setIotState(String value) { IotState = value }
	@Keyword public static String getIotState() { return IotState }

	@Keyword public static void setPairingDriver(String value) { PairingDriver = value }
	@Keyword public static String getPairingDriver() { return PairingDriver }
}