package storage;

import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        try {

            for (int i = 4; i < 10000; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Expected StorageException");
        }
        storage.save(resume5);

    }

}