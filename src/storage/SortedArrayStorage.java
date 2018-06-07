package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

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
        int resumeIndex = getIndex(resume.getUuid());
        if (storageSize == STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else if (resumeIndex >= 0) {
            System.out.println("Resume \"" + resume.getUuid() + "\" already exists in storage");
        } else {
            int insertionPoint = Math.abs(resumeIndex) - 1;
            Resume[] resultStorage = new Resume[storage.length];
            if (resumeIndex == -1) {
                System.arraycopy(storage, 0, resultStorage, 1, storageSize);
                resultStorage[insertionPoint] = resume;
                storage = resultStorage;
            } else {
                System.arraycopy(storage, 0, resultStorage, 0, insertionPoint);
                resultStorage[insertionPoint] = resume;
                System.arraycopy(storage, insertionPoint, resultStorage, insertionPoint + 1, storageSize);
                storage = resultStorage;
            }
            storageSize++;
        }
    }

    @Override
    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex >= 0) {
            Resume[] resultStorage = new Resume[storage.length];
            if (resumeIndex == 0) {
                System.arraycopy(storage, 1, resultStorage, 0, storageSize);
                storage = resultStorage;
            } else {
                System.arraycopy(storage, 0, resultStorage, 0, resumeIndex);
                System.arraycopy(storage, resumeIndex + 1, resultStorage, resumeIndex, storageSize);
                storage = resultStorage;
            }
            storageSize--;
        } else {
            System.out.println("Resume \"" + resumeIndex + "\" does not exist in storage");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchResume = new Resume();
        searchResume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, storageSize, searchResume);

    }
}
