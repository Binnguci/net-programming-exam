import java.io.*;
import java.util.StringTokenizer;

public class Ex3 {
    // Khởi tạo thư mục làm việc mặc định
    File currentDirectory = new File("/home/binnguci/Documents/net");

    /**
     * Xử lý lệnh người dùng nhập vào.
     * @param command Lệnh từ người dùng.
     * @return Phản hồi kết quả của lệnh.
     */
    public String excuteCommand(String command) {
        // Tách chuỗi lệnh thành các token (từ khóa)
        StringTokenizer tokenizer = new StringTokenizer(command);
        String action = tokenizer.nextToken().toLowerCase(); // Lấy lệnh đầu tiên và chuyển thành chữ thường
        String response = "";

        switch (action) {
            case "cd":
                // Lệnh CD cần có tham số (tên thư mục)
                if (tokenizer.hasMoreTokens()) {
                    String target = tokenizer.nextToken();
                    response = changeDirectory(target);
                } else {
                    response = "Usage: CD <directory>"; // Thông báo nếu thiếu tham số
                }
                break;
            case "dir":
                // Lệnh DIR để liệt kê thư mục
                response = listDirectory();
                break;
            case "delete":
                // Lệnh DELETE cần có tham số (tên file/thư mục)
                if (tokenizer.hasMoreTokens()) {
                    String target = tokenizer.nextToken();
                    if (!delete(target)) {
                        response = "DELETE: cannot remove: No such file or directory";
                    }
                } else {
                    response = "Usage: DELETE <file/folder>"; // Thông báo nếu thiếu tham số
                }
                break;
            default:
                // Lệnh không hợp lệ
                response = "Invalid command";
                break;
        }
        return response;
    }

    /**
     * Thay đổi thư mục hiện tại.
     * @param target Tên thư mục cần chuyển đến.
     * @return Đường dẫn thư mục mới hoặc thông báo lỗi.
     */
    private String changeDirectory(String target) {
        // Lệnh "CD .." để quay về thư mục cha
        if (target.equals("..")) {
            File parent = currentDirectory.getParentFile();
            if (parent != null) {
                currentDirectory = parent;
                return currentDirectory.getAbsolutePath(); // Trả về đường dẫn mới
            } else {
                return "Already at root directory"; // Thông báo khi không thể quay lại
            }
        }
        // Thay đổi sang thư mục con
        File newDirectory = new File(currentDirectory, target);
        if (newDirectory.exists() && newDirectory.isDirectory()) {
            currentDirectory = newDirectory;
            return "";
        } else {
            return "Directory not found"; // Thông báo lỗi nếu thư mục không tồn tại
        }
    }

    /**
     * Xóa file hoặc thư mục (đệ quy nếu là thư mục).
     * @param target Tên file hoặc thư mục cần xóa.
     * @return true nếu xóa thành công, false nếu thất bại.
     */
    private boolean delete(String target) {
        File file = new File(currentDirectory, target);
        if (!file.exists()) {
            return false; // Trả về false nếu file/thư mục không tồn tại
        }
        if (file.isFile()) {
            return file.delete(); // Xóa file
        }
        if (file.isDirectory()) {
            // Xóa đệ quy tất cả file/thư mục con
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    delete(f.getAbsolutePath());
                }
            }
        }
        return file.delete(); // Xóa thư mục rỗng
    }

    /**
     * Liệt kê nội dung thư mục hiện tại.
     * @return Danh sách file và thư mục.
     */
    private String listDirectory() {
        StringBuilder sb = new StringBuilder();
        File[] files = currentDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    sb.append(file.getName().toUpperCase()).append("\n"); // In hoa nếu là thư mục
                } else {
                    sb.append(file.getName()).append("\n"); // In thường nếu là file
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        Ex3 ex3 = new Ex3();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        // Vòng lặp nhận lệnh từ người dùng
        while (true) {
            writer.print(ex3.currentDirectory.getAbsolutePath() + "> "); // Hiển thị đường dẫn hiện tại
            writer.flush();
            String command = reader.readLine(); // Đọc lệnh từ người dùng
            if (command == null || command.equalsIgnoreCase("exit")) {
                break; // Thoát vòng lặp nếu lệnh là "exit"
            }
            String response = ex3.excuteCommand(command); // Thực thi lệnh
            writer.println(response); // In kết quả
        }
    }
}
