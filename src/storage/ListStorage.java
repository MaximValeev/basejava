package storage;

import exception.NotExistStorageException;
import model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void delete(String uuid) {
        int resumeIndex = storage.indexOf(getElement(uuid));
        if (resumeIndex >= 0) {
            storage.remove(resumeIndex);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    protected void eraseAllElements() {
        storage.clear();
    }

    @Override
    protected Resume updateElement(Resume resume) {
        int position = storage.indexOf(resume);
        if (position >= 0) {
            Resume resumeToUpdate = storage.get(position);
            storage.set(position, resume);
            return resumeToUpdate;
        } else {
            return null;
        }
    }

    @Override
    protected boolean saveElement(Resume resume) {
        int index = storage.indexOf(resume);
        if (index >= 0) {
            return false;
        } else {
            storage.add(resume);
            return true;
        }
    }

    @Override
    protected Resume getElement(String uuid) {
        for (Resume resume : storage) {
            if (resume.getUuid().equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    @Override
    protected boolean deleteElement(String uuid) {
        Resume resume = getElement(uuid);
        if (resume != null) {
            storage.remove(resume);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Resume[] getAllElements() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int getSize() {
        return storage.size();
    }
}
