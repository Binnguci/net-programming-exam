package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Ex10 {
    
    public void pack(String folder, String zipFile) throws FileNotFoundException, IOException {
        try (RandomAccessFile raf = new RandomAccessFile(zipFile, "rw")){
            File dir = new File(folder);
            if (!dir.exists() || !dir.isDirectory()) {
                throw new FileNotFoundException("Folder not found.");
            }
            File[] files = dir.listFiles(File::isFile);
            if (files == null) return;
            int numFiles = files.length;
            raf.seek(0);
            raf.writeInt(numFiles);
            long currentPosition = 4 + numFiles * (8 + 8 + 256);
            for (File file : files) {
                raf.writeLong(currentPosition);
                raf.writeLong(file.length());
                 String fileName = file.getName();
                byte[] nameBytes = fileName.getBytes();
                byte[] paddedName = Arrays.copyOf(nameBytes, 256); // Đảm bảo tên file dài 256 byte
                raf.write(paddedName); 
                
                currentPosition += file.length();
            }
            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        raf.write(buffer, 0, bytesRead);
                    }
                }
            }

            System.out.println("Đóng gói thành công!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public void unpack(String packedFile, String extractFile, String destFile) {
        try (RandomAccessFile raf = new RandomAccessFile(packedFile, "r");
             FileOutputStream fos = new FileOutputStream(destFile)) {
             
            int fileCount = raf.readInt(); // Đọc số lượng file

            boolean fileFound = false;
            long filePosition = 0;
            long fileSize = 0;

            for (int i = 0; i < fileCount; i++) {
                long position = raf.readLong();
                long size = raf.readLong();
                
                byte[] nameBytes = new byte[256];
                raf.read(nameBytes);
                String name = new String(nameBytes).trim();

                if (name.equals(extractFile)) {
                    fileFound = true;
                    filePosition = position;
                    fileSize = size;
                    break;
                }
            }

            if (fileFound) {
                raf.seek(filePosition); // Di chuyển đến vị trí file trong packedFile
                
                byte[] buffer = new byte[1024];
                long remaining = fileSize;
                while (remaining > 0) {
                    int bytesToRead = (int) Math.min(buffer.length, remaining);
                    int bytesRead = raf.read(buffer, 0, bytesToRead);
                    fos.write(buffer, 0, bytesRead);
                    remaining -= bytesRead;
                }

                System.out.println("File trích xuất thành công: " + destFile);
            } else {
                System.out.println("Không tìm thấy file cần trích xuất!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Ex10 ex10 = new Ex10();
        // ex10.pack("/home/binnguci/Documents/net/Screenshots", "/home/binnguci/Documents/net/Screenshots.zip");
        ex10.unpack("/home/binnguci/Documents/net/Screenshots.zip", "Screenshot From 2024-10-31 12-30-53.png", "/home/binnguci/Documents/net/53.png");
    }
}
