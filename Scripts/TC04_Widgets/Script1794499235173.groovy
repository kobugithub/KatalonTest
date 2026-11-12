import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys
import common.DemoQAHelper as H

WebUI.openBrowser('')
WebUI.maximizeWindow()

// 1. ACCORDIAN (Kompatibel dengan Bootstrap 5 Accordion & Card Header)
WebUI.navigateToUrl('https://demoqa.com/accordian')
H.cleanAds()
H.safeClick(H.byXpath("//*[contains(text(),'Where does it come from?')]"))
WebUI.verifyElementVisible(H.byXpath("//*[contains(text(),'Contrary to popular belief')]"))

// 2. DATE PICKER
WebUI.navigateToUrl('https://demoqa.com/date-picker')
H.cleanAds()
WebUI.click(H.byCss('#datePickerMonthYearInput'))
WebUI.sendKeys(H.byCss('#datePickerMonthYearInput'), Keys.chord(Keys.CONTROL, 'a'))
WebUI.sendKeys(H.byCss('#datePickerMonthYearInput'), '11/12/2026')
WebUI.sendKeys(H.byCss('#datePickerMonthYearInput'), Keys.chord(Keys.ENTER))
WebUI.verifyElementAttributeValue(H.byCss('#datePickerMonthYearInput'), 'value', '11/12/2026', 5)

// 3. PROGRESS BAR
WebUI.navigateToUrl('https://demoqa.com/progress-bar')
H.cleanAds()
H.safeClick(H.byCss('#startStopButton'))
WebUI.waitForElementPresent(H.byCss('#resetButton'), 20)
WebUI.verifyElementPresent(H.byCss('#resetButton'), 5)

// 4. TABS
WebUI.navigateToUrl('https://demoqa.com/tabs')
H.cleanAds()
H.safeClick(H.byCss('#demo-tab-origin'))
WebUI.verifyElementVisible(H.byCss('#demo-tabpane-origin'))

// 5. TOOL TIPS (Mouse Over)
WebUI.navigateToUrl('https://demoqa.com/tool-tips')
H.cleanAds()
WebUI.waitForElementVisible(H.byCss('#toolTipButton'), 10)
WebUI.mouseOver(H.byCss('#toolTipButton'))
WebUI.waitForElementVisible(H.byCss('.tooltip-inner'), 5)
WebUI.verifyElementText(H.byCss('.tooltip-inner'), 'You hovered over the Button')

// 6. SELECT MENU
WebUI.navigateToUrl('https://demoqa.com/select-menu')
H.cleanAds()
WebUI.waitForElementVisible(H.byCss('#oldSelectMenu'), 10)
WebUI.selectOptionByLabel(H.byCss('#oldSelectMenu'), 'Purple', false)

WebUI.closeBrowser()