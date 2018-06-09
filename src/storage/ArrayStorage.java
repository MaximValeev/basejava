package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void put(Resume resume, int index) {
        storage[storageSize] = resume;
    }

    @Override
    protected void erase(int index) {
        storage[index] = storage[storageSize - 1];
    }

    /**
     * @return index of model.Resume in storage, if resume does not exist return -1
     */
    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }


}

