import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int storageSize = 0;

    void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    void update(Resume r) {
        Integer i = checkResumeExistence(r.getUuid());
        if (i != null) {
            storage[i] = r;
        } else {
            System.out.println("Resume does not exist");
        }
    }

    void save(Resume r) {
        if (storageSize == storage.length) {
            System.out.println("Storage is overflow");
            return;
        }
            if (checkResumeExistence(r.getUuid()) == null) {
                storage[storageSize] = r;
                storageSize++;
            } else System.out.println("Resume already exists in storage");
    }

    Resume get(String uuid) {
        Integer i = checkResumeExistence(uuid);
        if (i != null) {
            return storage[i];
        } else {
            System.out.println("Resume does not exists in storage");
            return null;
        }
    }

    void delete(String uuid) {
        Integer i = checkResumeExistence(uuid);
        if (i != null) {
            storageSize--;
            storage[i] = storage[storageSize];
            storage[storageSize] = null;
        } else System.out.println("Resume does not exists in storage");

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);

    }

    int size() {
        return storageSize;
    }

    /**
     * @return index of Resume in storage, if resume does not exists return null
     */
    private Integer checkResumeExistence(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) return i;
        }
        return null;
    }
}

