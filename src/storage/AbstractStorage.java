package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        updateElement(handleContainsResult(uuid, new NotExistStorageException(uuid)), resume);
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        saveElement(handleNotContainsResult(uuid, new ExistStorageException(uuid)), resume);
    }

    @Override
    public Resume get(String uuid) {
        return getElement(handleContainsResult(uuid, new NotExistStorageException(uuid)));
    }

    @Override
    public void delete(String uuid) {
        deleteElement(handleContainsResult(uuid, new NotExistStorageException(uuid)));

    }

    private Object handleContainsResult(String uuid, StorageException exception) {
        Object key = getKey(uuid);
        if (contains(key)) {
            return key;
        } else {
            throw exception;
        }
    }

    private Object handleNotContainsResult(String uuid, StorageException exception) {
        Object key = getKey(uuid);
        if (!contains(key)) {
            return key;
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
