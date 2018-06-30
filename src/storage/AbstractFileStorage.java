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
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if(file.exists()){
            file.delete();
            }
        }
    }

    @Override
    protected void updateElement(File file, Resume resume) {
        doWrite(file, resume);
    }

    @Override
    protected void saveElement(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getElement(File file) {
        return doRead(file);
    }

    @Override
    protected void deleteElement(File file) {
        file.delete();
    }

    @Override
    protected List<Resume> getResumeList() {
        List<Resume> allResumes = new ArrayList<>();
        for (File file : directory.listFiles()) {
            allResumes.add(doRead(file));
        }
        return allResumes;
    }

    @Override
    protected File getSearchKey(String searchKey) {
        return new File(directory, searchKey);
    }

    @Override
    public int size() {
        return directory.list().length;
    }

    @Override
    protected boolean contains(File file) {
        return file.exists();
    }

    public abstract void doWrite(File file, Resume resume);

    public abstract Resume doRead(File file);
}
