import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.RestRequestObjectBuilder
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

String baseUrl = "https://reqres.in/api"
JsonSlurper slurper = new JsonSlurper()

// Header standar (termasuk free API key reqres.in)
List<TestObjectProperty> defaultHeaders = [
	new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json"),
	new TestObjectProperty("Accept", ConditionType.EQUALS, "application/json"),
	new TestObjectProperty("x-api-key", ConditionType.EQUALS, "reqres-free-v1")
]

// =========================================================================
// 1. POST CREATE USER (Consumer mengirim payload -> Producer mengembalikan 201)
// =========================================================================
Map createPayload = [
	name: "Budi Atmoko",
	job : "QA Automation Engineer"
]

RequestObject postReq = new RestRequestObjectBuilder()
	.withRestUrl("${baseUrl}/users")
	.withHttpHeaders(defaultHeaders)
	.withRestRequestMethod("POST")
	.withTextBodyContent(JsonOutput.toJson(createPayload), "UTF-8")
	.build()

ResponseObject postRes = WS.sendRequest(postReq)

// Verifikasi Kontrak Producer (Status Code, Schema & Data)
WS.verifyResponseStatusCode(postRes, 201)
Map postData = (Map) slurper.parseText(postRes.getResponseText())
assert postData.name == "Budi Atmoko"
assert postData.job == "QA Automation Engineer"
assert postData.id != null
assert postData.createdAt != null

// Consumer mengekstrak ID dari Producer untuk request selanjutnya
String createdUserId = postData.id.toString()
KeywordUtil.logInfo("Consumer berhasil mengekstrak User ID: " + createdUserId)

// =========================================================================
// 2. GET LIST & SINGLE USER (Verifikasi struktur data Producer)
// =========================================================================
RequestObject getReq = new RestRequestObjectBuilder()
	.withRestUrl("${baseUrl}/users/2")
	.withHttpHeaders(defaultHeaders)
	.withRestRequestMethod("GET")
	.build()

ResponseObject getRes = WS.sendRequest(getReq)
WS.verifyResponseStatusCode(getRes, 200)

Map getData = (Map) slurper.parseText(getRes.getResponseText())
assert getData.data.id == 2
assert getData.data.email.toString().endsWith("@reqres.in")
assert getData.support.url != null

// =========================================================================
// 3. PUT UPDATE USER (Consumer memodifikasi state -> Producer merespons 200)
// =========================================================================
Map updatePayload = [
	name: "Budi Atmoko",
	job : "Lead SDET"
]

RequestObject putReq = new RestRequestObjectBuilder()
	.withRestUrl("${baseUrl}/users/2")
	.withHttpHeaders(defaultHeaders)
	.withRestRequestMethod("PUT")
	.withTextBodyContent(JsonOutput.toJson(updatePayload), "UTF-8")
	.build()

ResponseObject putRes = WS.sendRequest(putReq)
WS.verifyResponseStatusCode(putRes, 200)

Map putData = (Map) slurper.parseText(putRes.getResponseText())
assert putData.job == "Lead SDET"
assert putData.updatedAt != null

// =========================================================================
// 4. DELETE USER (Verifikasi penghapusan resource -> HTTP 204 No Content)
// =========================================================================
RequestObject deleteReq = new RestRequestObjectBuilder()
	.withRestUrl("${baseUrl}/users/${createdUserId}")
	.withHttpHeaders(defaultHeaders)
	.withRestRequestMethod("DELETE")
	.build()

ResponseObject deleteRes = WS.sendRequest(deleteReq)
WS.verifyResponseStatusCode(deleteRes, 204)
assert deleteRes.getResponseText().isEmpty()