import java.io.File;

public class Ex1 {
    public boolean delete(String path){
        File file = new File(path);
        if (!file.exists()) { 
            return true;
        }
        if (file.isFile()){
            return file.delete();
        }
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if (files != null){
                for (File f : files) {
                    delete(f.getAbsolutePath());
                }
            }
        }
        return file.delete();
    }
    public static void main(String[] args) {
        Ex1 ex1 = new Ex1();
        String path = "/home/binnguci/Documents";
        System.out.println(ex1.delete(path));
    }
}
