package storage;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {


    @Override
    AbstractArrayStorage initStorage() {
        return new SortedArrayStorage();
    }
}