package storage;

import inputOutput.ObjectWriteReadToFile;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectWriteReadToFile()) {
        });
    }
}