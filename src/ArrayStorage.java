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

    void update(Resume resume) {
        int resumeIndex = checkResumeExistence(resume.getUuid());
        if (resumeIndex != -1) {
            storage[resumeIndex] = resume;
        } else {
            System.out.println("Resume \"" + resume.getUuid() + "\" does not exist in storage");
        }
    }

    void save(Resume resume) {
        if (storageSize == storage.length) {
            System.out.println("Storage overflow");
            return;
        }
        if (checkResumeExistence(resume.getUuid()) == -1) {
            storage[storageSize] = resume;
            storageSize++;
        } else {
            System.out.println("Resume \"" + resume.getUuid() + "\" already exists in storage");
        }
    }

    Resume get(String uuid) {
        int resumeIndex = checkResumeExistence(uuid);
        if (resumeIndex != -1) {
            return storage[resumeIndex];
        }
        System.out.println("Resume \"" + uuid + "\" does not exist in storage");
        return null;
    }

    void delete(String uuid) {
        int resumeIndex = checkResumeExistence(uuid);
        if (resumeIndex != -1) {
            storageSize--;
            storage[resumeIndex] = storage[storageSize];
            storage[storageSize] = null;
        } else {
            System.out.println("Resume \"" + uuid + "\" does not exist in storage");
        }

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
     * @return index of Resume in storage, if resume does not exist return -1
     */
    private int checkResumeExistence(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}

