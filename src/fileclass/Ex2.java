import java.io.File;

public class Ex2 {
    /**
     * Xóa tất cả các tệp và thư mục con trong đường dẫn được chỉ định.
     *
     * @param path Đường dẫn đến tệp hoặc thư mục cần xóa.
     * @return true nếu tất cả các tệp được xóa thành công hoặc không tồn tại;
     *         false nếu có bất kỳ lỗi nào xảy ra trong quá trình xóa.
     */
    public boolean delete(String path) {
        File file = new File(path);
        // Nếu tệp hoặc thư mục không tồn tại, trả về true
        if (!file.exists()) {
            return true;
        }
        
        // Nếu là tệp, thực hiện xóa trực tiếp
        if (file.isFile()) {
            return file.delete();
        }
        
        // Nếu là thư mục, lặp qua tất cả các tệp con và xóa chúng
        boolean flag = true;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    flag &= delete(f.getAbsolutePath());
                }
            }
        }
        
        return flag;
    }

    public static void main(String[] args) {
        Ex2 ex2 = new Ex2();
        String path = "/home/binnguci/Documents/net";
        // In ra kết quả xóa (true nếu thành công xóa hết các file, false nếu thất bại hoặc có 1 file không thể xóa)
        System.out.println(ex2.delete(path));
    }
}
