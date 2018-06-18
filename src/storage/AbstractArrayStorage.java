package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    @Override
    protected boolean updateElement(Object index, Resume resume) {
        if ((int) index >= 0) {
            storage[(int) index] = resume;
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean saveElement(Object index, Resume resume) {
        if ((int) index >= 0) {
            return false;
        } else if (storageSize == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            put(resume, (int) index);
            storageSize++;
            return true;
        }
    }

    @Override
    protected Resume getElement(Object index) {
        if ((int) index >= 0) {
            return storage[(int) index];
        } else {
            return null;
        }
    }

    @Override
    protected boolean deleteElement(Object index) {
        if ((int) index >= 0) {
            erase((Integer) index);
            storage[storageSize - 1] = null;
            storageSize--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return storageSize;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    protected abstract Object getKey(String uuid);

    protected abstract void put(Resume resume, int index);

    protected abstract void erase(int index);
}
