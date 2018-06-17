package storage;

import model.Resume;
import org.junit.Assert;

import java.util.Arrays;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void getAll() {
        Resume[] allResumes = {resume1, resume2, resume3, resume4};
        Resume[] allFromMapStorage = storage.getAll();
        Arrays.sort(allFromMapStorage);
        Assert.assertTrue(Arrays.equals(allResumes, allFromMapStorage));
    }
}