import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import custom_library.MobileHelper
import custom_library.TransactionalManager
import internal.GlobalVariable as GlobalVariable
Mobile.startExistingApplication('com.seatech.bluebird.regress')


//// 2. Eksekusi Pembuatan Pesanan (Create Order)
//MobileHelper.tapCreateOrderButton()
//Mobile.delay(8)

// 3. Menyiapkan Parameter Pengujian Simulator
String testCaseName = TransactionalManager.getMyBBTestCaseName()?.toLowerCase() ?: ''
String folderCaseName = TransactionalManager.getMyBBFolderCaseName()?.toLowerCase() ?: ''
String vehicleType = 'Delivery BB'

// 4. Inisialisasi Token & Order ID via Web Service Simulator
WS.callTestCase(findTestCase('Test Cases/Simulator/Get Token'), ['vehicle_type': vehicleType], FailureHandling.STOP_ON_FAILURE)
WS.callTestCase(findTestCase('Test Cases/Simulator/Get Order ID'), ['vehicle_type': vehicleType, 'test_case_name': testCaseName], FailureHandling.STOP_ON_FAILURE)

//// 5. Perubahan Status Order: ENROUTE (1)
//Mobile.comment("Mengubah status orderan: ENROUTE (1)")
//WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status Enroute'), [:], FailureHandling.STOP_ON_FAILURE)
//Mobile.delay(6)
//
//// 6. Perubahan Status Order: ARRIVED (4)
//Mobile.comment("Mengubah status orderan: ARRIVED (4)")
//WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status Arrived'), [:], FailureHandling.STOP_ON_FAILURE)
//Mobile.delay(3)
//
//// 7. Perubahan Status Order: ON TRIP (6)
//Mobile.comment("Mengubah status orderan: ON TRIP (6)")
//WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status On Trip'), [:], FailureHandling.STOP_ON_FAILURE)
//Mobile.delay(5)

// 8. Perubahan Status Order: COMPLETE CASH (7)
Mobile.comment("Mengubah status orderan: COMPLETE CASH (7)")
WS.callTestCase(findTestCase('Test Cases/Simulator/Set Status Complete Order Cash'), [:], FailureHandling.STOP_ON_FAILURE)
Mobile.delay(5)

// 9. Ambil Bukti Eksekusi (Screenshot)
Mobile.takeScreenshot()