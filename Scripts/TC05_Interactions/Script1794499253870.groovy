import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import common.DemoQAHelper as H

WebUI.openBrowser('')
WebUI.maximizeWindow()

// 1. SELECTABLE
WebUI.navigateToUrl('https://demoqa.com/selectable')
H.cleanAds()
H.safeClick(H.byXpath("//ul[@id='verticalListContainer']/li[text()='Cras justo odio']"))
String classAttr = WebUI.getAttribute(H.byXpath("//ul[@id='verticalListContainer']/li[text()='Cras justo odio']"), 'class')
assert classAttr.contains('active')

// 2. DROPPABLE (Drag and Drop)
WebUI.navigateToUrl('https://demoqa.com/droppable')
H.cleanAds()
WebUI.dragAndDropToObject(H.byCss('#draggable'), H.byCss('#simpleDropContainer #droppable'))
WebUI.verifyElementText(H.byCss('#simpleDropContainer #droppable p'), 'Dropped!')

// 3. SORTABLE
WebUI.navigateToUrl('https://demoqa.com/sortable')
H.cleanAds()
WebUI.dragAndDropToObject(
	H.byXpath("//div[@id='demo-tabpane-list']//div[text()='One']"),
	H.byXpath("//div[@id='demo-tabpane-list']//div[text()='Four']")
)

WebUI.closeBrowser()