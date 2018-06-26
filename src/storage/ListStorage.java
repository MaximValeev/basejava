package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateElement(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void saveElement(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void deleteElement(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean contains(Integer index) {
        return index != null;
    }
}
