package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateElement(Object key, Resume resume) {
        storage.replace((String) key, resume);
    }

    @Override
    protected void saveElement(Object key, Resume resume) {
        storage.put(resume.getFullName(), resume);
    }

    @Override
    protected Resume getElement(Object key) {
        return storage.get(key.toString());
    }

    @Override
    protected void deleteElement(Object key) {
        storage.remove(key.toString());
    }

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected Object getSearchKey(String key) {
        if (storage.containsKey(key)) {
            return key;
        } else {
            return null;
        }
    }

    @Override
    protected String getIdentifier(Resume resume) {
        return resume.getFullName();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean contains(Object key) {
        return key != null;
    }
}
