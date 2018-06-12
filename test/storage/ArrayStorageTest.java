package storage;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    @Override
    AbstractArrayStorage initStorage() {
        return new ArrayStorage();
    }
}