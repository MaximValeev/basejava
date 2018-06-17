package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void clear() {
        eraseAllElements();
    }

    @Override
    public void update(Resume resume) {
        if (updateElement(resume) == null) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        if (!saveElement(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = getElement(uuid);
        if (resume != null) {
            return resume;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        if (!deleteElement(uuid)) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return getAllElements();
    }

    public int size() {
        return getSize();
    }


    protected abstract void eraseAllElements();

    protected abstract Resume updateElement(Resume resume);

    protected abstract boolean saveElement(Resume resume);

    protected abstract Resume getElement(String uuid);

    protected abstract boolean deleteElement(String uuid);

    protected abstract Resume[] getAllElements();

    protected abstract int getSize();

}
