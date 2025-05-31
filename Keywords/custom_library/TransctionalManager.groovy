package custom_library

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil

public class TransactionalManager {
	static String MyBBPasscode = null
	static String MyBBDateSchedule = null
	static String OrderStatus = null
	static String StatusBeforeLoading = null
	static String ExpectedAndroidDriver = null
	static String MyBBTestCaseName = null
	static String MyBBFolderCaseName = null
	static String MyBBTestSuitesName = null
	static String ExpectedToastMessageEng = null
	static String ExpectedToastMessageIdn = null
	static String MyBBPaymentTypeHistory = null
	static String MyBBServiceTypeHistory = null
	static String MyBBPaymentStatusHistory = null
	static String IotFareInfo = null
	static String IotExtraFareInfo = null
	static String BBDAuthToken = null
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


	@Keyword
	public static void setMyBBPasscode(String passcode) {
		MyBBPasscode = passcode
	}
	@Keyword
	public static void setMyBBLanguage(String value) {
		MyBBLanguage = value
	}
	@Keyword
	public static void setMyBBID(String value) {
		MyBBID = value
	}
	@Keyword
	public static void setMyBBDateSchedule(String date_schedule) {
		MyBBDateSchedule = date_schedule
	}
	@Keyword
	public static void setMyBBTestcaseName(String testcase_name) {
		MyBBTestCaseName = testcase_name
	}
	@Keyword
	public static void setMyBBFolderCaseName(String foldercase_name) {
		MyBBFolderCaseName = foldercase_name
	}
	@Keyword
	public static void setMyBBTestSuitesName(String testsuites_name) {
		MyBBTestSuitesName = testsuites_name
	}
	@Keyword
	public static void setMyBBPaymentStatusHistory(String payment_status) {
		MyBBPaymentStatusHistory = payment_status
	}
	@Keyword
	public static void setMyBBPaymentTypeHistory(String payment_type) {
		MyBBPaymentTypeHistory = payment_type
	}
	@Keyword
	public static void setMyBBServiceTypeHistory(String service_type) {
		MyBBServiceTypeHistory = service_type
	}
	@Keyword
	public static void setIotFareInfo(String fare_info) {
		IotFareInfo = fare_info
	}
	@Keyword
	public static void setIotExtraFareInfo(String extra_fare_info) {
		IotExtraFareInfo = extra_fare_info
	}
	@Keyword
	public static void setOrderStatus(String order_status) {
		//Driver is On The Way to pick-up
		if (order_status == 'Job Detail') {
			OrderStatus = 'driver_on_the_way'
		}
		//Driver is Arrived
		else if (order_status == 'START') {
			OrderStatus = 'driver_arrived'
		}
		//Driver and customer are On Trip to destination
		else if (order_status == 'FINISH') {
			OrderStatus = 'on_trip'
		}
		//Customer's order is Completed
		else if (order_status == 'Order Complete') {
			OrderStatus = 'completed'
		}
		//Default
		else {
			OrderStatus = 'ERROR'
		}
	}
	@Keyword
	public static void setStatusBeforeLoading(String order_status) {
		StatusBeforeLoading = order_status
	}
	@Keyword
	public static void setExpectedAndroidDriver(String android_driver) {
		ExpectedAndroidDriver = android_driver
	}
	@Keyword
	public static void setExpectedToastMessageEng(String expected_toast_message) {
		ExpectedToastMessageEng = expected_toast_message
	}
	@Keyword
	public static void setExpectedToastMessageIdn(String expected_toast_message) {
		ExpectedToastMessageIdn = expected_toast_message
	}
	@Keyword
	public static void verifyActualText(String actual, String expected_eng, String expected_idn, FailureHandling priority = FailureHandling.STOP_ON_FAILURE){
		Mobile.takeScreenshot()
		if (actual.equals(expected_eng)) {
			Mobile.verifyMatch(actual, expected_eng, false, priority)
		} else {
			Mobile.verifyMatch(actual, expected_idn, false, priority)
		}
	}
	@Keyword
	public static void verifyActualContainsText(String actual, String expected_eng, String expected_idn, FailureHandling priority = FailureHandling.STOP_ON_FAILURE){
		Mobile.takeScreenshot()
		if (actual.contains(expected_eng) || actual.contains(expected_idn)) {
			KeywordUtil.markPassed(actual)
		} else {
			KeywordUtil.markFailed('not match')
		}
	}
	@Keyword
	public static void setBBDAuthToken(String token) {
		BBDAuthToken = token
	}
	@Keyword
	public static void setCPAuthToken(String token) {
		CPAuthToken = token
	}
	@Keyword
	public static void setBBDSimulatorSession(String session) {
		BBDSimulatorSession = session
	}
	@Keyword
	public static void setBBDSimulatorNomorLambung(String nomor_lambung) {
		BBDSimulatorNomorLambung = nomor_lambung
	}
	@Keyword
	public static void setBBDSimulatorDriverID(String driver_id) {
		BBDSimulatorDriverID = driver_id
	}
	@Keyword
	public static void setBBDSimulatorVehicleID(String vehicle_id) {
		BBDSimulatorVehicleID = vehicle_id
	}
	@Keyword
	public static void setBBDSimulatorDriverPass(String driver_pass) {
		BBDSimulatorDriverPass = driver_pass
	}
	@Keyword
	public static void setBBDSimulatorDeviceID(String device_id) {
		BBDSimulatorDeviceID = device_id
	}
	@Keyword
	public static void setOrderID(String order_id) {
		OrderID = order_id
	}
	@Keyword
	public static void setgetOrderID(String order_id) {
		OrderID = order_id
	}
	@Keyword
	public static void setPriceBeforePromo(String price_before_promo) {
		PriceBeforePromo = price_before_promo
	}
	@Keyword
	public static void setPriceAfterPromo(String price_after_promo) {
		PriceAfterPromo = price_after_promo
	}
	@Keyword
	public static void SetEvoucherBalance(String evoucher_balance) {
		EvoucherBalance = evoucher_balance
	}
	@Keyword
	public static void SetAmountTrips(String amount_trips) {
		AmountTrips = amount_trips
	}
	@Keyword
	public static void SetMyBBToken(String token) {
		MyBBToken = token
	}
	@Keyword
	public static void SetHandlingFeeActualFare(String actual_fare) {
		HandlingFeeActualFare = actual_fare
	}
	@Keyword
	public static void SetHandlingFee(String handling_fee) {
		HandlingFee = handling_fee
	}
	@Keyword
	public static void HandlingFeeTotal(String total) {
		HandlingFeeTotal = total
	}
	@Keyword
	public static void SetOrderIDMybb(String order_id_mybb) {
		OrderIDMybb = order_id_mybb
	}
	@Keyword
	public static void SetgetOrderIDMybb(String order_id_mybb) {
		OrderIDMybb = order_id_mybb
	}
	@Keyword
	public static void SetIotState(String state_iot) {
		IotState = state_iot
	}
	@Keyword
	public static void SetgetIotState(String state_iot) {
		IotState = state_iot
	}
	@Keyword
	public static void SetPairingDriver(String pairing_driver) {
		PairingDriver = pairing_driver
	}
	@Keyword
	public static void SetgetPairingDriver(String pairing_driver) {
		PairingDriver = pairing_driver
	}
}