package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;

import java.util.Arrays;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    @Before
    public void setUp() throws Exception {
        storage = initStorage();
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_4));
        storage.save(new Resume(UUID_5));
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2);
        storage.update(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        Resume resume = new Resume("nonsexistResume");
        storage.update(resume);
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume("uuid6");
        storage.save(resume);
        Assert.assertEquals("uuid6", storage.get(resume.getUuid()).toString());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        Resume resume = new Resume("uuid3");
        storage.save(resume);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        for (int i = 1; i <= 10000; i++) {
            storage.save(new Resume());
        }
    }

    @Test
    public void get() throws Exception {
        Resume expectedResume = storage.get(UUID_3);
        Assert.assertEquals(UUID_3, expectedResume.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("nonsexistResume");
    }

    @Test
    public void delete() throws Exception {
        Assert.assertEquals(5, storage.getAll().length);
        storage.delete(UUID_5);
        Assert.assertEquals(4, storage.getAll().length);
        storage.delete(UUID_1);
        Assert.assertEquals(3, storage.getAll().length);
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.getAll().length);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("nonsexistResume");
    }

    @Test
    public void getAll() throws Exception {
        Resume[] allResumes = storage.getAll();
        Assert.assertFalse(Arrays.asList(allResumes).contains(null));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(5, storage.size());
    }

    abstract AbstractArrayStorage initStorage();
}