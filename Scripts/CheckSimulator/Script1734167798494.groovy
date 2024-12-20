import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import custom_library.TransactionalManager
import groovy.ui.SystemOutputInterceptor
import custom_library.SimulatorDataManager
import internal.GlobalVariable

def pairing_driver = "0"
int count = 0

while(pairing_driver != "1" && count < 2) {
	SimulatorDataManager.createSession()
	SimulatorDataManager.simulatorResourceBluebird()
	SimulatorDataManager.uploadSio()
	SimulatorDataManager.iotLogin()
	SimulatorDataManager.driverLogin()
	SimulatorDataManager.driverPairing()
	SimulatorDataManager.checkStatistics()
	
	pairing_driver = TransactionalManager.getPairingDriver()
	
	count++ ;
}
System.out.print(pairing_driver)
if (pairing_driver == "0" ) {
	System.exit(0)
}

