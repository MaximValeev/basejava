package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void put(Resume resume, int index) {
        int insertionPoint = Math.abs(index) - 1;
        System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, storageSize - insertionPoint);
        storage[insertionPoint] = resume;
    }

    @Override
    protected void erase(int index) {
        System.arraycopy(storage, index+1, storage, index, storageSize - index-1);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchResume = new Resume();
        searchResume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, storageSize, searchResume);

    }
}
