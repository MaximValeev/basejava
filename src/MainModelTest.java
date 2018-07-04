import model.*;

import java.time.Month;

import static model.SectionType.*;

public class MainModelTest {
    public static void main(String[] args) {
        Resume resume = new Resume("Link");

        resume.addContact(ContactType.PHONE, "8964-450-58-84");
        resume.addSection(PERSONAL, new SectionText("Humble but brave."));
        resume.addSection(OBJECTIVE, new SectionText("Protagonist of Nintendo's video" +
                " game series The Legend of Zelda."));
        resume.addSection(ACHIEVEMENTS, new SectionItemsList("Saved Zelda.", "Better than Mario.", "Defends Hyrule."));
        resume.addSection(QUALIFICATIONS, new SectionItemsList("Possession of a sword and shield.", "Agility.", "Analytical mind."));


        resume.addSection(EDUCATION,
                new SectionPlace(new Place("FEFU", "https://www.dvfu.ru/",
                        new Place.WorkPosition("position1", "description1", 2012, Month.SEPTEMBER, 2016, Month.JULY),
                        new Place.WorkPosition("position2", "description2", 2016, Month.SEPTEMBER, 2018, Month.JULY)),
                        new Place("LoftSchool", "https://LoftSchool.com/",
                                new Place.WorkPosition("androidDevelopment", "description", 2017, Month.SEPTEMBER))));

        resume.addSection(EXPERIENCE, new SectionPlace(new Place("DNS", "https://www.dns-shop.ru/",
                new Place.WorkPosition("Q.A. Engineer", "Android smartPhones testing", 2017, Month.OCTOBER))));

        System.out.println(resume.toString());


    }
}
