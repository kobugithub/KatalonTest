package common

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.llm.keyword.LlmKeywords as LLM
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.WebElement

public class DemoQAHelper {

	/**
	 * Membuat TestObject secara dinamis menggunakan XPath atau CSS Selector
	 */
	@Keyword
	static TestObject byXpath(String xpath) {
		TestObject to = new TestObject(xpath)
		to.addProperty("xpath", ConditionType.EQUALS, xpath)
		return to
	}

	@Keyword
	static TestObject byCss(String css) {
		TestObject to = new TestObject(css)
		to.addProperty("css", ConditionType.EQUALS, css)
		return to
	}

	/**
	 * Menghapus banner iklan dan footer fixed di DemoQA agar tidak menghalangi klik
	 */
	@Keyword
	static void cleanAds() {
		String js = """
			var ads = document.querySelectorAll('#fixedban, footer, iframe[id^="google_ads"]');
			ads.forEach(function(el) { el.remove(); });
		"""
		WebUI.executeJavaScript(js, null)
	}

	/**
	 * Scroll ke elemen dan lakukan klik aman (mencegah ElementClickInterceptedException)
	 */
	@Keyword
	static void safeClick(TestObject to) {
		cleanAds()
		WebUI.waitForElementPresent(to, 15)
		WebElement element = WebUI.findWebElement(to, 15)
		WebUI.executeJavaScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", Arrays.asList(element))
		WebUI.delay(1)
		try {
			WebUI.click(to)
		} catch (Exception e) {
			WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(element))
		}
	}
}

