package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    @Override
    public void update(Resume resume) {
        int resumeIndex = getIndex(resume.getUuid());
        if (resumeIndex >= 0) {
            storage[resumeIndex] = resume;
        } else {
            System.out.println("Resume \"" + resume.getUuid() + "\" does not exist in storage");
        }
    }

    @Override
    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("Resume \"" + resume.getUuid() + "\" already exists in storage");
        } else if (storageSize == STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            put(resume);
            storageSize++;
        }
    }

    public Resume get(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex >= 0) {
            return storage[resumeIndex];
        }
        System.out.println("Resume \"" + uuid + "\" does not exist in storage");
        return null;
    }

    @Override
    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex >= 0) {
            erase(resumeIndex);
            storageSize--;
        } else {
            System.out.println("Resume \"" + uuid + "\" does not exist in storage");
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);

    }

    public int size() {
        return storageSize;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void put(Resume resume);

    protected abstract void erase(int index);
}
