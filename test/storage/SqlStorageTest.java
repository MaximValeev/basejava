package storage;

import config.Config;
import org.junit.Test;

import static org.junit.Assert.*;

public class SqlStorageTest extends AbstractStorageTest {
    private static Config config = Config.getInstance();

    public SqlStorageTest() {
        super(new SqlStorage(config.getDbUrl(), config.getDbUser(), config.getDbPassword()));
    }
}