package custom_library

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.exception.StepFailedException as StepFailedException

import internal.GlobalVariable

import com.kms.katalon.core.util.KeywordUtil

public class ErrorHandlingManager {
	private static boolean isElementStale

	private static void setElementStaleStatus(StepFailedException error, FailureHandling priority) {
		String cause
		try {
			cause = error.cause
			if (cause.contains('StaleElementReferenceException')) {
				isElementStale = true
			} else {
				throw new Exception("Not containing 'StaleElementReferenceException'.\nCAUSE: $error")
			}
		} catch(Exception another_error) {
			if (priority == FailureHandling.CONTINUE_ON_FAILURE) {
				Mobile.takeScreenshot(FailureHandling.CONTINUE_ON_FAILURE)
				KeywordUtil.markFailed("Mark Test Case as FAILED")
			} else if (priority == FailureHandling.OPTIONAL) {
				KeywordUtil.markWarning("Mark Test Case as Warning")
			} else {
				Mobile.takeScreenshot(FailureHandling.CONTINUE_ON_FAILURE)
				KeywordUtil.markErrorAndStop("\n\nERROR: $another_error\n")
			}
			isElementStale = false
		}
	}


	@Keyword
	public static doubledGetText(TestObject object, int timeout, FailureHandling priority = FailureHandling.STOP_ON_FAILURE){
		isElementStale = true
		String value
		while(isElementStale) {
			try {
				if (Mobile.waitForElementPresent(object, timeout)) {
					value = Mobile.getText(object, timeout, FailureHandling.STOP_ON_FAILURE)
					break
				} else {
					throw new StepFailedException("Object $object not found")
				}
			} catch(StepFailedException error) {
				setElementStaleStatus(error, priority)
			}
		}
		return value
	}
	@Keyword
	public static void doubledSetText(TestObject object, String input, int timeout, FailureHandling priority = FailureHandling.STOP_ON_FAILURE){
		isElementStale = true
		while(isElementStale) {
			try {
				if (Mobile.waitForElementPresent(object, timeout)) {
					Mobile.setText(object, input, timeout, FailureHandling.STOP_ON_FAILURE)
					break
				} else {
					throw new StepFailedException("Object $object not found")
				}
			} catch(StepFailedException error) {
				setElementStaleStatus(error, priority)
			}
		}
	}
	@Keyword
	public static void doubledTap(TestObject object, int timeout, FailureHandling priority = FailureHandling.STOP_ON_FAILURE){
		isElementStale = true
		while(isElementStale) {
			try {
				if (Mobile.waitForElementPresent(object, timeout)) {
					Mobile.tap(object, timeout, FailureHandling.STOP_ON_FAILURE)
					break
				} else {
					throw new StepFailedException("Object $object not found")
				}
			} catch(StepFailedException error) {
				setElementStaleStatus(error, priority)
			}
		}
	}
	@Keyword
	public static doubledVerifyElementText(TestObject object, String input, FailureHandling priority = FailureHandling.STOP_ON_FAILURE){
		isElementStale = true
		boolean value
		while(isElementStale) {
			try {
				if (Mobile.waitForElementPresent(object, GlobalVariable.globalTimeout)) {
					value = Mobile.verifyElementText(object, input, FailureHandling.STOP_ON_FAILURE)
					break
				} else {
					throw new StepFailedException("Object $object not found")
				}
			} catch(StepFailedException error) {
				setElementStaleStatus(error, priority)
			}
		}
		return value
	}
	@Keyword
	public static doubledWaitForElementPresent(TestObject object, int timeout){
		isElementStale = true
		boolean value
		while(isElementStale) {
			try {
				value = Mobile.waitForElementPresent(object, timeout)
				break
			} catch(StepFailedException error) {
				setElementStaleStatus(error, FailureHandling.OPTIONAL)
			}
		}
		return value
	}
	@Keyword
	public static doubledWaitForElementNotPresent(TestObject object, int timeout){
		isElementStale = true
		boolean value
		while(isElementStale) {
			try {
				value = Mobile.waitForElementNotPresent(object, timeout)
				break
			} catch(StepFailedException error) {
				setElementStaleStatus(error, FailureHandling.OPTIONAL)
			}
		}
		return value
	}
	@Keyword
	public static doubledWaitForElementAttributeValue(TestObject object, String attribute_name, String attribute_value, int timeout){
		isElementStale = true
		boolean value
		while(isElementStale) {
			try {
				value = Mobile.waitForElementAttributeValue(object, attribute_name, attribute_value, timeout)
				break
			} catch(StepFailedException error) {
				setElementStaleStatus(error, FailureHandling.OPTIONAL)
			}
		}
		return value
	}
	@Keyword
	public static doubledVerifyElementExist(TestObject object, int timeout, FailureHandling priority = FailureHandling.STOP_ON_FAILURE){
		isElementStale = true
		boolean value
		while(isElementStale) {
			try {
				if (Mobile.waitForElementPresent(object, timeout)) {
					value = Mobile.verifyElementExist(object, timeout, FailureHandling.STOP_ON_FAILURE)
					break
				} else {
					throw new StepFailedException("Object $object not found")
				}
			} catch(StepFailedException error) {
				setElementStaleStatus(error, priority)
			}
		}
		return value
	}
	@Keyword
	public static doubledVerifyElementNotExist(TestObject object, int timeout, FailureHandling priority = FailureHandling.STOP_ON_FAILURE){
		isElementStale = true
		boolean value
		while(isElementStale) {
			try {
				if (Mobile.waitForElementNotPresent(object, timeout)) {
					value = Mobile.verifyElementNotExist(object, timeout, FailureHandling.STOP_ON_FAILURE)
					break
				} else {
					throw new StepFailedException("Object $object not found")
				}
			} catch(StepFailedException error) {
				setElementStaleStatus(error, priority)
			}
		}
		return value
	}
	@Keyword
	public static doubledGetElementTopPosition(TestObject object, int timeout, FailureHandling priority = FailureHandling.STOP_ON_FAILURE){
		isElementStale = true
		int value
		while(isElementStale) {
			try {
				if (Mobile.waitForElementPresent(object, timeout)) {
					value = Mobile.getElementTopPosition(object, timeout, FailureHandling.STOP_ON_FAILURE)
					break
				} else {
					throw new StepFailedException("Object $object not found")
				}
			} catch(StepFailedException error) {
				setElementStaleStatus(error, priority)
			}
		}
		return value
	}
	@Keyword
	public static doubledGetElementLeftPosition(TestObject object, int timeout, FailureHandling priority = FailureHandling.STOP_ON_FAILURE){
		isElementStale = true
		int value
		while(isElementStale) {
			try {
				if (Mobile.waitForElementPresent(object, timeout)) {
					value = Mobile.getElementLeftPosition(object, timeout, FailureHandling.STOP_ON_FAILURE)
					break
				} else {
					throw new StepFailedException("Object $object not found")
				}
			} catch(StepFailedException error) {
				setElementStaleStatus(error, priority)
			}
		}
		return value
	}


	@Keyword
	public static void swipeUp() {
		int device_half_height = Math.round(Mobile.getDeviceHeight()/2)
		int device_half_width = Math.round(Mobile.getDeviceWidth()/2)
		int device_1_per_4_height = Math.round(device_half_height/2)
		int device_3_per_4_height = device_half_height + device_1_per_4_height
		Mobile.swipe(device_half_width, device_3_per_4_height, device_half_width, device_1_per_4_height)
	}
	@Keyword
	public static void swipeDown() {
		int device_half_height = Math.round(Mobile.getDeviceHeight()/2)
		int device_half_width = Math.round(Mobile.getDeviceWidth()/2)
		int device_1_per_4_height = Math.round(device_half_height/2)
		int device_3_per_4_height = device_half_height + device_1_per_4_height
		Mobile.swipe(device_half_width, device_1_per_4_height, device_half_width, device_3_per_4_height)
	}
}