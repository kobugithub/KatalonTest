import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import common.DemoQAHelper as H

WebUI.openBrowser('')
WebUI.maximizeWindow()

// 1. BROWSER WINDOWS (New Tab)
WebUI.navigateToUrl('https://demoqa.com/browser-windows')
H.safeClick(H.byCss('#tabButton'))
WebUI.switchToWindowIndex(1)
WebUI.verifyElementText(H.byCss('#sampleHeading'), 'This is a sample page')
WebUI.closeWindowIndex(1)
WebUI.switchToWindowIndex(0)

// 2. ALERTS (Simple Alert, Confirm Box, Prompt Box)
WebUI.navigateToUrl('https://demoqa.com/alerts')
H.cleanAds()

H.safeClick(H.byCss('#alertButton'))
WebUI.verifyAlertPresent(5)
WebUI.acceptAlert()

H.safeClick(H.byCss('#confirmButton'))
WebUI.dismissAlert()
WebUI.verifyElementText(H.byCss('#confirmResult'), 'You selected Cancel')

H.safeClick(H.byCss('#promtButton'))
WebUI.setAlertText('Katalon Tester')
WebUI.acceptAlert()
WebUI.verifyElementText(H.byCss('#promptResult'), 'You entered Katalon Tester')

// 3. FRAMES (IFrame Switch)
WebUI.navigateToUrl('https://demoqa.com/frames')
WebUI.switchToFrame(H.byCss('#frame1'), 5)
WebUI.verifyElementText(H.byCss('#sampleHeading'), 'This is a sample page')
WebUI.switchToDefaultContent()

// 4. MODAL DIALOGS
WebUI.navigateToUrl('https://demoqa.com/modal-dialogs')
H.safeClick(H.byCss('#showSmallModal'))
WebUI.verifyElementText(H.byCss('.modal-body'), 'This is a small modal. It has very less content')
H.safeClick(H.byCss('#closeSmallModal'))

WebUI.closeBrowser()