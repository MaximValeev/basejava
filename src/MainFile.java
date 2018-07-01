import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File rootDit = new File(".");
        printAllFiles(rootDit);
    }

    private static void printAllFiles(File rootDir) {
        File[] dirFiles = rootDir.listFiles();
        if (dirFiles == null) return;
        if (rootDir.isDirectory()) {
            for (File file : dirFiles) {
                if (file.isDirectory()) {
                    printAllFiles(file);
                } else {
                    System.out.println(file.getName());
                }
            }
        } else {
            System.out.println(rootDir);
        }
    }

}
