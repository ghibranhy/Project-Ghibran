import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable as GlobalVariable
import custom_library.TransactionalManager
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ResponseObject


// Ambil Order ID dari TransactionalManager
String orderId = TransactionalManager.getOrderID()
WS.comment("ℹ️ Memakai OrderID dari TransactionalManager: " + orderId)

// Ambil test object Get Order Id + inject orderId
TestObject getOrderObj = findTestObject(
    'Simulator/Get Order Id',
    [('order_id') : orderId]   // ini inject ke ${order_id}
)

// Retry maksimal 10x
int maxRetry = 10
int interval = 5 // detik
int status = -1

for (int i = 1; i <= maxRetry; i++) {
    def resp = WS.sendRequest(getOrderObj)

    // Pastikan response OK
    WS.verifyResponseStatusCode(resp, 200)

    status = WS.getElementPropertyValue(resp, 'data.status')

    KeywordUtil.logInfo("🔄 Percobaan ke-${i} | OrderID = ${orderId} | Status = ${status}")

    if (status == 8) {
        KeywordUtil.markPassed("✅ Order sudah status 8, siap lanjut test berikutnya.")
        break
    } else {
        if (i == maxRetry) {
            KeywordUtil.markFailedAndStop("❌ Order belum mencapai status 8 setelah ${maxRetry}x percobaan.")
        } else {
            KeywordUtil.logInfo("⏳ Status masih ${status}, tunggu ${interval} detik lalu retry...")
            Thread.sleep(interval * 1000)
        }
    }
}