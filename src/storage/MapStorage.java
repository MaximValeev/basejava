package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> storage = new HashMap<>();

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
        storage.put(resume.getUuid(), resume);
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
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        } else {
            return null;
        }
    }

    @Override
    protected boolean contains(Object key) {
        return key != null;
    }
}
