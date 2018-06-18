package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        boolean operationResult = updateElement(getKey(uuid), resume);
        handleOperationResult(operationResult, new NotExistStorageException(uuid));
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        boolean operationResult = saveElement(getKey(uuid), resume);
        handleOperationResult(operationResult, new ExistStorageException(uuid));
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = getElement(getKey(uuid));
        if (resume == null) {
            throw new NotExistStorageException(uuid);
        } else {
            return resume;
        }

    }

    @Override
    public void delete(String uuid) {
        boolean operationResult = deleteElement(getKey(uuid));
        handleOperationResult(operationResult, new NotExistStorageException(uuid));
    }

    private void handleOperationResult(boolean operationStatus, StorageException exception) {
        if (!operationStatus) {
            throw exception;
        }
    }

    protected abstract boolean updateElement(Object key, Resume resume);

    protected abstract boolean saveElement(Object key, Resume resume);

    protected abstract Resume getElement(Object key);

    protected abstract boolean deleteElement(Object key);

    protected abstract Object getKey(String uuid);

}
