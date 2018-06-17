package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object key = getKey(resume.getUuid());
        if ((key instanceof Integer && (Integer) key >= 0) || (key instanceof String && !key.equals("null"))) {
            updateElement(key, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        Object key = getKey(resume.getUuid());
        if ((key instanceof Integer && (Integer) key >= 0) || (key instanceof String && !key.equals("null"))) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveElement(key, resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object key = getKey(uuid);
        if ((key instanceof Integer && (Integer) key >= 0) || (key instanceof String && !key.equals("null"))) {
            return getElement(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        Object key = getKey(uuid);
        if ((key instanceof Integer && (Integer) key >= 0) || (key instanceof String && !key.equals("null"))) {
            deleteElement(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void updateElement(Object key, Resume resume);

    protected abstract void saveElement(Object key, Resume resume);

    protected abstract Resume getElement(Object key);

    protected abstract void deleteElement(Object key);

    protected abstract Object getKey(String uuid);

}
