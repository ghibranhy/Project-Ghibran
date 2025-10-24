import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable as GlobalVariable
import custom_library.TransactionalManager

// Hit API Get Orders List
def response = WS.sendRequest(findTestObject('Object Repository/Simulator/Get List Order'))

// Verify response OK
WS.verifyResponseStatusCode(response, 200)

// Ambil order_id paling baru dari list
String latestOrderId = WS.getElementPropertyValue(response, 'data[0].order_id')
def status = WS.getElementPropertyValue(response, 'data[0].status')

// Logging buat monitor
KeywordUtil.logInfo("🆕 Latest OrderID = ${latestOrderId} | Status = ${status}")

// Simpan ke TransactionalManager
TransactionalManager.setOrderID(latestOrderId)

// Optional juga bisa simpan ke GlobalVariable kalau perlu
GlobalVariable.orderId = latestOrderId

KeywordUtil.markPassed("✅ Berhasil ambil OrderID terbaru: ${latestOrderId}")
