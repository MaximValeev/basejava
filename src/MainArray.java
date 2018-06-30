import model.Resume;
import storage.ArrayStorage;
import storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Test for com.urise.webapp.storage.storage.ArrayStorage
 */
public class MainArray {
    private final static Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save uuid | delete uuid | get uuid | clear | update | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < params.length; i++) {
                if (i == 1) {
                    sb.append(params[i]);
                } else {
                    sb.append(" ").append(params[i]);
                }
            }
            String fullName = sb.toString();

            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    r = new Resume(fullName);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(fullName);
                    printAll();
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(fullName));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "update":
                    r = new Resume(fullName);
                    ARRAY_STORAGE.update(r);
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println("FullName: " + r.getFullName() + "; UUID: " + r.getUuid());
            }
        }
        System.out.println("----------------------------");
    }
}