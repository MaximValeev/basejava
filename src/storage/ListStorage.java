package storage;

import model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected boolean updateElement(Object index, Resume resume) {
        if (index != null) {
            storage.set((Integer) index, resume);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean saveElement(Object index, Resume resume) {
        if (index == null) {
            storage.add(resume);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Resume getElement(Object index) {
        if (index != null) {
            return storage.get((Integer) index);
        } else {
            return null;
        }
    }

    @Override
    protected boolean deleteElement(Object index) {
        if (index != null) {
            storage.remove((int) index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }
}
