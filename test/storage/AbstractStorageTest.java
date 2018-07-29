package storage;

import config.Config;
import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static data.TestData.*;
import static model.ContactType.*;
import static model.SectionType.*;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    static final File STORAGE_DIR = Config.getInstance().getStorageDir();

    Storage storage;

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
        newResume.addContact(HOME_PAGE, "resume2updatedWebPage");
        newResume.addContact(MAIL, "resume2@updatedMail.ru");
        newResume.addContact(GITHUB, "resume2github");
        newResume.deleteContact(PHONE);
        newResume.addSection(PERSONAL, new SectionText("persona2Resume2 updated"));
        newResume.deleteSection(OBJECTIVE);
        newResume.addSection(ACHIEVEMENTS, new SectionItemsList("achievement1Resume2 updated", "achievement2Resume2 updated", "achievement3Resume2 updated"));
        newResume.addSection(QUALIFICATIONS, new SectionItemsList("qualification1Resume2 updated", "qualification2Resume2 updated", "qualification3Resume2 updated"));
        newResume.deleteSection(EXPERIENCE);
        newResume.addSection(EDUCATION,
                new SectionPlace(
                        new Place("resume2University", "fefu.ru",
                                new Place.WorkPosition("resume2Speciality", "boring", 2012, Month.SEPTEMBER, 2016, Month.JULY),
                                new Place.WorkPosition("resume2Magistracy", "2X Boring", 2016, Month.SEPTEMBER, 2018, Month.JULY))
                ));
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