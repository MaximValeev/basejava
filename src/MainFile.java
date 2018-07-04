import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File rootDit = new File("D:\\Study\\Java\\basejava");
        printAllFiles(rootDit, 0);

    }

    private static void printAllFiles(File rootDir, int level) {
        File[] listFile = rootDir.listFiles();
        if (listFile != null) {
            for (File file : listFile) {
                for (int i = 0; i <level; i++) System.out.print("\t");
                if (file.isDirectory()) {
                    System.out.println(file.getName());
                    printAllFiles(file, level+1);
                } else {
                    System.out.println(file.getName());
                }
            }
        }
    }

}
