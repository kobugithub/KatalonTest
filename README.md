# Katalon Automation Testing — DemoQA Web UI & Reqres.in RESTful API

Repositori ini berisi rangkaian *script* pengujian otomatis (*Automated Testing*) berbasis **Katalon Studio (Groovy)** yang mencakup pengujian fungsional *end-to-end* pada seluruh modul antarmuka web **[DemoQA](https://demoqa.com/)** serta pengujian kontrak **RESTful API (Producer & Consumer)** menggunakan **[Reqres.in](https://reqres.in/)**.

Seluruh pengujian dirancang menggunakan pendekatan **Programmatic Dynamic TestObject** (`XPath` & `CSS Selector` dinamis) melalui *Custom Keyword* tanpa ketergantungan pada perekaman manual di *Object Repository*.

---

## Arsitektur & Custom Helper (`Keywords/common/DemoQAHelper.groovy`)

Untuk memastikan stabilitas eksekusi terhadap elemen dinamis berbasis React/Bootstrap di DemoQA, proyek ini menggunakan helper terpusat:
* **`byCss(String css)` & `byXpath(String xpath)`**: Membangun *locator* elemen secara dinamis langsung di dalam *Script Mode*.
* **`cleanAds()`**: Menghapus *floating banner* iklan dan *footer* statis menggunakan injeksi JavaScript agar tidak menghalangi interaksi elemen.
* **`safeClick(TestObject to)`**: Melakukan *auto-scroll* hingga elemen berada tepat di tengah layar (`scrollIntoView`), menunggu elemen siap, dan menyediakan *fallback* klik berbasis JavaScript untuk mencegah `ElementClickInterceptedException`.

---

## Cakupan Test Case (Test Cases Coverage)

### 1. Web UI Automation — DemoQA (`https://demoqa.com`)

| ID Test Case | Modul | Fitur yang Diuji | Validasi Utama (Assertions) |
| :--- | :--- | :--- | :--- |
| **`TC01_Elements`** | Elements | • Text Box<br>• Check Box (`rc-tree`)<br>• Radio Button<br>• Web Tables (Add & Search)<br>• Buttons (Double, Right, Dynamic Click) | • Kesesuaian teks output Nama & Email<br>• Status seleksi *checkbox* & *radio button*<br>• Baris data baru ditemukan pada hasil pencarian tabel<br>• Pesan konfirmasi untuk ketiga jenis klik tombol |
| **`TC02_Forms`** | Forms | • Student Registration Form<br>• Input Radio & Checkbox<br>• React-Select Autocomplete<br>• Dynamic File Upload | • Pembuatan & pengunggahan file sementara berhasil<br>• Pemilihan *Subject*, *State*, dan *City* via *autocomplete*<br>• Modal konfirmasi muncul dengan data tabel yang akurat |
| **`TC03_Alerts_Frame_Windows`** | Alerts, Frame & Windows | • Browser Windows (New Tab)<br>• JavaScript Alerts (Simple, Confirm, Prompt)<br>• IFrames (`#frame1`)<br>• Modal Dialogs | • Perpindahan indeks *tab* browser & verifikasi *heading*<br>• *Accept/Dismiss alert* & pengiriman teks pada *Prompt*<br>• Pembacaan elemen di dalam `iframe` & kembalinya konteks ke halaman utama |
| **`TC04_Widgets`** | Widgets | • Accordian<br>• Date Picker<br>• Progress Bar<br>• Tabs<br>• Tool Tips (*Mouse Hover*)<br>• Select Menu | • Ekspansi konten *Accordian* saat bagian judul diklik<br>• Pengisian tanggal (`11/12/2026`) & verifikasi atribut `value`<br>• *Progress bar* mencapai 100% dan tombol *Reset* muncul<br>• Munculnya pesan *tooltip* saat dilakukan *hover* |
| **`TC05_Interactions`** | Interactions | • Selectable List<br>• Droppable (*Drag & Drop*)<br>• Sortable List | • Perubahan *class* elemen menjadi `active` saat dipilih<br>• Elemen berhasil dipindahkan ke area target dengan status `Dropped!`<br>• Reorder elemen daftar menggunakan aksi *drag and drop* |
| **`TC06_BookStore`** | Book Store Application | • Pencarian Katalog Buku<br>• Halaman Detail Buku<br>• Validasi Login Negatif | • Filter pencarian buku *real-time* (`Git Pocket Guide`)<br>• Verifikasi kecocokan Judul & Penulis pada halaman detail<br>• Munculnya pesan error pada percobaan login tidak valid |
| **`TC08_DDT_DemoQA_Form`** | Data-Driven UI | • Eksekusi *looping* form registrasi menggunakan sumber data `Data Files/dqa_forms_data.csv` | • Seluruh baris data CSV terinput dan tervalidasi secara berurutan pada modal hasil *submit* dalam satu sesi browser |

---

### 2. RESTful API Automation — Reqres.in (`https://reqres.in/api`)

| ID Test Case | Peran Pengujian | Endpoint & Method | Skenario & Validasi Kontrak |
| :--- | :--- | :--- | :--- |
| **`TC07_Reqres_Producer_Consumer`** | **Consumer & Producer (End-to-End Chaining)** | 1. `POST /api/users`<br>2. `GET /api/users/2`<br>3. `PUT /api/users/2`<br>4. `DELETE /api/users/{id}` | • **Consumer**: Merakit JSON payload, menyertakan header autentikasi (`x-api-key`), dan mengekstrak `id` dari respons `POST` untuk digunakan pada request `DELETE`.<br>• **Producer**: Memvalidasi HTTP Status Code (`201`, `200`, `204`), waktu respons, struktur skema JSON, serta kesesuaian nilai `createdAt` dan `updatedAt`. |
| **`TC09_DDT_Reqres_API`** | **Data-Driven API** | • `POST /api/users` (Multi-payload dari `Data Files/reqres_api_data.csv`) | • Mengirim berbagai kombinasi `name` dan `job` dari file CSV serta memastikan setiap iterasi mengembalikan status `201 Created` dan ID unik. |

---

## Eksekusi Test Suite Regresi (`TS_Regression_All`)

Seluruh *Test Case* UI dan API di atas diorkestrasi ke dalam satu *Test Suite* terpadu: **`Test Suites/TS_Regression_All`**.

### Urutan Eksekusi di Dalam `TS_Regression_All`
1. `TC07_Reqres_Producer_Consumer` *(Validasi cepat kesiapan layanan API)*
2. `TC01_Elements`
3. `TC02_Forms`
4. `TC06_BookStore`
5. `TC05_Interactions`
6. `TC04_Widgets`
7. `TC03_Alerts_Frame_Windows`

### Cara Menjalankan Regresi

#### 1. Melalui Katalon Studio GUI
1. Buka proyek di **Katalon Studio**.
2. Pada panel **Tests Explorer**, buka **`Test Suites/TS_Regression_All`**.
3. Klik panah di sebelah tombol **Run** pada toolbar atas, lalu pilih **Chrome** atau **Chrome (headless)**.
4. Laporan hasil eksekusi (*HTML*, *JUnit XML*, dan tangkapan layar) otomatis tersimpan di direktori `Reports/`.

#### 2. Melalui GitHub Actions (CI/CD)
Setiap kali terdapat `git push` atau *Pull Request* ke branch `main`, workflow `.github/workflows/katalon.yml` akan otomatis:
* Menjalankan `TS_Regression_All` menggunakan *Katalon Runtime Engine (KRE)* pada mode **Chrome (headless)** di lingkungan Linux (`ubuntu-latest`) dengan resolusi layar virtual `1920x1080`.
* Menerbitkan ringkasan hasil pengujian JUnit dan mengunggah arsip laporan lengkap pada bagian **Artifacts** GitHub Actions.
