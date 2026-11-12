import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import common.DemoQAHelper as H

WebUI.openBrowser('')
WebUI.maximizeWindow()

// 1. TEXT BOX
WebUI.navigateToUrl('https://demoqa.com/text-box')
H.cleanAds()
WebUI.setText(H.byCss('#userName'), 'Budi QA')
WebUI.setText(H.byCss('#userEmail'), 'budi.qa@example.com')
WebUI.setText(H.byCss('#currentAddress'), 'Jl. Industri Raya No. 10, Bogor')
WebUI.setText(H.byCss('#permanentAddress'), 'Jl. Sudirman No. 45, Jakarta')
H.safeClick(H.byCss('#submit'))

WebUI.verifyElementText(H.byCss('#name'), 'Name:Budi QA')
WebUI.verifyElementText(H.byCss('#email'), 'Email:budi.qa@example.com')

// 2. CHECK BOX (Kompatibel dengan rc-tree & react-checkbox-tree)
WebUI.navigateToUrl('https://demoqa.com/checkbox')
H.cleanAds()
H.safeClick(H.byXpath("//span[text()='Home'] | //span[contains(@class,'rc-tree-checkbox')] | //span[contains(@class,'rct-checkbox')]"))
WebUI.verifyElementPresent(H.byCss('#result'), 5)
WebUI.verifyElementPresent(H.byXpath("//div[@id='result']//span[contains(@class,'text-success')]"), 5)

// 3. RADIO BUTTON
WebUI.navigateToUrl('https://demoqa.com/radio-button')
H.cleanAds()
H.safeClick(H.byCss("label[for='impressiveRadio']"))
WebUI.verifyElementText(H.byCss('.text-success'), 'Impressive')

// 4. WEB TABLES (Add Record & Search - mendukung <td> dan gridcell)
WebUI.navigateToUrl('https://demoqa.com/webtables')
H.cleanAds()
H.safeClick(H.byCss('#addNewRecordButton'))
WebUI.setText(H.byCss('#firstName'), 'Raka')
WebUI.setText(H.byCss('#lastName'), 'Pratama')
WebUI.setText(H.byCss('#userEmail'), 'raka@example.com')
WebUI.setText(H.byCss('#age'), '28')
WebUI.setText(H.byCss('#salary'), '9500000')
WebUI.setText(H.byCss('#department'), 'Quality Assurance')
H.safeClick(H.byCss('#submit'))

WebUI.setText(H.byCss('#searchBox'), 'Raka')
WebUI.verifyElementPresent(H.byXpath("//*[(self::td or @role='gridcell') and text()='Raka']"), 5)

// 5. BUTTONS (Double Click, Right Click, Dynamic Click)
WebUI.navigateToUrl('https://demoqa.com/buttons')
H.cleanAds()
WebUI.doubleClick(H.byCss('#doubleClickBtn'))
WebUI.verifyElementText(H.byCss('#doubleClickMessage'), 'You have done a double click')

WebUI.rightClick(H.byCss('#rightClickBtn'))
WebUI.verifyElementText(H.byCss('#rightClickMessage'), 'You have done a right click')

H.safeClick(H.byXpath("//button[text()='Click Me']"))
WebUI.verifyElementText(H.byCss('#dynamicClickMessage'), 'You have done a dynamic click')

WebUI.closeBrowser()