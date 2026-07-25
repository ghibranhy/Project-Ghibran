import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import custom_library.TransactionalManager
import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

// Tangkap parameter test_case_name (fallback aman jika null)
String tcName = (binding.hasVariable('test_case_name') && test_case_name != null) ? test_case_name.toString().toLowerCase() : ''
int maxRetry = 8
int retryCount = 0
def order_id = null
def job_id = null
def resp = null

boolean isSchedule = tcName.contains('schedule')
String apiPath = isSchedule ? 'Object Repository/Simulator/On Going Order, Schedule' : 'Object Repository/Simulator/On Going Order'

KeywordUtil.logInfo("🔍 [Get Order ID] Endpoint: ${apiPath} | Target TC Name: ${tcName}")

// Looping hingga berhasil ambil order_id atau batas maxRetry
while (retryCount < maxRetry) {
	retryCount++
	KeywordUtil.logInfo("🔄 [Attempt ${retryCount}/${maxRetry}] Checking On-Going Order from MyBB API...")

	resp = WS.sendRequest(findTestObject(apiPath, [
		'token': TransactionalManager.getMyBBToken(),
		'app_version': GlobalVariable.APP_VERSION
	]))

	int statusCode = WS.getResponseStatusCode(resp)
	if (statusCode != 200 && statusCode != 201) {
		KeywordUtil.markErrorAndStop("❌ API On-Going Order HTTP status bukan 200/201. Status saat ini: " + statusCode)
	}

	String responseText = resp.getResponseText()
	KeywordUtil.logInfo("📡 API Response Body: " + responseText)

	try {
		def parsed = new JsonSlurper().parseText(responseText)
		def orderList = []

		// Support Dual Format:
		// Format A: parsed.data.records (Object/Array di dalam 'records')
		// Format B: parsed.data (Array langsung)
		if (parsed?.data?.records instanceof List && !parsed.data.records.isEmpty()) {
			orderList = parsed.data.records
		} else if (parsed?.data instanceof List && !parsed.data.isEmpty()) {
			orderList = parsed.data
		}

		if (!orderList.isEmpty()) {
			// Ambil order paling terbaru (indeks pertama)
			def latestOrder = orderList[0]
			order_id = latestOrder?.order_id
			job_id = latestOrder?.job_id ?: latestOrder?.external_order_id ?: order_id

			if (order_id != null && !order_id.toString().isEmpty()) {
				GlobalVariable.order_id = order_id
				TransactionalManager.setOrderIDMybb(order_id.toString())
				TransactionalManager.setOrderID(job_id.toString())

				WS.comment("✅ Order ID (MyBB): ${order_id}")
				WS.comment("📦 Job ID (VINI/BBD): ${job_id}")
				KeywordUtil.logInfo("✅ BERHASIL Ambil Order ID: ${order_id} | Job ID: ${job_id} pada percobaann ke-${retryCount}")
				break
			}
		}
	} catch (Exception e) {
		KeywordUtil.logInfo("⚠️ Parsing JSON Error pada percobaan ke-${retryCount}: " + e.getMessage())
	}

	KeywordUtil.logInfo("⚠️ Order belum ter-render di backend. Delaying 3s before retry...")
	WS.delay(3)
}

if (order_id == null) {
	KeywordUtil.markErrorAndStop("❌ Gagal ambil order_id / job_id setelah ${maxRetry} percobaan. Pastikan transaksi di MyBB berhasil dibuat!")
}