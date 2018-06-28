import model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static model.SectionType.*;

public class MainModelTest {
    public static void main(String[] args) {
        Resume resume = new Resume("Link");
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

        Place universityStudy = new Place(
                "FEFU", "Energy and Resource-Saving Processes " +
                "in Chemical Engineering, Pztroleum Chemistry and Biotechnology",
                LocalDate.of(2012, Month.SEPTEMBER, 1),
                LocalDate.of(2016, Month.JULY, 30));
        Place onlineStudy = new Place("LoftSchool.com", "Android development",
                LocalDate.of(2017, Month.SEPTEMBER, 1),
                LocalDate.of(2017, Month.OCTOBER, 30));
        List<Place> studyPlaces = new ArrayList<>();
        studyPlaces.add(universityStudy);
        studyPlaces.add(onlineStudy);
        resume.setSection(EDUCATION, new SectionPlace(studyPlaces));

        Place dnsShop = new Place("dns-shop.ru", "Q.A. Engineer of mobile department",
                LocalDate.of(2017, Month.SEPTEMBER, 28),
                LocalDate.now());
        List<Place> workPlace = new ArrayList<>();
        workPlace.add(dnsShop);
        resume.setSection(EXPERIENCE, new SectionPlace(workPlace));

        System.out.println(resume.toString());
    }
}
