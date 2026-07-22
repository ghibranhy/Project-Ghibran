import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import custom_library.TransactionalManager
import com.kms.katalon.core.util.KeywordUtil

// 1. Ambil & Potong ID secara dinamis
String jobIdAsli = TransactionalManager.getOrderID()
if (jobIdAsli == null || jobIdAsli.isEmpty()) {
	jobIdAsli = "" // Ganti dengan nomor job id aktif untuk debug mandiri
	println "⚠️ Menggunakan Job ID Hardcode: ${jobIdAsli}"
}
String stateValue = "4" // 4 untuk Driver Arrived
String orderIdCustom = jobIdAsli.substring(0, 1) + jobIdAsli.substring(2)

println "========== MANIPULASI STATE 4 (ARRIVED) UNTUK ID ESCAPED: ${orderIdCustom} =========="

// 2. Setup Request Object & Headers
RequestObject ro = new RequestObject('SOAP_State_4')
ro.setRestUrl('https://regress-mybb-gw.bluebird.id/soap/itop/service')
ro.setRestRequestMethod('POST')

List<TestObjectProperty> httpHeaders = new ArrayList<TestObjectProperty>()
httpHeaders.add(new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/xml'))
httpHeaders.add(new TestObjectProperty('Accept', ConditionType.EQUALS, 'application/xml'))
ro.setHttpHeaderProperties(httpHeaders)

// 3. Payload dengan Escaped Entities murni
String xmlPayload = '<x:Envelope xmlns:x="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sil="http://siliconstraits.com">' +
	'<x:Header/>' +
	'<x:Body>' +
	  '<sil:sendOrderInfo>' +
	  '<sil:orderInfo>' +
	    '&lt;set&gt;&lt;orderInfo&gt;&lt;orderId&gt;' + orderIdCustom + '&lt;/orderId&gt;&lt;itopId&gt;021&lt;/itopId&gt;&lt;state&gt;' + stateValue + '&lt;/state&gt;&lt;carNo>TST0000&lt;/carNo&gt;&lt;carLatitude&gt;-6.246607457753482&lt;/carLatitude&gt;&lt;carLongitude&gt;106.82582447338763&lt;/carLongitude&gt;&lt;carPhone&gt;+6288877776666&lt;/carPhone&gt;&lt;driverId&gt;TEST0000&lt;/driverId&gt;&lt;driverName&gt;Driver Tester&lt;/driverName&gt;&lt;photoPath&gt;https://ui-avatars.com/api/?name=Mr+Tester&lt;/photoPath&gt;&lt;driverRate&gt;5&lt;/driverRate&gt;&lt;epayFlag&gt;0&lt;/epayFlag&gt;&lt;finalEpay&gt;0&lt;/finalEpay&gt;&lt;passCodeFail&gt;0&lt;/passCodeFail&gt;&lt;tokenNr&gt;&lt;/tokenNr&gt;&lt;/orderInfo&gt;&lt;/set&gt;' +
	  '</sil:orderInfo>InsufficientBalance' +
	  '</sil:sendOrderInfo>' +
	'</x:Body>' +
'</x:Envelope>'

ro.setBodyContent(new HttpTextBodyContent(xmlPayload, "UTF-8", "application/xml"))

// 4. Kirim Request
def response = WS.sendRequest(ro)

// 5. Verifikasi response
KeywordUtil.logInfo("RESPONSE FROM SERVER: " + response.getResponseBodyContent())
WS.verifyResponseStatusCode(response, 200)