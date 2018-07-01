import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File rootDit = new File(".");
        printAllFiles(rootDit);
    }

    private static void printAllFiles(File rootDir) {
        for (File file: rootDir.listFiles()) {
            if (file.isDirectory()) {
                printAllFiles(file);
            } else System.out.println(file.getName());
        }
    }

}
