import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import custom_library.TransactionalManager
import custom_library.SimulatorDataManager
import internal.GlobalVariable

// Definisikan expected_status langsung di sini
def expected_status = 'start' 

// Logic dari test case kedua, langsung jalanin disini
WS.delay(3)
def globalLoadingSafe = GlobalVariable.globalLoading ?: 1
long timeout = new Date().getTime() + (9000 * globalLoadingSafe)

switch (expected_status) {
	case "start":
		SimulatorDataManager.IOtState()
		def state = TransactionalManager.getIotState()
		while(state != expected_status && new Date().getTime() < timeout) {
			WS.delay(3)
			SimulatorDataManager.iotEvent() 
			SimulatorDataManager.IOtState()
			state = TransactionalManager.getIotState()
			WS.comment(state)
		}
		WS.comment(state + ' = ' + expected_status)
		break	
	case "stop":
		// expected_payment = GlobalVariable.activeEvoucher
		break
	case "complete":
		def state = TransactionalManager.getIotState()
		def state1 = 'start'
		def state2 = 'stop'
		while((state == state1 || state == state2) && new Date().getTime() < timeout) {
			WS.comment('state is not finished') 
			SimulatorDataManager.iotEvent()
			WS.delay(3) 
			SimulatorDataManager.IOtState()
			state = TransactionalManager.getIotState()
			WS.comment(state)
		}
		WS.comment('state is = ' + state)
		break
}
