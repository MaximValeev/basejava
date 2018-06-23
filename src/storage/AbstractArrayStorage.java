package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    private static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    int storageSize = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    @Override
    protected void updateElement(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    protected void saveElement(Object index, Resume resume) {
        if (storageSize == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            put(resume, (int) index);
            storageSize++;
        }
    }

    @Override
    protected Resume getElement(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void deleteElement(Object index) {
        erase((Integer) index);
        storage[storageSize - 1] = null;
        storageSize--;
    }

    @Override
    protected boolean contains(Object index) {
        return (int) index >= 0;
    }

    @Override
    public int size() {
        return storageSize;
    }

    @Override
    protected List<Resume> getResumeList() {
        return Arrays.asList(Arrays.copyOf(storage, storageSize));
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract void put(Resume resume, int index);

    protected abstract void erase(int index);
}
