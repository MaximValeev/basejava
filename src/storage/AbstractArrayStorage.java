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

    public int size() {
        return storageSize;
    }

    public Resume get(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex != -1) {
            return storage[resumeIndex];
        }
        System.out.println("model.Resume \"" + uuid + "\" does not exist in storage");
        return null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);

    }

    protected abstract int getIndex(String uuid);
}
