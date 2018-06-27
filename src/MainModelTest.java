import model.*;

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

        Place universityStudy = new Place("FEFU", "Energy and Resource-Saving Processes in Chemical Engineering, Petroleum Chemistry and Biotechnology");
        Place onlineStudy = new Place("LoftSchool.com", "Android development");
        List<Place> studyPlaces = new ArrayList<>();
        studyPlaces.add(universityStudy);
        studyPlaces.add(onlineStudy);
        resume.setSection(EDUCATION, new SectionPlace(studyPlaces));

        Place dnsShop = new Place("dns-shop.ru", "Q.A. Engineer of mobile department");
        List<Place> workPlace = new ArrayList<>();
        workPlace.add(dnsShop);
        resume.setSection(EXPERIENCE, new SectionPlace(workPlace));

        System.out.println(resume.toString());
    }
}
