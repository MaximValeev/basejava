package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateElement(Resume key, Resume resume) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void saveElement(Resume key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Resume resume) {
        return resume;
    }

    @Override
    protected void deleteElement(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean contains(Resume key) {
        return key != null;
    }
}
