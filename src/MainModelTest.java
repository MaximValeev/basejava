import model.*;
import util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static model.SectionType.*;

public class MainModelTest {
    public static void main(String[] args) {
        Resume resume = new Resume("Link");

        resume.setContact(ContactType.PHONE, "8964-450-58-84");
        resume.setSection(PERSONAL, new SectionText("Humble but brave."));
        resume.setSection(OBJECTIVE, new SectionText("Protagonist of Nintendo's video" +
                " game series The Legend of Zelda."));

        List<String> achievements = new ArrayList<>();
        achievements.add("Saved Zelda.");
        achievements.add("Better than Mario.");
        achievements.add("Defends Hyrule.");
        resume.setSection(ACHIEVEMENTS, new SectionItemsList(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("Possession of a sword and shield.");
        qualifications.add("Agility.");
        qualifications.add("Analytical mind.");
        resume.setSection(QUALIFICATIONS, new SectionItemsList(qualifications));

        Place universityStudy = new Place("FEFU", "https://www.dvfu.ru/",
                DateUtil.of(2012, Month.SEPTEMBER),
                DateUtil.of(2016, Month.JULY), "Energy and Resource-Saving Processes in " +
                "Chemical Engineering, Pztroleum Chemistry and Biotechnology", null);
        universityStudy.addBusyPeriod(DateUtil.of(2016, Month.SEPTEMBER),
                DateUtil.of(2018, Month.AUGUST), "Chemical Technology",
                "Not interesting description");

        Place onlineStudy = new Place("LoftSchool", "https://loftschool.com/",
                DateUtil.of(2017, Month.SEPTEMBER),
                LocalDate.now(), "Android development", null);
        List<Place> studyPlaces = new ArrayList<>();
        studyPlaces.add(universityStudy);
        studyPlaces.add(onlineStudy);
        resume.setSection(EDUCATION, new SectionPlace(studyPlaces));

        Place dnsShop = new Place("DNS", "dns-shop.ru",
                DateUtil.of(2017, Month.SEPTEMBER),
                LocalDate.now(),
                "Q.A. Engineer", null);
        List<Place> workPlace = new ArrayList<>();
        workPlace.add(dnsShop);
        resume.setSection(EXPERIENCE, new SectionPlace(workPlace));

        System.out.println(resume.toString());
    }
}
