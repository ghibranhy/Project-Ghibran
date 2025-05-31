package custom_library

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject

import custom_library.TransactionalManager

import com.kms.katalon.core.annotation.Keyword

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import internal.GlobalVariable

public class SimulatorDataManager {
	public static createSession() {
		WS.delay(3)
		def resp = WS.sendRequest(findTestObject('Object Repository/Simulator/Create Session'))

		if(WS.getResponseStatusCode(resp) != 200) {
			KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
		} else {
			try {
				def session = WS.getElementPropertyValue(resp, 'session_id')
				WS.comment("Emulator's Session ID: $session")
				TransactionalManager.setBBDSimulatorSession(session)
			} catch (Exception error) {
				KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
			}
		}
	}
	public static simulatorResourceBluebird() {
		WS.delay(3)
		def session
		ResponseObject resp
		session = TransactionalManager.getBBDSimulatorSession()
		if(session != null) {
			resp = WS.sendRequest(findTestObject('Object Repository/Simulator/Simulator Resource Bluebird',
					[
						'session_id': session
					]))
		}
		if(resp.getStatusCode()!= 200) {
			KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
		} else {
			try {

				WS.comment(resp.getResponseText())
				GlobalVariable.simulatorResource = resp.getResponseText()
				WS.comment(GlobalVariable.simulatorResource)
				def nomor_lambung = WS.getElementPropertyValue(resp, 'vehicle_driver[0].vehicle_no')
				def driver_id = WS.getElementPropertyValue(resp, 'vehicle_driver[0].driver_id')
				def vehicle_id = WS.getElementPropertyValue(resp, 'vehicle_driver[0].vehicle_id')
				def driver_pass = WS.getElementPropertyValue(resp, 'vehicle_driver[0].driver_pass')
				def device_id = WS.getElementPropertyValue(resp, 'vehicle_driver[0].device_id')
				WS.comment(nomor_lambung)
				WS.comment(driver_id)
				WS.comment(vehicle_id)
				WS.comment(driver_pass)
				WS.comment(device_id)
				TransactionalManager.setBBDSimulatorNomorLambung(nomor_lambung)
				TransactionalManager.setBBDSimulatorDriverID(driver_id)
				TransactionalManager.setBBDSimulatorVehicleID(vehicle_id)
				TransactionalManager.setBBDSimulatorDriverPass(driver_pass)
				TransactionalManager.setBBDSimulatorDeviceID(device_id)
			} catch (Exception error) {
				KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
			}
		}
	}
	public static simulatorResourceSilverbird() {
		WS.delay(3)
		def session
		ResponseObject resp
		session = TransactionalManager.getBBDSimulatorSession()
		if(resp.getStatusCode()!= 200) {
			KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
		} else {
			try {

				WS.comment(resp.getResponseText())
				GlobalVariable.simulatorResource = resp.getResponseText()
				WS.comment(GlobalVariable.simulatorResource)
				def nomor_lambung = WS.getElementPropertyValue(resp, 'vehicle_driver[0].vehicle_no')
				def driver_id = WS.getElementPropertyValue(resp, 'vehicle_driver[0].driver_id')
				def vehicle_id = WS.getElementPropertyValue(resp, 'vehicle_driver[0].vehicle_id')
				def driver_pass = WS.getElementPropertyValue(resp, 'vehicle_driver[0].driver_pass')
				def device_id = WS.getElementPropertyValue(resp, 'vehicle_driver[0].device_id')
				WS.comment(nomor_lambung)
				WS.comment(driver_id)
				WS.comment(vehicle_id)
				WS.comment(driver_pass)
				WS.comment(device_id)
				TransactionalManager.setBBDSimulatorNomorLambung(nomor_lambung)
				TransactionalManager.setBBDSimulatorDriverID(driver_id)
				TransactionalManager.setBBDSimulatorVehicleID(vehicle_id)
				TransactionalManager.setBBDSimulatorDriverPass(driver_pass)
				TransactionalManager.setBBDSimulatorDeviceID(device_id)
			} catch (Exception error) {
				KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
			}
		}
	}
	public static uploadSio() {
		WS.delay(3)
		def resp = WS.sendRequest(findTestObject('Object Repository/Simulator/Upload Sios'))

		if(WS.getResponseStatusCode(resp) != 200) {
			KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
		} else {
			try {
				WS.comment("Status is Passed" + + WS.getResponseStatusCode(resp))
				def message = WS.getElementPropertyValue(resp, 'message')
				WS.comment('message' + message)
			} catch (Exception error) {
				KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
			}
		}
	}
	public static iotLogin() {
		WS.delay(3)
		def resp = WS.sendRequest(findTestObject('Object Repository/Simulator/IoT Login'))


		if(WS.getResponseStatusCode(resp) != 200) {
			KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
		} else {
			try {
				WS.comment("Status is Passed" + + WS.getResponseStatusCode(resp))
				def message = WS.getElementPropertyValue(resp, 'message')
				WS.comment('message' + message)
			} catch (Exception error) {
				KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
			}
		}
	}
	public static driverLogin() {
		WS.delay(3)
		def resp = WS.sendRequest(findTestObject('Object Repository/Simulator/Driver Login'))


		if(WS.getResponseStatusCode(resp) != 200) {
			KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
		} else {
			try {
				WS.comment("Status is Passed" + + WS.getResponseStatusCode(resp))
				def message = WS.getElementPropertyValue(resp, 'message')
				WS.comment('message' + message)
			} catch (Exception error) {
				KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
			}
		}
	}
	public static driverPairing() {
		WS.delay(3)
		def resp = WS.sendRequest(findTestObject('Object Repository/Simulator/Driver Pairing'))


		if(WS.getResponseStatusCode(resp) != 200) {
			KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
		} else {
			try {
				WS.comment("Status is Passed" + + WS.getResponseStatusCode(resp))
				def message = WS.getElementPropertyValue(resp, 'message')
				WS.comment('message' + message)
			} catch (Exception error) {
				KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
			}
		}
	}
	public static checkStatistics() {
		WS.delay(5)
		def session = TransactionalManager.getBBDSimulatorSession()
		WS.comment(session)

		def resp = WS.sendRequest(findTestObject('Object Repository/Simulator/Statistics',
				[
					'session_id': session
				]))

		if(WS.getResponseStatusCode(resp) != 200) {
			KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
		} else {
			def pairing_driver = WS.getElementPropertyValue(resp, 'field_driver_pairing_success')
			WS.comment(pairing_driver)
			def pairing_driver_success = pairing_driver
			TransactionalManager.setPairingDriver(pairing_driver_success)
			if(pairing_driver_success == null || pairing_driver_success == "0" ) {
				KeywordUtil.markFailedAndStop("driver not pairing" + ' '+ pairing_driver)
			} else {
				KeywordUtil.markPassed("Succes pairing driver" + " " +pairing_driver)
			}
		}
	}

