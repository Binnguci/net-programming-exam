package io;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ex4 {
    /**
     * Chia nhỏ một file lớn thành các file nhỏ hơn với kích thước xác định.
     * @param path Đường dẫn đến file gốc.
     * @param partSize Kích thước mỗi phần file (tính bằng byte).
     * @throws FileNotFoundException Nếu file không tồn tại.
     */
    public void split(String path, int partSize) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File not found");
            return;
        }
        
        // Tính toán số phần và phần còn lại
        int numberOfParts = (int) (file.length() / partSize);
        int remaining = (int) (file.length() % partSize);
        byte[] buffer = new byte[partSize];
        
        // Đọc file và chia nhỏ
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            for (int i = 0; i < numberOfParts; i++) {
                int bytesRead = bis.read(buffer);
                if (bytesRead > 0) {
                    File newFile = new File(createFilename(file.getAbsolutePath(), i));
                    System.out.println(newFile.getAbsolutePath());
                    writeToFile(buffer, newFile, bytesRead);
                }
            }
            
            // Xử lý phần dữ liệu còn lại
            if (remaining > 0) {
                buffer = new byte[remaining];
                int bytesRead = bis.read(buffer);
                if (bytesRead > 0) {
                    File newFile = new File(createFilename(file.getAbsolutePath(), numberOfParts));
                    System.out.println(newFile.getAbsolutePath());
                    writeToFile(buffer, newFile, bytesRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ghi dữ liệu vào file đích.
     * @param buffer Mảng byte chứa dữ liệu.
     * @param file File đích.
     * @param bytesToWrite Số byte cần ghi.
     * @throws IOException Nếu có lỗi trong quá trình ghi file.
     */
    private void writeToFile(byte[] buffer, File file, int bytesToWrite) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(buffer, 0, bytesToWrite);
            bos.flush();
        }
    }

    /**
     * Tạo tên file cho các phần nhỏ.
     * @param name Tên file gốc.
     * @param index Chỉ số phần file.
     * @return Tên file mới.
     */
    private String createFilename(String name, int index) {
        if (index < 10) {
            return name + ".00" + index;
        } else if (index < 100) {
            return name + ".0" + index;
        } else {
            return name + "." + index;
        }
    }

    /**
     * Nối các file nhỏ thành một file gốc.
     * @param firstFile Đường dẫn đến phần đầu tiên của file.
     * @throws IOException Nếu có lỗi trong quá trình nối file.
     */
    public void join(String firstFile) throws IOException {
        List<File> files = new ArrayList<>();
        File firstFileObj = new File(firstFile);
        
        // Lấy tên file gốc
        String originName = firstFileObj.getAbsolutePath().substring(0,
                firstFileObj.getAbsolutePath().lastIndexOf('.'));
        File parent = firstFileObj.getParentFile();
        
        // Tìm tất cả các phần của file
        for (File file : parent.listFiles()) {
            if (file.getAbsolutePath().startsWith(originName + ".")) {
                files.add(file);
            }
        }
        
        // Sắp xếp các file theo chỉ số
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                String num1 = f1.getName().substring(f1.getName().lastIndexOf('.') + 1);
                String num2 = f2.getName().substring(f2.getName().lastIndexOf('.') + 1);
                return Integer.compare(Integer.parseInt(num1), Integer.parseInt(num2));
            }
        });
        
        // Nối các phần vào file gốc
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(originName))) {
            byte[] buffer = new byte[10240];
            for (File file : files) {
                try (BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(file))) {
                    int bytesRead;
                    while ((bytesRead = bis1.read(buffer)) > 0) {
                        bos.write(buffer, 0, bytesRead);
                    }
                    bos.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error during file joining process.", e);
        }
    }

    public static void main(String[] args) {
        Ex4 ex4 = new Ex4();
        try {
            // Chia file thành các phần nhỏ
            // ex4.split("/home/binnguci/Documents/net/hsk3.jpg", 10240);
            
            // Nối file từ các phần nhỏ
            ex4.join("/home/binnguci/Documents/net/hsk3.jpg.000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
