package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void put(Resume resume) {
        int insertionPoint = Math.abs(getIndex(resume.getUuid())) - 1;
        Resume[] resultStorage = new Resume[storage.length];

        System.arraycopy(storage, 0, resultStorage, 0, insertionPoint);
        resultStorage[insertionPoint] = resume;
        System.arraycopy(storage, insertionPoint, resultStorage, insertionPoint + 1, storageSize - insertionPoint);
        storage = resultStorage;
    }

    @Override
    protected void erase(int index) {
        Resume[] resultStorage = new Resume[storage.length];
        System.arraycopy(storage, 0, resultStorage, 0, index);
        System.arraycopy(storage, index + 1, resultStorage, index, storageSize);
        storage = resultStorage;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchResume = new Resume();
        searchResume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, storageSize, searchResume);

    }
}
