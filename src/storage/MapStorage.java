package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void eraseAllElements() {
        storage.clear();
    }

    @Override
    protected Resume updateElement(Resume resume) {
        Resume resumeToUpdate = storage.get(resume.getUuid());
        if (resumeToUpdate != null) {
            storage.put(resume.getUuid(), resume);
            return resume;
        } else {
            return null;
        }
    }

    @Override
    protected boolean saveElement(Resume resume) {
        if (storage.containsKey(resume.getUuid())) {
            return false;
        } else {
            storage.put(resume.getUuid(), resume);
            return true;
        }
    }

    @Override
    protected Resume getElement(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean deleteElement(String uuid) {
        Resume resume = storage.get(uuid);
        if (resume != null) {
            storage.remove(uuid);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Resume[] getAllElements() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected int getSize() {
        return storage.size();
    }
}
