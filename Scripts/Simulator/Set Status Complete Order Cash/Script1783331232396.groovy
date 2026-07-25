import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import custom_library.TransactionalManager
import com.kms.katalon.core.util.KeywordUtil

// 1. Ambil & Potong ID secara dinamis dari TransactionalManager
String jobIdAsli = TransactionalManager.getOrderID()
if (jobIdAsli == null || jobIdAsli.isEmpty()) {
	jobIdAsli = "10200173004" // Nomor job id aktif untuk debug mandiri
	println "⚠️ Menggunakan Job ID Hardcode: ${jobIdAsli}"
}

// Konfigurasi Spesifik untuk Payment Cash
String stateValue = "7"         // 7 untuk Complete Cash
String epayValue = "0"          // 0 untuk Cash
String businessPrice = "50000.0"

String orderIdCustom = jobIdAsli.substring(0, 1) + jobIdAsli.substring(2)

println "========== MANIPULASI FINISH ORDER - CASH (STATE 7) UNTUK ID: ${orderIdCustom} =========="

// 2. Setup Request Object & Headers
RequestObject ro = new RequestObject('SOAP_Complete_Cash')
ro.setRestUrl('https://regress-mybb-gw.bluebird.id/soap/itop/service')
ro.setRestRequestMethod('POST')

List<TestObjectProperty> httpHeaders = new ArrayList<TestObjectProperty>()
httpHeaders.add(new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/xml'))
httpHeaders.add(new TestObjectProperty('Accept', ConditionType.EQUALS, 'application/xml'))
ro.setHttpHeaderProperties(httpHeaders)

// 3. Rakit XML sendBusinessFinishInfo dengan Escaped Entities murni satu baris penuh
String xmlPayload = '<x:Envelope xmlns:x="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sil="http://siliconstraits.com">' +
	'<x:Header/>' +
	'<x:Body>' +
	  '<sil:sendBusinessFinishInfo>' +
	    '<sil:orderInfo>' +
	      '&lt;orderInfo&gt;&lt;orderId&gt;' + orderIdCustom + '&lt;/orderId&gt;&lt;itopId&gt;021&lt;/itopId&gt;&lt;finalEpay&gt;' + epayValue + '&lt;/finalEpay&gt;&lt;epayFlag&gt;' + epayValue + '&lt;/epayFlag&gt;&lt;state&gt;' + stateValue + '&lt;/state&gt;&lt;carNo&gt;TST0000&lt;/carNo&gt;&lt;taxiCat&gt;Blue Bird&lt;/taxiCat&gt;&lt;carLatitude&gt;-6.246607457753482&lt;/carLatitude&gt;&lt;carLongitude&gt;106.82582447338763&lt;/carLongitude&gt;&lt;carPhone&gt;+6288877776666&lt;/carPhone&gt;&lt;driverId&gt;TEST0000&lt;/driverId&gt;&lt;driverName&gt;Mr. Tester&lt;/driverName&gt;&lt;photoPath&gt;https://ui-avatars.com/api/?name=Mr+Tester&lt;/photoPath&gt;&lt;driverRate&gt;5&lt;/driverRate&gt;&lt;businessPrice&gt;' + businessPrice + '&lt;/businessPrice&gt;&lt;businessExtra&gt;0&lt;/businessExtra&gt;&lt;businessFinalPrice&gt;' + businessPrice + '&lt;/businessFinalPrice&gt;&lt;businessKilo&gt;1.0&lt;/businessKilo&gt;&lt;beginTime&gt;&lt;/beginTime&gt;&lt;endTime&gt;&lt;/endTime&gt;&lt;pointCount&gt;0&lt;/pointCount&gt;&lt;/orderInfo&gt;' +
	    '</sil:orderInfo>' +
	  '</sil:sendBusinessFinishInfo>' +
	'</x:Body>' +
'</x:Envelope>'

ro.setBodyContent(new HttpTextBodyContent(xmlPayload, "UTF-8", "application/xml"))

// 4. Kirim Request ke Gateway
def response = WS.sendRequest(ro)

// 5. Verifikasi & Intip Log Response
KeywordUtil.logInfo("RESPONSE FROM SERVER: " + response.getResponseBodyContent())
WS.verifyResponseStatusCode(response, 200)