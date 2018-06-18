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
    protected boolean updateElement(Object key, Resume resume) {
        if (key != null) {
            storage.replace((String) key, resume);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean saveElement(Object key, Resume resume) {
        if (key == null) {
            storage.put(resume.getUuid(), resume);
            return true;
        } else {
            return false;

        }
    }

    @Override
    protected Resume getElement(Object key) {
        if (key != null) {
            return storage.get(key.toString());
        } else {
            return null;
        }
    }

    @Override
    protected boolean deleteElement(Object key) {
        if (key != null) {
            storage.remove(key.toString());
            return true;
        } else {
            return false;
        }
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
}
