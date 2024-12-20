<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>Add Extra</name>
   <tag></tag>
   <elementGuidId>cb9b518b-1aea-4b12-93d6-c2024ec2b67d</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\&quot;session_id\&quot;:\&quot;${session_id}\&quot;,\n\&quot;vehicle_driver\&quot;:[\n    {\n    \&quot;session_id\&quot;:\&quot;${session_id}\&quot;,\n    \&quot;vehicle_id\&quot;:\&quot;${vehicle_id}\&quot;,\n    \&quot;vehicle_no\&quot;:\&quot;${vehicle_no}\&quot;,\n    \&quot;driver_id\&quot;:\&quot;${driver_id}\&quot;,\n    \&quot;driver_pass\&quot;:\&quot;${driver_pass}\&quot;,\n    \&quot;device_id\&quot;:\&quot;${device_id}\&quot;,\n    \&quot;job_confirm_pct\&quot;:\&quot;100\&quot;,\n    \&quot;offer_bid_pct\&quot;:\&quot;100\&quot;,\n    \&quot;auto_bid\&quot;:true,\n    \&quot;auto_iot\&quot;:false,\n    \&quot;center_loc\&quot;:{\n        \&quot;lat\&quot;:-6.30255,\n        \&quot;lng\&quot;:106.64525,\n        \&quot;radius\&quot;:\&quot;100\&quot;\n        },\n        \&quot;track_param\&quot;:\&quot;20\&quot;,\n        \&quot;flag_odrd\&quot;:false,\n        \&quot;validate_package\&quot;:false\n    }\n    ],\n    \&quot;fare\&quot;: 25000,\n    \&quot;extra\&quot;: 10000\n    }&quot;,
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
      <webElementGuid>c0c48811-387f-409f-8f6a-89c1c41467a0</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>8.6.8</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>https://regressapi4.bluebird.id/simulator-director/fare-extra</restUrl>
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
