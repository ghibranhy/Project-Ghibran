import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import custom_library.TransactionalManager
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable as GlobalVariable
import internal.GlobalVariable

mybb_version = GlobalVariable.APP_VERSION
mobile_phone = GlobalVariable.USER_PHONE_DEFAULT
password = GlobalVariable.USER_PASS_DEFAULT
token = ''

// Step 1: Validate User
def resp = null
int attempt = 0
boolean isSuccess = false

while (true) {
	attempt++
	KeywordUtil.logInfo("🔄 [PERCOBAAN ${attempt}] Mencoba validasi user untuk HP: ${mobile_phone}")
	
	try {
		resp = WS.sendRequest(findTestObject('Object Repository/Simulator/Validate User', [
			'phone_number': mobile_phone,
			'app_version': mybb_version
		]))
		
		if (resp != null) {
			int statusCode = WS.getResponseStatusCode(resp)
			String responseText = resp.getResponseBodyContent()
			KeywordUtil.logInfo("📡 [PERCOBAAN ${attempt}] Server Response Code: ${statusCode} | Body: ${responseText}")
			
			if (statusCode == 200) {
				token = WS.getElementPropertyValue(resp, 'data.session_token')
				if (token != null && !token.isEmpty()) {
					WS.comment("✨ [SUKSES] Session Token Berhasil Didapat pada percobaan ke-${attempt}: ${token}")
					isSuccess = true
					break // Keluar paksa dari infinite loop karena data sudah dapet
				}
			}
		}
	} catch (Exception e) {
		KeywordUtil.logInfo("⚠️ [PERCOBAAN ${attempt}] Error koneksi/parsing: ${e.getMessage()}")
	}
	
	// Jika belum sukses, paksa delay 4 detik lalu biarkan loop berputar kembali
	KeywordUtil.logInfo("⏳ Data belum siap/gagal. Menunggu 4 detik sebelum mencoba lagi...")
	WS.delay(4)
}

// STEP 2: AUTHENTICATION

KeywordUtil.logInfo("🔑 Mengirim data autentikasi final ke simulator...")
def resp_auth = WS.sendRequest(findTestObject('Object Repository/Simulator/Auth', [
	'password': password,
	'app_version': mybb_version,
	'session_token': token
]))

if (WS.getResponseStatusCode(resp_auth) != 200) {
	KeywordUtil.markErrorAndStop("❌ Status code Auth bukan 200. Respon: ${WS.getResponseStatusCode(resp_auth)}")
} else {
	try {
		token = WS.getElementPropertyValue(resp_auth, 'data.token')
		TransactionalManager.setMyBBToken(token)
		WS.comment("✅ Final Token: ${token}")

		String bbid = WS.getElementPropertyValue(resp_auth, 'data.user_id')
		TransactionalManager.setMyBBID(bbid)
		WS.comment("📱 BB ID: ${bbid}")

		String customer_name = WS.getElementPropertyValue(resp_auth, 'data.name')
		WS.comment("👤 Nama Customer: ${customer_name}")
	} catch (Exception e) {
		KeywordUtil.markErrorAndStop("\n\n❌ ERROR saat parsing data auth: $e\n")
	}
}