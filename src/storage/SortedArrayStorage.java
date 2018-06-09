package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void put(Resume resume) {
        int insertionPoint = Math.abs(getIndex(resume.getUuid())) - 1;

        System.arraycopy(storage, 0, storage, 0, insertionPoint);
        storage[insertionPoint] = resume;
        System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, storageSize - insertionPoint);
    }

    @Override
    protected void erase(int index) {
        System.arraycopy(storage, 0, storage, 0, index);
        System.arraycopy(storage, index + 1, storage, index, storageSize);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchResume = new Resume();
        searchResume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, storageSize, searchResume);

    }
}
