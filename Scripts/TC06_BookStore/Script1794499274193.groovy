import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import common.DemoQAHelper as H

WebUI.openBrowser('')
WebUI.maximizeWindow()

// 1. PENCARIAN KATALOG BUKU
WebUI.navigateToUrl('https://demoqa.com/books')
H.cleanAds()
WebUI.setText(H.byCss('#searchBox'), 'Git Pocket Guide')

TestObject bookLink = H.byXpath("//a[contains(text(),'Git Pocket Guide')]")
WebUI.verifyElementPresent(bookLink, 5)
H.safeClick(bookLink)

// 2. VERIFIKASI DETAIL BUKU
WebUI.verifyElementText(H.byCss('#title-wrapper #userName-value'), 'Git Pocket Guide')
WebUI.verifyElementText(H.byCss('#author-wrapper #userName-value'), 'Richard E. Silverman')

// 3. VALIDASI HALAMAN LOGIN BOOK STORE
WebUI.navigateToUrl('https://demoqa.com/login')
H.cleanAds()
WebUI.setText(H.byCss('#userName'), 'invalid_user_qa')
WebUI.setText(H.byCss('#password'), 'WrongPass123!')
H.safeClick(H.byCss('#login'))
WebUI.verifyElementText(H.byCss('#name'), 'Invalid username or password!')

WebUI.closeBrowser()