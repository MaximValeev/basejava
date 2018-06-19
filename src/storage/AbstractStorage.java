package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        updateElement(handleContainsResult(uuid), resume);
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        saveElement(handleNotContainsResult(uuid), resume);
    }

    @Override
    public Resume get(String uuid) {
        return getElement(handleContainsResult(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteElement(handleContainsResult(uuid));

    }

    private Object handleContainsResult(String uuid) {
        Object key = getKey(uuid);
        if (contains(key)) {
            return key;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    private Object handleNotContainsResult(String uuid) {
        Object key = getKey(uuid);
        if (!contains(key)) {
            return key;
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract boolean contains(Object key);

    protected abstract void updateElement(Object key, Resume resume);

    protected abstract void saveElement(Object key, Resume resume);

    protected abstract Resume getElement(Object key);

    protected abstract void deleteElement(Object key);

    protected abstract Object getKey(String uuid);

}
