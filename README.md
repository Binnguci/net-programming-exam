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

---

## Phần 3: Socket

### **Bài 16: Upload file**
- Viết CT **client/server** upload file lên server. Người dùng tương tác với clietn qua console với cú pháp lệnh: `upload source_file dest_file`
- Kết nối TCP, port = 2000
- Người dùng nhập lệnh trên console của client theo cú pháp:
`UPLOAD source_path dest_path`
- Trong đó:
- **UPLOAD** là lệnh
- **source_path** là đường dẫn tuyệt đối của file nguồn trên clien
- **odest_path** là đường dẫn tuyệt đối của file đích sẽ tạo trên server
- Xử lý 2 ngoại lệ: không mở được file nguồn và không tạo được file đích
- 1.Giai đoạn 1: Chỉ thực hiện lệnh được 1 lần 
- 2.Giai đoạn 2: Người dùng thực hiện lệnh nhiều lần cho đến khi nhập lệnh “EXIT” client mơi kết thúc. Trong quá trình hoạt động kết nối TCP được duy trì.

### **Bài 17: Tính toán + - * /**
- Client tương tác với server bằng giao thức TCP, cổng 12345, thông qua các dòng văn bản, m6 hình tương tác Command/Response. Người dùng nhập lệnh trên client có dạng:
``` bash
Toán_hạng_1 Toán_tử Toán_hạng_2
```
- Trong đó: Toán hạng 1 và toán hạng 2 là số bất kỳ, Toán tử là 1 trong 4 ký tự: +, -, *, /
, Server sẽ phản hồi kết quả là thông báo lỗi, hoặc kết quả của phép
- Người dùng thực hiện nhiều lần tùy ý cho đến khi  gửi lệnh “EXIT” để yêu cầu kết thúc.
- Ví dụ:
``` bash
  1 + 2 rồi nhấp enter, client gửi dòng dữ liệu tới server, server trả về kết quả có dạng:
  1 + 2 = 3
```

- Cần xử lý các ngoại lệ: toán hạng không phải là số và phép chia cho 0.

### **Bài 18:Viết CT client/server ra cứu thông tin sinh viên qua mạng**
- Kết nối TCP, cổng 54321
- Tương tác Client/Server thông qua các dòng văn bản
- Mô hình Command/Response
- Trước tiên cần phải đăng nhập. Trong giai đoạn này người dùng có thể dùng 3 lệnh sau.
- **QUIT**: Kết thúc chương trình
- **USER** user_name
- **PASS** password
    Cơ chế đăng nhập tương tự giao thức POP3
- Sau khi đăng nhập thành công, người dùng có thể tra cứu thông tin sinh viên thông qua các lệnh sau:
- QUIT: Kết thúc chương trình
- **FindById** mã_sinh_viên
- **FindByName** một_phần_cuối_của_tên
	Ví dụ: Sinh viên có tên là Nguyễn Thị Hoa Hồng thì tham số của FindByName có thể là: Hồng, Hoa Hồng, Thị Hoa Hồng hoặc Nguyễn Thị Hoa Hồng 
- Kết quả trả về nếu không tìm thấy thì thông báo “Không tìm thấy” ngược lại thì thông tin mỗi sinh viên là 1 dòng 
- Xử lý trường hợp lệnh không hợp lệ
- Thông tin sinh viên bao gồm: Mã sinh viên, Họ và tên, điểm trung bình. 
- Sinh viên tự tạo dữ liệu mẫu và lưu trong ArrayList để sử dụng