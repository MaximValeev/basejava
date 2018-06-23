package storage;

import model.Resume;

public class MapUuidStorage extends MapStorage {

    @Override
    protected void saveElement(Object key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected String getIdentifier(Resume resume) {
        return resume.getUuid();
    }
}
