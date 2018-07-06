package storage;

import inputOutput.ObjectWriteReadToFile;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectWriteReadToFile()));
    }
}