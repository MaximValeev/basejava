package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;

    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void update(Resume resume) {
        int resumeIndex = checkResumeExistence(resume.getUuid());
        if (resumeIndex != -1) {
            storage[resumeIndex] = resume;
        } else {
            System.out.println("model.Resume \"" + resume.getUuid() + "\" does not exist in storage");
        }
    }

    public void save(Resume resume) {
        if (checkResumeExistence(resume.getUuid()) != -1) {
            System.out.println("model.Resume \"" + resume.getUuid() + "\" already exists in storage");
        } else if (storageSize == STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            storage[storageSize] = resume;
            storageSize++;
        }
    }

    public Resume get(String uuid) {
        int resumeIndex = checkResumeExistence(uuid);
        if (resumeIndex != -1) {
            return storage[resumeIndex];
        }
        System.out.println("model.Resume \"" + uuid + "\" does not exist in storage");
        return null;
    }

    public void delete(String uuid) {
        int resumeIndex = checkResumeExistence(uuid);
        if (resumeIndex != -1) {
            storageSize--;
            storage[resumeIndex] = storage[storageSize];
            storage[storageSize] = null;
        } else {
            System.out.println("model.Resume \"" + uuid + "\" does not exist in storage");
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);

    }

    public int size() {
        return storageSize;
    }

    /**
     * @return index of model.Resume in storage, if resume does not exist return -1
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

