package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    private Resume resume1 = new Resume(UUID_1);
    private Resume resume2 = new Resume(UUID_2);
    private Resume resume3 = new Resume(UUID_3);
    private Resume resume4 = new Resume(UUID_4);
    private Resume resume5 = new Resume(UUID_5);

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Rule
    public ExpectedException notExistStorageException = ExpectedException.none();

    @Rule
    public ExpectedException storageOverflow = ExpectedException.none();

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
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resumeBeforeUpdate = storage.get(UUID_2);
        storage.update(resume2);
        Assert.assertEquals(resumeBeforeUpdate, resume2);

    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resume5);
    }

    @Test
    public void save() {
        storage.save(resume5);
        Assert.assertEquals(resume5, storage.get(resume5.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(resume3);
    }

    @Test
    public void saveStorageOverflow() {
        for (int i = 4; i < 10000; i++) {
            storage.save(new Resume());
        }

        try {
            storage.save(resume5);
            Assert.fail("Expected StorageException");
        } catch (StorageException e) {
            Assert.assertEquals("Storage overflow", e.getMessage());
        }
    }

    @Test
    public void get() {
        Resume expectedResume = storage.get(UUID_3);
        Assert.assertEquals(UUID_3, expectedResume.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(resume5.getUuid());
    }

    @Test
    public void delete() {
        storage.delete(UUID_3);
        Assert.assertEquals(3, storage.getAll().length);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(resume5.getUuid());
    }

    @Test
    public void getAll() {
        Resume[] allResumes = {resume1, resume2, resume3, resume4};
        Assert.assertTrue(Arrays.equals(allResumes, storage.getAll()));
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }

}