package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Place {
    private Link homePage;
    private List<WorkPeriod> workPeriods = new ArrayList<>();

    public Place(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        this.workPeriods.add(new WorkPeriod(title, description, startDate, endDate));
    }

    public void addBusyPeriod(LocalDate startDate, LocalDate endDate, String title, String description) {
        this.workPeriods.add(new WorkPeriod(title, description, startDate, endDate));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(homePage).append('\n');
        for (WorkPeriod wp : workPeriods) {
            sb.append(wp.toString()).append('\n');
        }
        return sb.toString();
    }

    private class WorkPeriod {
        private final String title;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;

        WorkPeriod(String title, String description, LocalDate startDate, LocalDate endDate) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.title = title;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
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

            WorkPeriod that = (WorkPeriod) o;

            if (!title.equals(that.title)) return false;
            if (description != null ? !description.equals(that.description) : that.description != null) return false;
            if (!startDate.equals(that.startDate)) return false;
            return endDate.equals(that.endDate);
        }

        @Override
        public int hashCode() {
            int result = title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            result = 31 * result + startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            return result;
        }
    }
}


