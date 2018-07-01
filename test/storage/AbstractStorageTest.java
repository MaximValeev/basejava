package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static model.SectionType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class AbstractStorageTest {

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

    Resume resume1 = new Resume(UUID_1, FULL_NAME_1);
    Resume resume2 = new Resume(UUID_2, FULL_NAME_2);
    Resume resume3 = new Resume(UUID_3, FULL_NAME_3);
    Resume resume4 = new Resume(UUID_4, FULL_NAME_4);
    Resume resume5 = new Resume(UUID_5, FULL_NAME_5);

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();

        resume1.setContact(ContactType.PHONE, "8964-450-58-84");
        resume1.setSection(PERSONAL, new SectionText("Humble but brave."));
        resume1.setSection(OBJECTIVE, new SectionText("Protagonist of Nintendo's video" +
                " game series The Legend of Zelda."));

        List<String> achievements = new ArrayList<>();
        achievements.add("Saved Zelda.");
        achievements.add("Better than Mario.");
        achievements.add("Defends Hyrule.");
        resume1.setSection(ACHIEVEMENTS, new SectionItemsList(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("Possession of a sword and shield.");
        qualifications.add("Agility.");
        qualifications.add("Analytical mind.");
        resume1.setSection(QUALIFICATIONS, new SectionItemsList(qualifications));

        WorkPeriod universityStudyPeriod = new WorkPeriod("resume 1 speciality title", null,
                DateUtil.of(2012, Month.SEPTEMBER), DateUtil.of(2016, Month.JULY));
        WorkPeriod universitySecondPeriod = new WorkPeriod("resume 1 second speciality title",
                "Not interesting description", DateUtil.of(2016, Month.SEPTEMBER),
                DateUtil.of(2018, Month.AUGUST));
        List<WorkPeriod> universityStudyPeriods = new ArrayList<>();
        universityStudyPeriods.add(universityStudyPeriod);
        universityStudyPeriods.add(universitySecondPeriod);
        Place universityStudy = new Place("Resume 1 study place", "https://www.Resume1studyplace.ru/",
                universityStudyPeriods);

        WorkPeriod onlineStudyPeriod = new WorkPeriod("SEO", "All about SEO",
                DateUtil.of(2017, Month.SEPTEMBER), LocalDate.now());
        List<WorkPeriod> onlineStudyPeriods = new ArrayList<>();
        onlineStudyPeriods.add(onlineStudyPeriod);
        Place onlineStudy = new Place("resume1 online school", "https://resume1OnlineSchool.com/", onlineStudyPeriods);

        List<Place> studyPlaces = new ArrayList<>();
        studyPlaces.add(universityStudy);
        studyPlaces.add(onlineStudy);
        resume1.setSection(EDUCATION, new SectionPlace(studyPlaces));

        WorkPeriod dnsShopPeriod = new WorkPeriod("BigBoss", null, DateUtil.of(2017, Month.SEPTEMBER),
                LocalDate.now());
        List<WorkPeriod> dnsShopPeriods = new ArrayList<>();
        dnsShopPeriods.add(dnsShopPeriod);
        Place dnsShop = new Place("resume1 workplace", "workForResume1.com", dnsShopPeriods);
        List<Place> workPlace = new ArrayList<>();
        workPlace.add(dnsShop);
        resume1.setSection(EXPERIENCE, new SectionPlace(workPlace));
        storage.save(resume1);

        resume2.setContact(ContactType.PHONE, "8964-450-58-84");
        resume2.setContact(ContactType.WEBPAGE, "resume2Web.ru");
        resume2.setSection(PERSONAL, new SectionText("resume2 personal data."));
        resume2.setSection(OBJECTIVE, new SectionText("resume2 objective data."));

        List<String> achievementsResume2 = new ArrayList<>();
        achievements.add("resume2 achievements1");
        achievements.add("resume2 achievements2");
        achievements.add("resume2 achievements3");
        resume2.setSection(ACHIEVEMENTS, new SectionItemsList(achievementsResume2));

        List<String> qualificationsResume2 = new ArrayList<>();
        qualifications.add("resume2 qualifications1");
        qualifications.add("resume2 qualifications2");
        qualifications.add("resume2 qualifications3");
        resume2.setSection(QUALIFICATIONS, new SectionItemsList(qualificationsResume2));

        WorkPeriod universityStudyResume2WorkPeriod = new WorkPeriod("Resume2TitleEducationPlace1",
                "Resume2DescriptionEducationPlace1", DateUtil.of(2005, Month.JULY),
                DateUtil.of(2010, Month.JULY));
        WorkPeriod universityStudyResume2NewPeriod = new WorkPeriod("Resume2TitleEducationPlace2",
                "Resume2TitleEducationPlace2", DateUtil.of(2011, Month.SEPTEMBER),
                DateUtil.of(2019, Month.JULY));
        List<WorkPeriod> universityResume2Periods = new ArrayList<>();
        universityResume2Periods.add(universityStudyResume2WorkPeriod);
        universityResume2Periods.add(universityStudyResume2NewPeriod);
        Place universityStudyResume2 = new Place("MIT", "http://web.mit.edu//",
                universityResume2Periods);

        WorkPeriod onlineStudyResume2Period = new WorkPeriod("Java development", null, DateUtil.of(2017, Month.AUGUST),
                LocalDate.now());
        List<WorkPeriod> onlineStudyResume2Periods = new ArrayList<>();
        onlineStudyResume2Periods.add(onlineStudyResume2Period);
        Place onlineStudyResume2 = new Place("topjava", "https://topjava.ru/", onlineStudyResume2Periods);
        List<Place> studyPlacesResume2 = new ArrayList<>();
        studyPlaces.add(universityStudyResume2);
        studyPlaces.add(onlineStudyResume2);
        resume2.setSection(EDUCATION, new SectionPlace(studyPlacesResume2));

        WorkPeriod dnsShopResume2Period = new WorkPeriod("Product Manager", null,
                DateUtil.of(2010, Month.JULY),
                LocalDate.now());
        List<WorkPeriod> dnsShopResume2Periods = new ArrayList<>();
        dnsShopResume2Periods.add(dnsShopResume2Period);
        Place dnsShopResume2 = new Place("DNSResume2", "dns-shop.ruResume2", dnsShopResume2Periods);
        List<Place> workPlaceResume2 = new ArrayList<>();
        workPlace.add(dnsShopResume2);
        resume2.setSection(EXPERIENCE, new SectionPlace(workPlaceResume2));

        storage.save(resume2);
        storage.save(resume3);
        storage.save(resume4);

        resume5.setContact(ContactType.PHONE, "8964-450-58-84");
        resume5.setContact(ContactType.EMAIL, "Jake@mail.ru");
        resume5.setSection(PERSONAL, new SectionText("resume5 personal data"));
        resume5.setSection(OBJECTIVE, new SectionText("resume5 objective data"));

        List<String> achievementsResume5 = new ArrayList<>();
        achievements.add("resume5 achievements1");
        achievements.add("resume5 achievements2");
        achievements.add("resume5 achievements3");
        resume5.setSection(ACHIEVEMENTS, new SectionItemsList(achievementsResume5));

        List<String> qualificationsResume5 = new ArrayList<>();
        qualifications.add("resume5 qualifications1");
        qualifications.add("resume5 qualifications2");
        qualifications.add("resume5 qualifications3");
        resume5.setSection(QUALIFICATIONS, new SectionItemsList(qualificationsResume5));

        WorkPeriod universityStudyResume5Period = new WorkPeriod("resume5 speciality title", null,
                DateUtil.of(1999, Month.SEPTEMBER), DateUtil.of(2005, Month.JULY));
        List<WorkPeriod> universityStudyResume5Periods = new ArrayList<>();
        universityStudyResume5Periods.add(universityStudyResume5Period);
        Place universityStudyResume5 = new Place("resume5 university", "https://www.resume5.ru/",
                universityStudyResume5Periods);
        WorkPeriod universityResume5SecondPeriod = new WorkPeriod("resume5 speciality 2 title",
                "Not interesting description", DateUtil.of(2006, Month.SEPTEMBER),
                DateUtil.of(2010, Month.AUGUST));
        universityStudy.addBusyPeriod(universityResume5SecondPeriod);
        WorkPeriod onlineStudyResume5Period = new WorkPeriod("Android development", null,
                DateUtil.of(2017, Month.SEPTEMBER), LocalDate.now());
        List<WorkPeriod> onlineStudyResume5Periods = new ArrayList<>();
        onlineStudyResume5Periods.add(onlineStudyResume5Period);
        Place onlineStudyResume5 = new Place("resume5 online study", "https://resume5online.com/",
                onlineStudyResume5Periods);
        List<Place> studyPlacesResume5 = new ArrayList<>();
        studyPlaces.add(universityStudyResume5);
        studyPlaces.add(onlineStudyResume5);
        resume5.setSection(EDUCATION, new SectionPlace(studyPlacesResume5));

        WorkPeriod dnsShopResume5Period = new WorkPeriod("resume5 Worker", null,
                DateUtil.of(2017, Month.SEPTEMBER), LocalDate.now());
        List<WorkPeriod> dnsShopResume5Periods = new ArrayList<>();
        dnsShopResume5Periods.add(dnsShopResume5Period);
        Place dnsShopResume5 = new Place("resume5 Workplace", "resume5Workplace.ru", dnsShopResume5Periods);
        List<Place> workPlaceResume5 = new ArrayList<>();
        workPlace.add(dnsShopResume5);
        resume5.setSection(EXPERIENCE, new SectionPlace(workPlaceResume5));
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
        assertSame(newResume, storage.get(UUID_2));

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