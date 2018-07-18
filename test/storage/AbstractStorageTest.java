package storage;

import config.Config;
import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();

    Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    protected static final String FULL_NAME_1 = "Nathan";
    protected static final String FULL_NAME_2 = "Kratos";
    protected static final String FULL_NAME_3 = "Link";
    protected static final String FULL_NAME_4 = "Joel";
    protected static final String FULL_NAME_5 = "Zelda";

    public static final Resume resume1;
    public static final Resume resume2;
    public static final Resume resume3;
    public static final Resume resume4;
    public static final Resume resume5;

    static {
        resume1 = new Resume(UUID_1, FULL_NAME_1);
        resume2 = new Resume(UUID_2, FULL_NAME_2);
        resume3 = new Resume(UUID_3, FULL_NAME_3);
        resume4 = new Resume(UUID_4, FULL_NAME_4);
        resume5 = new Resume(UUID_5, FULL_NAME_5);

        /*resume1.addContact(PHONE, "1234567");
        resume1.addContact(GITHUB, "GITHUB web page");

        resume1.addSection(PERSONAL, new SectionText("personalResume1"));
        resume1.addSection(OBJECTIVE, new SectionText("objectiveResume1"));
        resume1.addSection(ACHIEVEMENTS, new SectionItemsList("achievement1Resume1", "achievement2Resume1", "achievement3Resume1"));
        resume1.addSection(QUALIFICATIONS, new SectionItemsList("qualification1Resume1", "qualification2Resume1", "qualification3Resume1"));
        resume1.addSection(EXPERIENCE,
                new SectionPlace(
                        new Place("resume1Place", null,
                                new Place.WorkPosition("resume1Worker1", null, 2000, Month.DECEMBER, 2003, Month.JULY)),
                        new Place("resume1Place2", "resume1WorkPlace2.ru",
                                new Place.WorkPosition("resume1Worker2", "some description", 2005, Month.SEPTEMBER))
                ));
        resume1.addSection(EDUCATION,
                new SectionPlace(
                        new Place("resume1University", "fefu.ru",
                                new Place.WorkPosition("resume1Speciality", "boring", 2012, Month.SEPTEMBER, 2016, Month.JULY),
                                new Place.WorkPosition("resume1Magistracy", "2X Boring", 2016, Month.SEPTEMBER, 2018, Month.JULY))
                ));

        resume2.addContact(EMAIL, "mmm@mail.ru");
        resume2.addContact(WEBPAGE, "com.com");
        resume2.addSection(EXPERIENCE,
                new SectionPlace(
                        new Place("resume2Place", null,
                                new Place.WorkPosition("resume2Worker1", null, 2010, Month.DECEMBER)),
                        new Place("resume2Place2", "resume2Place2.ru",
                                new Place.WorkPosition("resume2Worker2", "some description", 2005, Month.SEPTEMBER, 2009, Month.JULY))
                ));*/

    }


    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
        storage.save(resume4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_2, "New Name");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_2));

    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resume5);
    }

    @Test
    public void save() {
        storage.save(resume5);
        assertEquals(resume5, storage.get(UUID_5));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(resume3);
    }

    @Test
    public void get() {
        assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_5);
    }

    @Test
    public void delete() {
        storage.delete(UUID_3);
        assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_5);
    }

    @Test
    public void getAllSorted() {
        List<Resume> benchmarkList = new ArrayList<>();
        benchmarkList.add(resume4);
        benchmarkList.add(resume2);
        benchmarkList.add(resume3);
        benchmarkList.add(resume1);
        List<Resume> sortedList = storage.getAllSorted();
        Assert.assertEquals(benchmarkList, sortedList);
    }


    @Test
    public void size() {
        assertEquals(4, storage.size());
    }
}