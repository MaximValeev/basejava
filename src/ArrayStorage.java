import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int nextIndex = 0;

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            storage[i] = null;
        }
        nextIndex = 0;
    }

    void save(Resume r) {
        storage[nextIndex] = r;
        nextIndex++;
    }

    Resume get(String uuid) {
        for(int i = 0; i < nextIndex; i++) {
            if (storage[i].toString().equals(uuid)){
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < nextIndex; i++) {
            if (storage[i].uuid.equals(uuid)){
                storage[i] = null;
                storage[i] = storage[nextIndex - 1];
                storage[nextIndex -1] = null;
                nextIndex--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, nextIndex);

    }

    int size() {
        return nextIndex;
    }
}
