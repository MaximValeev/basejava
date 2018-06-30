import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File rootDit = new File(".");
        printAllFiles(Objects.requireNonNull(rootDit.listFiles()), 0, 0);
    }

    private static void printAllFiles(File[] allFilesInDirectory, int index, int level) {
        if(index == allFilesInDirectory.length) return;

        for (int i = 0; i < level; i++) {
            System.out.print('\t');
        }

        if (allFilesInDirectory[index].isFile()) {
            System.out.println(allFilesInDirectory[index].getName());
        } else if (allFilesInDirectory[index].isDirectory()) {
            System.out.println("[ " + allFilesInDirectory[index].getName() + " ]");
            printAllFiles(Objects.requireNonNull(allFilesInDirectory[index].listFiles()), 0, level + 1);
        }

        printAllFiles(allFilesInDirectory, ++index, level);

    }

}
