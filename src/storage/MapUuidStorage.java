package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateElement(String key, Resume resume) {
        storage.replace(key, resume);
    }

    @Override
    protected void saveElement(String key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(String key) {
        return storage.get(key);
    }

    @Override
    protected void deleteElement(String key) {
        storage.remove(key);
    }

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean contains(String key) {
        return storage.containsKey(key);
    }
}
