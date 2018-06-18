package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        Object key = getKey(uuid);
        if (handleContainsResult(contains(key), new NotExistStorageException(uuid))) {
            updateElement(key, resume);
        }
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        Object key = getKey(uuid);
        if (handleContainsResult(!contains(key), new ExistStorageException(uuid))) {
            saveElement(key, resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object key = getKey(uuid);
        if (contains(key)) {
            return getElement(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        Object key = getKey(uuid);
        if (handleContainsResult(contains(key), new NotExistStorageException(uuid))) {
            deleteElement(key);
        }

    }

    private boolean handleContainsResult(boolean isContain, StorageException exception) {
        if (isContain) {
            return true;
        } else {
            throw exception;
        }
    }

    protected abstract boolean contains(Object key);

    protected abstract void updateElement(Object key, Resume resume);

    protected abstract void saveElement(Object key, Resume resume);

    protected abstract Resume getElement(Object key);

    protected abstract void deleteElement(Object key);

    protected abstract Object getKey(String uuid);

}
