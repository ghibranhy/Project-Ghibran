import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import custom_library.TransactionalManager
import com.kms.katalon.core.util.KeywordUtil

// 1. Ambil & Potong ID
String jobIdAsli = TransactionalManager.getOrderID()
if (jobIdAsli == null || jobIdAsli.isEmpty()) {
	jobIdAsli = "1200172965"
}
String orderIdCustom = jobIdAsli.length() >= 3 ? (jobIdAsli.substring(0, 1) + jobIdAsli.substring(2)) : jobIdAsli

// 2. Tangkap Variabel Payment
String epayValue = "1" // Default ECV
if (binding.hasVariable('epay_value') && epay_value != null) {
	epayValue = epay_value.toString()
}
String stateValue = "6" // 6 untuk On Trip

KeywordUtil.logInfo("========== [SIMULATOR] SET STATUS ON TRIP ==========")
KeywordUtil.logInfo("📱 Order ID : ${orderIdCustom} | 💳 E-Pay Flag : ${epayValue}")

// 3. Setup Request Object
RequestObject ro = new RequestObject('SOAP_State_6_Dynamic')
ro.setRestUrl('https://regress-mybb-gw.bluebird.id/soap/itop/service')
ro.setRestRequestMethod('POST')

List<TestObjectProperty> httpHeaders = new ArrayList<TestObjectProperty>()
httpHeaders.add(new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/xml'))
httpHeaders.add(new TestObjectProperty('Accept', ConditionType.EQUALS, 'application/xml'))
ro.setHttpHeaderProperties(httpHeaders)

// 4. Rakit XML
String innerOrderInfo = "&lt;set&gt;&lt;orderInfo&gt;" +
	"&lt;orderId&gt;" + orderIdCustom + "&lt;/orderId&gt;" +
	"&lt;itopId&gt;021&lt;/itopId&gt;" +
	"&lt;state&gt;" + stateValue + "&lt;/state&gt;" +
	"&lt;carNo&gt;TST0000&lt;/carNo&gt;" +
	"&lt;carLatitude&gt;-6.246607457753482&lt;/carLatitude&gt;" +
	"&lt;carLongitude&gt;106.82582447338763&lt;/carLongitude&gt;" +
	"&lt;carPhone&gt;+6288877776666&lt;/carPhone&gt;" +
	"&lt;driverId&gt;TEST0000&lt;/driverId&gt;" +
	"&lt;driverName&gt;Driver Tester&lt;/driverName&gt;" +
	"&lt;photoPath&gt;https://ui-avatars.com/api/?name=Mr+Tester&lt;/photoPath&gt;" +
	"&lt;driverRate&gt;5&lt;/driverRate&gt;" +
	"&lt;epayFlag&gt;" + epayValue + "&lt;/epayFlag&gt;" +
	"&lt;finalEpay&gt;" + epayValue + "&lt;/finalEpay&gt;" +
	"&lt;passCodeFail&gt;0&lt;/passCodeFail&gt;" +
	"&lt;tokenNr&gt;&lt;/tokenNr&gt;" +
"&lt;/orderInfo&gt;&lt;/set&gt;"

String xmlPayload = '<x:Envelope xmlns:x="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sil="http://siliconstraits.com">' +
	'<x:Header/>' +
	'<x:Body>' +
	  '<sil:sendOrderInfo>' +
	  '<sil:orderInfo>' + innerOrderInfo + '</sil:orderInfo>' +
	  '</sil:sendOrderInfo>' +
	'</x:Body>' +
'</x:Envelope>'

ro.setBodyContent(new HttpTextBodyContent(xmlPayload, "UTF-8", "application/xml"))

// 5. Kirim Request
def response = WS.sendRequest(ro)
KeywordUtil.logInfo("RESPONSE FROM SERVER: " + response.getResponseBodyContent())
WS.verifyResponseStatusCode(response, 200)