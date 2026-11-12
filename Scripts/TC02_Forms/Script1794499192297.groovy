import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys
import common.DemoQAHelper as H
import java.nio.file.Files
import java.nio.file.Path

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://demoqa.com/automation-practice-form')
H.cleanAds()

// Buat file dummy sementara untuk pengujian Upload Picture
Path tempFile = Files.createTempFile("sample_avatar", ".txt")
Files.write(tempFile, "Dummy file content for DemoQA upload".getBytes())

// Isi Data Diri
WebUI.setText(H.byCss('#firstName'), 'Dimas')
WebUI.setText(H.byCss('#lastName'), 'Saputra')
WebUI.setText(H.byCss('#userEmail'), 'dimas.saputra@example.com')
H.safeClick(H.byCss("label[for='gender-radio-1']")) // Male
WebUI.setText(H.byCss('#userNumber'), '0812345678')

// Autocomplete Subjects
WebUI.setText(H.byCss('#subjectsInput'), 'Maths')
WebUI.sendKeys(H.byCss('#subjectsInput'), Keys.chord(Keys.ENTER))

// Pilih Hobbies & Upload File
H.safeClick(H.byCss("label[for='hobbies-checkbox-1']")) // Sports
WebUI.uploadFile(H.byCss('#uploadPicture'), tempFile.toAbsolutePath().toString())

// Alamat & React-Select Dropdown (State & City)
WebUI.setText(H.byCss('#currentAddress'), 'Klapanunggal, Jawa Barat')
WebUI.setText(H.byCss('#react-select-3-input'), 'NCR')
WebUI.sendKeys(H.byCss('#react-select-3-input'), Keys.chord(Keys.ENTER))

WebUI.setText(H.byCss('#react-select-4-input'), 'Delhi')
WebUI.sendKeys(H.byCss('#react-select-4-input'), Keys.chord(Keys.ENTER))

// Submit & Verifikasi Modal
H.safeClick(H.byCss('#submit'))
WebUI.verifyElementText(H.byCss('#example-modal-sizes-title-lg'), 'Thanks for submitting the form')
WebUI.verifyElementText(H.byXpath("//td[text()='Student Name']/following-sibling::td"), 'Dimas Saputra')

Files.deleteIfExists(tempFile)
WebUI.closeBrowser()