package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SearchKey> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        String uuid = resume.getUuid();
        updateElement(handleContainsResult(uuid), resume);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        String uuid = resume.getUuid();
        saveElement(handleNotContainsResult(uuid), resume);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return getElement(handleContainsResult(uuid));
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        deleteElement(handleContainsResult(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> resumeList = getResumeList();
        Collections.sort(resumeList);
        return resumeList;
    }

    private SearchKey handleContainsResult(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (contains(searchKey)) {
            return searchKey;
        } else {
            LOG.warning("Resume with uuid " + uuid + " does not exist.");
            throw new NotExistStorageException(uuid);
        }
    }

    private SearchKey handleNotContainsResult(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (!contains(searchKey)) {
            return searchKey;
        } else {
            LOG.warning("Resume with uuid " + uuid + " already exists.");
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract boolean contains(SearchKey key);

    protected abstract void updateElement(SearchKey key, Resume resume);

    protected abstract void saveElement(SearchKey key, Resume resume);

    protected abstract Resume getElement(SearchKey key);

    protected abstract void deleteElement(SearchKey key);

    protected abstract SearchKey getSearchKey(String searchKey);

    protected abstract List<Resume> getResumeList();


}
