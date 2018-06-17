package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    Resume resume1 = new Resume(UUID_1);
    Resume resume2 = new Resume(UUID_2);
    Resume resume3 = new Resume(UUID_3);
    Resume resume4 = new Resume(UUID_4);
    Resume resume5 = new Resume(UUID_5);

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
        storage.save(resume4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(resume2);
        assertEquals(resume2, storage.get(UUID_2));

    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resume5);
    }

    @Test
    public void save() {
        storage.save(resume5);
        assertEquals(resume5, storage.get(UUID_5));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(resume3);
    }

    @Test
    public void get() {
        assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_5);
    }

    @Test
    public void delete() {
        storage.delete(UUID_3);
        assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_5);
    }

    @Test
    public void getAll() {
        Resume[] allResumes = {resume1, resume2, resume3, resume4};
        assertTrue(Arrays.equals(allResumes, storage.getAll()));
    }

    @Test
    public void size() {
        assertEquals(4, storage.size());
    }
}