	public static invalidSession() {
		WS.delay(5)
		def session = TransactionalManager.getBBDSimulatorSession()
		WS.comment(session)

		def resp = WS.sendRequest(findTestObject('Object Repository/Simulator/InvalidSession',
				[
					'session_id': session
				]))

		if(WS.getResponseStatusCode(resp) != 200) {
			KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
		} else {
			KeywordUtil.markPassed("Succes invalid session" + " " +session)
		}
	}



	public static iotEvent() {
		WS.delay(3)
		def resp = WS.sendRequest(findTestObject('Object Repository/Simulator/IoT Event'))
		if(WS.getResponseStatusCode(resp) != 200 && WS.getResponseStatusCode(resp) != 500) {
			KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
		} else {
			try {
				if(WS.getResponseStatusCode(resp) == 500) {
					def message = WS.getElementPropertyValue(resp, 'message')
					WS.comment('message'+ ' ' + message)
					TransactionalManager.setIotState(message)
				} else {
					WS.comment("Status is Passed" + + WS.getResponseStatusCode(resp))
					def message = WS.getElementPropertyValue(resp, 'message')
					WS.comment('message' + message)
				}
			} catch (Exception error) {
				KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
			}
		}
		WS.delay(10)
	}
	public static IOtState() {
		def resp = WS.sendRequest(findTestObject('Object Repository/Simulator/Get IoT State'))
		if(WS.getResponseStatusCode(resp) != 200 && WS.getResponseStatusCode(resp) != 500) {
			KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
		} else {
			try {
				WS.comment("Status is Passed" + + WS.getResponseStatusCode(resp))
				if(WS.getResponseStatusCode(resp) == 500) {
					def message = WS.getElementPropertyValue(resp, 'message')
					WS.comment('message'+ ' ' + message)
					TransactionalManager.setIotState(message)
				} else {
					def state = WS.getElementPropertyValue(resp, 'state_string')
					WS.comment('state' + state , )
					TransactionalManager.setIotState(state)
					WS.delay(3)
				}
			} catch (Exception error) {
				KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
			}
		}
	}
}
