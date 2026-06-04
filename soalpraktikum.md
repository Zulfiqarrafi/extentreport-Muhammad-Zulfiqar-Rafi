Soal Praktikum Selenium Extent Report
PLATFORM TARGET & TOOLS
URL Target: https://www.saucedemo.com/

Bahasa Pemrograman: Java 21

Framework / Library Utama:

Selenium WebDriver

TestNG

ExtentReports (untuk pembuatan laporan HTML)

Build tool: Apache Maven

SKENARIO & FITUR YANG HARUS DIUJI
Setiap peserta wajib membuat skenario otomatisasi pengujian yang mencakup 3 (tiga) fitur utama di bawah ini. Pengujian harus mencakup Skenario Positif (Positive Test) dan Skenario Negatif (Negative Test).

Fitur 1: Login (Autentikasi)
Skenario Positif:

Melakukan login menggunakan kredensial yang valid (standard_user dan secret_sauce).

Verifikasi: Sistem berhasil masuk dan mengarahkan pengguna ke halaman Inventory/Products (tanda: muncul header "Products").

Skenario Negatif:

Melakukan login dengan username valid tetapi password salah, atau mengosongkan salah satu field.

Verifikasi: Sistem menolak login dan memunculkan pesan kesalahan (error message) yang sesuai pada layar.

Fitur 2: Sort Data (Pengurutan Produk)
Skenario:

Setelah berhasil login, lakukan pengubahan urutan produk menggunakan komponen dropdown sort yang tersedia.

Lakukan pengujian untuk minimal 2 jenis pengurutan berikut:

Name (Z to A)

Price (Low to High)

Verifikasi: Pastikan produk yang tampil di layar benar-benar berurutan sesuai dengan filter yang dipilih (lakukan pengecekan pada teks nama atau nilai harga produk pertama setelah di-sort).

Fitur 3: Checkout (Proses Pembelian)
Skenario:

Pilih minimal 2 produk acak dan masukkan ke dalam keranjang belanja (Add to Cart).

Masuk ke halaman keranjang (Cart), lalu klik tombol Checkout.

Isi data informasi pengiriman (First Name, Last Name, Postal Code).

Lanjutkan ke halaman Checkout: Overview dan selesaikan proses dengan menekan tombol Finish.

Verifikasi: Sistem menampilkan halaman sukses dengan pesan "Thank you for your order!".

Note
Pengumpulan:

Buat dan kumpulkan url repo github dan package dengan nama extentreport-namapeserta serta url dokumen SIT Google Spreadsheet