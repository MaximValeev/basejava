package storage;

import static org.junit.Assert.assertEquals;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void update() {
        storage.update(resume2);
        assertEquals(resume2, storage.get(FULL_NAME_2));
    }

    @Override
    public void save() {
        storage.save(resume5);
        assertEquals(resume5, storage.get(FULL_NAME_5));
    }

    @Override
    public void get() {
        assertEquals(resume3, storage.get(FULL_NAME_3));
    }

    @Override
    public void getNotExist() {
        storage.get(FULL_NAME_5);
    }

    @Override
    public void delete() {
        storage.delete(FULL_NAME_3);
        assertEquals(3, storage.size());
    }

    @Override
    public void deleteNotExist() {
        storage.delete(FULL_NAME_5);
    }
}