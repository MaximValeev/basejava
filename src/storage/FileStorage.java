package storage;

import exception.StorageException;
import model.Resume;
import storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private StreamSerializer streamSerializerStrategy;

    public FileStorage(File directory, StreamSerializer streamSerializerStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        } else if (!directory.canRead() || !directory.canWrite())
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable directory");
        this.streamSerializerStrategy = streamSerializerStrategy;
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] listFile = directory.listFiles();
        if (listFile != null) {
            for (File file : listFile) {
                deleteElement(file);
            }
        }
    }

    @Override
    protected void updateElement(File file, Resume resume) {
        try {
            streamSerializerStrategy.doWrite(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveElement(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        updateElement(file, resume);
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return streamSerializerStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void deleteElement(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error");
        }
    }

    @Override
    protected List<Resume> getResumeList() {
        File[] listFile = directory.listFiles();
        if (listFile == null) {
            throw new StorageException("Directory read error");
        }
        List<Resume> allResumes = new ArrayList<>();
        for (File file : listFile) {
            allResumes.add(getElement(file));
        }
        return allResumes;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public int size() {
        String[] directoryList = directory.list();
        if (directoryList == null) {
            throw new StorageException("Directory read error");
        }
        return directoryList.length;
    }

    @Override
    protected boolean contains(File file) {
        return file.exists();
    }
}
