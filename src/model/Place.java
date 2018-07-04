package model;

import util.DateUtil;

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

public class Place implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<WorkPosition> workPositions = new ArrayList<>();

    public Place(String name, String url, WorkPosition... workPositions) {
        this(new Link(name, url), Arrays.asList(workPositions));
    }

    public Place(Link homePage, List<WorkPosition> workPositions) {
        this.homePage = homePage;
        this.workPositions = workPositions;
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


    public static class WorkPosition implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String title;
        private final String description;
        private final LocalDate startDate;
        private final LocalDate endDate;

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
            this.description = description;
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


