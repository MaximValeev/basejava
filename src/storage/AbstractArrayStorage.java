package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    private static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    int storageSize = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    @Override
    protected void updateElement(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void saveElement(Integer index, Resume resume) {
        if (storageSize == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            put(resume, index);
            storageSize++;
        }
    }

    @Override
    protected Resume getElement(Integer index) {
        return storage[index];
    }

    @Override
    protected void deleteElement(Integer index) {
        erase(index);
        storage[storageSize - 1] = null;
        storageSize--;
    }

    @Override
    protected boolean contains(Integer index) {
        return index >= 0;
    }

    @Override
    public int size() {
        return storageSize;
    }

    @Override
    protected List<Resume> getResumeList() {
        return Arrays.asList(Arrays.copyOf(storage, storageSize));
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void put(Resume resume, int index);

    protected abstract void erase(int index);
}
