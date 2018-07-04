package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    public ObjectStreamPathStorage(String directory) {
        super(directory);
    }

    @Override
    protected void doWrite(OutputStream os, Resume resume) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    protected Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            try {
                return (Resume) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new StorageException("Error read resume", null, e);
            }
        }
    }
}
