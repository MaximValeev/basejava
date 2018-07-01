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

        WorkPeriod universityStudyPeriod = new WorkPeriod("Energy and Resource-Saving Processes in " +
                "Chemical Engineering, Pztroleum Chemistry and Biotechnology", null,
                DateUtil.of(2012, Month.SEPTEMBER),
                DateUtil.of(2016, Month.JULY));
        List<WorkPeriod> universityStudyPeriods = new ArrayList<>();
        universityStudyPeriods.add(universityStudyPeriod);
        Place universityStudy = new Place("FEFU", "https://www.dvfu.ru/", universityStudyPeriods);

        WorkPeriod magistracyPeriod = new WorkPeriod("Chemical Technology",
                "Not interesting description", DateUtil.of(2016, Month.SEPTEMBER),
                DateUtil.of(2018, Month.AUGUST));
        universityStudy.addBusyPeriod(magistracyPeriod);

        WorkPeriod onlineStudyPeriod = new WorkPeriod("Android development", null,
                DateUtil.of(2017, Month.SEPTEMBER),
                LocalDate.now());
        WorkPeriod onlineStudyPeriod2 = new WorkPeriod("SEO", null,
                DateUtil.of(2012, Month.SEPTEMBER),
                DateUtil.of(2013, Month.JANUARY));
        List<WorkPeriod> onlineStudyPeriods = new ArrayList<>();
        onlineStudyPeriods.add(onlineStudyPeriod);
        onlineStudyPeriods.add(onlineStudyPeriod2);
        Place onlineStudy = new Place("LoftSchool", "https://loftschool.com/", onlineStudyPeriods);

        List<Place> studyPlaces = new ArrayList<>();
        studyPlaces.add(universityStudy);
        studyPlaces.add(onlineStudy);
        resume.setSection(EDUCATION, new SectionPlace(studyPlaces));

        WorkPeriod dnsShopPeriod = new WorkPeriod("Q.A. Engineer", null,
                DateUtil.of(2017, Month.SEPTEMBER), LocalDate.now());
        List<WorkPeriod> dnsShopPeriods = new ArrayList<>();
        dnsShopPeriods.add(dnsShopPeriod);
        Place dnsShop = new Place("DNS", "https://www.dns-shop.ru/", dnsShopPeriods);
        List<Place> workPlace = new ArrayList<>();
        workPlace.add(dnsShop);
        resume.setSection(EXPERIENCE, new SectionPlace(workPlace));

        System.out.println(resume.toString());
    }
}
