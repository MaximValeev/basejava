package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    @Override
    protected void eraseAllElements() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    @Override
    protected Resume updateElement(Resume resume) {
        int resumeIndex = getIndex(resume.getUuid());
        if (resumeIndex >= 0) {
            Resume resumeToUpdate = storage[resumeIndex];
            storage[resumeIndex] = resume;
            return resumeToUpdate;
        } else {
            return null;
        }
    }

    @Override
    protected boolean saveElement(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            return false;
        } else if (storageSize == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            put(resume, index);
            storageSize++;
            return true;
        }
    }

    @Override
    protected Resume getElement(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex >= 0) {
            return storage[resumeIndex];
        } else {
            return null;
        }
    }

    @Override
    protected boolean deleteElement(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex >= 0) {
            erase(resumeIndex);
            storage[storageSize - 1] = null;
            storageSize--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Resume[] getAllElements() {
        return Arrays.copyOf(storage, storageSize);
    }

    @Override
    protected int getSize() {
        return storageSize;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void put(Resume resume, int index);

    protected abstract void erase(int index);
}
