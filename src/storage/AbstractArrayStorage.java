package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    @Override
    public void update(Resume resume) {
        int resumeIndex = getIndex(resume.getUuid());
        if (resumeIndex >= 0) {
            storage[resumeIndex] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else if (storageSize == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            put(resume, index);
            storageSize++;
        }
    }

    public Resume get(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex >= 0) {
            return storage[resumeIndex];
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex >= 0) {
            erase(resumeIndex);
            storage[storageSize - 1] = null;
            storageSize--;
        } else {
            throw new NotExistStorageException(uuid);
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

    protected abstract int getIndex(String uuid);

    protected abstract void put(Resume resume, int index);

    protected abstract void erase(int index);
}
