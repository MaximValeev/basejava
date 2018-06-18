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
    protected void updateElement(Object index, Resume resume) {
        storage.set((Integer) index, resume);
    }

    @Override
    protected void saveElement(Object index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected void deleteElement(Object index) {
        storage.remove((int) index);
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

    @Override
    protected boolean contains(Object index) {
        return index != null;
    }
}
