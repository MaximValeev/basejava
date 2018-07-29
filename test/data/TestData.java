package data;

import model.*;

import java.time.Month;
import java.util.UUID;

import static model.ContactType.*;
import static model.SectionType.*;
import static model.SectionType.QUALIFICATIONS;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final String UUID_5 = UUID.randomUUID().toString();

    private static final String FULL_NAME_1 = "Nathan";
    private static final String FULL_NAME_2 = "Kratos";
    private static final String FULL_NAME_3 = "Link";
    private static final String FULL_NAME_4 = "Joel";
    private static final String FULL_NAME_5 = "Zelda";

    public static final Resume resume1;
    public static final Resume resume2;
    public static final Resume resume3;
    public static final Resume resume4;
    public static Resume resume5;

    static {
        resume1 = new Resume(UUID_1, FULL_NAME_1);
        resume2 = new Resume(UUID_2, FULL_NAME_2);
        resume3 = new Resume(UUID_3, FULL_NAME_3);
        resume4 = new Resume(UUID_4, FULL_NAME_4);
        resume5 = new Resume(UUID_5, FULL_NAME_5);

        resume1.addContact(PHONE, "1234567");
        resume1.addContact(GITHUB, "GITHUB web page");
        resume1.addContact(MAIL, "resume1@mail.ru");

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

        resume2.addContact(MAIL, "mmm@mail.ru");
        resume2.addContact(PHONE, "222222222");
        resume2.addContact(HOME_PAGE, "resume2webPage");
        resume2.addSection(PERSONAL, new SectionText("persona2Resume2"));
        resume2.addSection(OBJECTIVE, new SectionText("objectiveResume2"));
        resume2.addSection(ACHIEVEMENTS, new SectionItemsList("achievement1Resume2", "achievement2Resume2", "achievement3Resume2"));
        resume2.addSection(QUALIFICATIONS, new SectionItemsList("qualification1Resume2", "qualification2Resume2", "qualification3Resume2"));
        resume2.addSection(EXPERIENCE,
                new SectionPlace(
                        new Place("resume2Place", null,
                                new Place.WorkPosition("resume2Worker1", null, 2010, Month.DECEMBER)),
                        new Place("resume2Place2", "resume2Place2.ru",
                                new Place.WorkPosition("resume2Worker2", "some description", 2005, Month.SEPTEMBER, 2009, Month.JULY))
                ));

    }
}
