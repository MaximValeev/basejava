package model;

import util.DateUtil;
import util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static util.DateUtil.NOW;
import static util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Place implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<WorkPosition> workPositions = new ArrayList<>();

    public Place() {
    }

    public Place(String name, String url, WorkPosition... workPositions) {
        this(new Link(name, url), Arrays.asList(workPositions));
    }

    public Place(Link homePage, List<WorkPosition> workPositions) {
        this.homePage = homePage;
        this.workPositions = workPositions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<WorkPosition> getWorkPositions() {
        return workPositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(homePage, place.homePage) &&
                Objects.equals(workPositions, place.workPositions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(homePage, workPositions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(homePage).append('\n');
        for (WorkPosition wp : workPositions) {
            sb.append(wp.toString()).append('\n');
        }
        return sb.toString();


    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class WorkPosition implements Serializable {
        private static final long serialVersionUID = 1L;

        private String title;
        private String description;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;

        public WorkPosition() {
        }

        public WorkPosition(String title, String description, int startYear, Month startMonth) {
            this(title, description, DateUtil.of(startYear, startMonth), NOW);
        }

        public WorkPosition(String title, String description, int startYear, Month startMonth, int endYear, Month endMonth) {
            this(title, description, of(startYear, startMonth), of(endYear, endMonth));
        }

        public WorkPosition(String title, String description, LocalDate startDate, LocalDate endDate) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.title = title;
            this.description = description != null ? description : "";
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
            return formatter.format(startDate) + " - " + formatter.format(endDate) + " " + title + '\n' + description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WorkPosition that = (WorkPosition) o;
            return Objects.equals(title, that.title) &&
                    Objects.equals(description, that.description) &&
                    Objects.equals(startDate, that.startDate) &&
                    Objects.equals(endDate, that.endDate);
        }

        @Override
        public int hashCode() {

            return Objects.hash(title, description, startDate, endDate);
        }
    }
}


