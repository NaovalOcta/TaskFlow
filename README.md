WELCOME
**TaskFlow - To Do List Manager**

TaskFlow adalah aplikasi manajemen harian pribadi tugas berbasis GUI (Graphical User Interface) yang dirancang untuk membantu pengguna membuat, mengatur, dan melihat daftar tugas secara efisien. Aplikasi ini mendukung fitur pencarian, pengelolaan gambar untuk setiap tugas, serta opsi untuk meng-update atau menghapus tugas yang ada.

--> Fitur Utama

### 1. **Pembuatan dan Penyimpanan Tugas**
- Pengguna dapat membuat daftar tugas baru dengan memasukkan:
  - Judul
  - Deskripsi
  - Tanggal tugas (dalam format `dd-MM-yyyy`)
  - Gambar opsional untuk tugas (tidak harus menambahkan gambar)
  - Tugas yang dibuat akan otomatis ditampilkan dalam tabel daftar tugas

### 2. **Pengelolaan Gambar**
- User dapat memilih gambar untuk setiap tugas melalui tombol Add file
- Gambar yang dipilih dapat dilihat pop up jendela baru atau bisa dihapus jika tidak diperlukan

### 3. **Penyuntingan dan Penghapusan Tugas**
- Tugas dapat di-upload dengan mengklik dua kali pada baris tugas di tabel
- Tugas juga dapat dihapus menggunakan tombol "Delete" di tabel

### 4. **Pencarian Tugas**
- Pengguna dapat mencari tugas berdasarkan judul menggunakan fitur Search pada bagian kanan atas

### 5. **Logout**
- Aplikasi memiliki tombol logout yang memungkinkan pengguna keluar dari aplikasi dengan cepat

----------------------------------------------------------

--> Teknologi yang Digunakan

- **Java 8+**
- **JavaFX** untuk GUI interaktif
- **Maven** sebagai alat build
- **ObservableList** untuk pengelolaan data tabel

----------------------------------------------------------

--> Struktur Kode

### 1. **Kelas Utama (GUI.java)**
- `start(Stage stage)`: Metode utama yang mengatur GUI dan logika aplikasi
- `getTable()`: Membuat tabel daftar tugas dengan kolom:
  - Judul tugas
  - Deskripsi tugas
  - Tanggal tugas
  - Gambar
  - Opsi hapus
- `isValidDate(String date)`: Memvalidasi format tanggal

### 2. **Event Handling**
- **Save/Update Button**: Menyimpan atau memperbarui tugas
- **Search Button**: Mencari tugas berdasarkan judul
- **Logout Button**: Menutup aplikasi
- **Choose Image Button**: Memilih gambar dari file sistem
- **Delete Image Button**: Menghapus gambar dari tugas

----------------------------------------------------------

## Cara Menjalankan Aplikasi

### Prasyarat:
1. Instalasi **Java Development Kit (JDK)** versi 8 atau lebih baru
2. Instalasi **JavaFX**
3. IDE seperti IntelliJ IDEA atau Eclipse

### Langkah:
1. Clone repository atau salin file proyek ke lokal
2. Buka proyek di IDE
3. Pastikan JavaFX sudah terhubung ke build path
4. Jalankan file `GUI.java` sebagai aplikasi Java

----------------------------------------------------------

## Masalah yang Umum Ditemui

1. **Error "IndexOutOfBoundsException"**
   - Solusi: Pastikan tabel tidak kosong sebelum melakukan operasi pencarian

2. **Format Tanggal Tidak Valid**
   - Solusi: Gunakan format `dd-MM-yyyy` saat memasukkan tanggal

3. **File Gambar Tidak Muncul**
   - Solusi: Pastikan file gambar memiliki ekstensi yang didukung (.png, .jpg, .jpeg)

----------------------------------------------------------

## Pengembangan Lebih Lanjut

1. **Integrasi Database**
   - Simpan daftar tugas di database seperti MySQL atau SQLite untuk penyimpanan yang lebih permanen
2. **Fitur Pengingat**
   - Tambahkan pengingat otomatis untuk tugas dengan tanggal tertentu
3. **Export/Import Tugas**
   - Tambahkan kemampuan untuk mengekspor atau mengimpor daftar tugas dalam format CSV

----------------------------------------------------------

## Developer
- Dibuat oleh Kelompok 10 untuk keperluan Tugas Besar UAP
- Semoga aplikasi ini bermanfaat dan membantu kelancaran tugas dan mengatur jadwal anda 🙌

----------------------------------------------------------

## Lisensi
Proyek ini dilisensikan oleh lisensi pribadi dan dapat digunakan secara umum (open source)

