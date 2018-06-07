package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void update(Resume resume) {
        int resumeIndex = getIndex(resume.getUuid());
        if (resumeIndex != -1) {
            storage[resumeIndex] = resume;
        } else {
            System.out.println("model.Resume \"" + resume.getUuid() + "\" does not exist in storage");
        }
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("model.Resume \"" + resume.getUuid() + "\" already exists in storage");
        } else if (storageSize == STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            storage[storageSize] = resume;
            storageSize++;
        }
    }

    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex != -1) {
            storageSize--;
            storage[resumeIndex] = storage[storageSize];
            storage[storageSize] = null;
        } else {
            System.out.println("model.Resume \"" + uuid + "\" does not exist in storage");
        }

    }

    /**
     * @return index of model.Resume in storage, if resume does not exist return -1
     */
    protected int getIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}

