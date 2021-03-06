import model.Resume;
import storage.SortedArrayStorage;
import storage.Storage;

/**
 * Test for com.urise.webapp.storage.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    private final static Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("human1", "uuid1");
        final Resume r2 = new Resume("human2", "uuid2");
        final Resume r3 = new Resume("human3", "uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);


        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();

        System.out.println("Update r2: ");
        ARRAY_STORAGE.update(r2);
        System.out.println("Update r1: ");
        ARRAY_STORAGE.update(r1);

        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
