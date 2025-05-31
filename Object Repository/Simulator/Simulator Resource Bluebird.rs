<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>Simulator Resource Bluebird</name>
   <tag></tag>
   <elementGuidId>1f428ae6-70c2-4174-845d-bc7731e23622</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n    \&quot;session_id\&quot;: \&quot;${session_id}\&quot;,\n    \&quot;num_vehicle_driver\&quot;: 1,\n    \&quot;service_id\&quot;: 1,\n    \&quot;vehicle_speed\&quot;: 20,\n    \&quot;track_interval\&quot;: 20,\n    \&quot;job_confirm_pct\&quot;: 100,\n    \&quot;offer_bid_pct\&quot;: 100,\n    \&quot;auto_bid\&quot;: true,\n    \&quot;auto_iot\&quot;: false,\n    \&quot;center_loc\&quot;: {\n        \&quot;lat\&quot;: -6.081969705141279,\n        \&quot;lng\&quot;: 106.67869753502303,\n        \&quot;radius\&quot;: 100\n    },\n    \&quot;track_param\&quot;: 20,\n    \&quot;flag_odrd\&quot;: false,\n    \&quot;vehicle_type\&quot;: 0\n}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>a185b3e3-8fa0-4adf-bcc1-5c890095801b</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>8.6.8</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>https://regressapi4.bluebird.id/simulator-director/simulator</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()

ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
