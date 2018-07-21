package storage;

import config.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }
}