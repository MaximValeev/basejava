package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        String identifier = getIdentifier(resume);
        updateElement(handleContainsResult(identifier), resume);
    }

    @Override
    public void save(Resume resume) {
        String uuid = getIdentifier(resume);
        saveElement(handleNotContainsResult(uuid), resume);
    }

    @Override
    public Resume get(String identifier) {
        return getElement(handleContainsResult(identifier));
    }

    @Override
    public void delete(String identifier) {
        deleteElement(handleContainsResult(identifier));

    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = getResumeList();
        Collections.sort(resumeList);
        return resumeList;
    }

    private Object handleContainsResult(String identifier) {
        Object searchKey = getSearchKey(identifier);
        if (contains(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(identifier);
        }
    }

    private Object handleNotContainsResult(String identifier) {
        Object searchKey = getSearchKey(identifier);
        if (!contains(searchKey)) {
            return searchKey;
        } else {
            throw new ExistStorageException(identifier);
        }
    }

    protected String getIdentifier(Resume resume) {
        return resume.getUuid();
    }

    protected abstract boolean contains(Object key);

    protected abstract void updateElement(Object key, Resume resume);

    protected abstract void saveElement(Object key, Resume resume);

    protected abstract Resume getElement(Object key);

    protected abstract void deleteElement(Object key);

    protected abstract Object getSearchKey(String searchKey);

    protected abstract List<Resume> getResumeList();


}
