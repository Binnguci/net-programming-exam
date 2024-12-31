## Phần File Class:

---

### **Bài 1:**  
**TH1 - Xóa File/Folder:**  
Cho trước 1 đường dẫn bất kỳ `path`, hiện thực hàm `boolean delete(String path)` xóa tất cả những gì có thể trong file/thư mục được chỉ định bởi `path`, bao gồm cả các thư mục con.  
- **Trả về:** `true` nếu xóa thành công, `false` nếu xóa không thành công.

---

### **Bài 2:**  
**Mở rộng:**  
Xóa file và giữ nguyên cấu trúc thư mục.

---

### **Bài 3:**  
**Ứng dụng command line thực hiện các lệnh cơ bản: CD, DIR, DELETE.**  

- **Khởi tạo:** Thư mục mặc định `defaultDir`, ví dụ: `c:\temp`.  
- **Dấu nhắc lệnh:** `defaultDir>` (ví dụ: `c:\temp>`).  

**Lệnh hỗ trợ:**  
- **EXIT:** Kết thúc chương trình.  
- **CD subfolder:** Chuyển đến thư mục con `subfolder`.  
- **CD ..:** Trở về thư mục cha.  
- **DIR:** Hiển thị nội dung thư mục hiện hành.  
   - Tên thư mục in **hoa**, hiển thị trước.  
   - Tên tệp in **thường**, hiển thị sau.  
- **DELETE file/folder:** Xóa tệp hoặc thư mục trong thư mục hiện hành.  

---

## Phần 2: IO

---

### **Bài 4: File Spliter and Joiner**

#### a. Chia File (`split`)
- **Phương thức:** `void split(String source, int pSize)`
- **Chức năng:** Chia một file nguồn (`source`) thành nhiều file thành phần có kích thước cụ thể (`pSize`).

#### b. Ghép File (`join`)
- **Phương thức:** `void join(String partFilename)`
- **Chức năng:** Ghép nối tất cả các file thành phần trong thư mục chứa `partFilename` để khôi phục file ban đầu.
- **Nguyên tắc:** Chỉ cần cung cấp tên của một file thành phần bất kỳ.

#### c. Quy ước Tên File Thành Phần
- Các file thành phần sẽ có phần mở rộng chứa 3 ký tự, lần lượt từ: `.001`, `.002`, ..., `.999`.

#### d. Nguyên Tắc Tạo Tên File Thành Phần
- `partFilename = source + ".001"`, `".002"`, ...

