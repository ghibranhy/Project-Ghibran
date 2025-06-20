import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import custom_library.TransactionalManager
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable as GlobalVariable
import internal.GlobalVariable

def mybb_version = GlobalVariable.mybb_app_version
def mobile_phone = GlobalVariable.activePhoneNumber
def password = GlobalVariable.activePassword
def token

//validate user
resp = WS.sendRequest(findTestObject('Object Repository/Simulator/Validate User',
	[
		'phone_number':mobile_phone,
		'app_version':mybb_version
	]))

if (WS.getResponseStatusCode(resp) != 200) {
	KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp))
} else {
	try {
		token = WS.getElementPropertyValue(resp, 'data.session_token')
		WS.comment(token)
	} catch (Exception error) {
		KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
	}
}

//auth
resp_auth = WS.sendRequest(findTestObject('Object Repository/Simulator/Auth',
	[
		'password':password,
		'app_version':mybb_version,
		'session_token':token
		]))

if (WS.getResponseStatusCode(resp_auth) != 200) {
	KeywordUtil.markErrorAndStop("Status code is not 200 as expected. It is " + WS.getResponseStatusCode(resp_auth))
} else {
	try {
		token = WS.getElementPropertyValue(resp_auth, 'data.token')
		TransactionalManager.setMyBBToken(token)
		WS.comment(token)
		bbid = WS.getElementPropertyValue(resp_auth, 'data.user_id')
		
		TransactionalManager.setMyBBID(bbid)
		WS.comment(bbid)
		
		customer_name = WS.getElementPropertyValue(resp_auth, 'data.name')
		WS.comment(customer_name)
	} catch (Exception error) {
		KeywordUtil.markErrorAndStop("\n\nERROR: $error\n")
	}
}
