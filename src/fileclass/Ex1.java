import java.io.File;

public class Ex1 {
    /**
     * Xóa tệp hoặc thư mục được chỉ định bởi đường dẫn.
     *
     * @param path Đường dẫn đến tệp hoặc thư mục cần xóa.
     * @return true nếu tệp hoặc thư mục được xóa thành công, false nếu thất bại.
     */
    public boolean delete(String path) {
        File file = new File(path);
        
        // Nếu tệp hoặc thư mục không tồn tại, trả về true
        if (!file.exists()) { 
            return true;
        }
        
        // Nếu là tệp, thực hiện xóa
        if (file.isFile()) {
            return file.delete();
        }
        
        // Nếu là thư mục, xóa tất cả các tệp con
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    delete(f.getAbsolutePath());
                }
            }
        }
        
        // Xóa thư mục sau khi đã xóa hết tệp con
        return file.delete();
    }

    public static void main(String[] args) {
        Ex1 ex1 = new Ex1();
        String path = "/home/binnguci/Documents/net";
        
        // In ra kết quả xóa (true nếu thành công, false nếu thất bại)
        System.out.println(ex1.delete(path));
    }
}
