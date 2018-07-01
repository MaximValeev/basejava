import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File rootDit = new File(".");
        printAllFiles(rootDit);
    }

    private static void printAllFiles(File rootDir) {
        File[] listFile = rootDir.listFiles();
        if (listFile != null) {
            for (File file : listFile) {
                if (file.isDirectory()) {
                    printAllFiles(file);
                } else System.out.println(file.getName());
            }
        }
    }

}
