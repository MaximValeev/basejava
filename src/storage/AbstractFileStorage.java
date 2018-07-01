package storage;

import exception.StorageException;
import model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        } else if (!directory.canRead() || !directory.canWrite())
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable directory");
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] listFile = directory.listFiles();
        if (listFile == null) {
            throw new StorageException("IO error", directory.getName());
        }
        for (File file : listFile) {
            deleteElement(file);
        }
    }

    @Override
    protected void updateElement(File file, Resume resume) {
        try {
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void saveElement(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        updateElement(file, resume);
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void deleteElement(File file) {
        if (!file.delete()) {
            throw new StorageException("Could not remove item", file.getName());
        }
    }

    @Override
    protected List<Resume> getResumeList() {
        List<Resume> allResumes = new ArrayList<>();
        File[] listFile = directory.listFiles();
        if (listFile == null) {
            throw new StorageException("IO error", directory.getName());
        }
        for (File file : listFile) {
            allResumes.add(getElement(file));
        }
        return allResumes;
    }

    @Override
    protected File getSearchKey(String searchKey) {
        return new File(directory, searchKey);
    }

    @Override
    public int size() {
        String[] directoryList = directory.list();
        if (directoryList == null) {
            throw new StorageException("IO error", directory.getName());
        }
        return directoryList.length;
    }

    @Override
    protected boolean contains(File file) {
        return file.exists();
    }

    protected abstract void doWrite(File file, Resume resume) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}
