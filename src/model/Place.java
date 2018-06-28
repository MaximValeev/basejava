package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Place {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public Place(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
        return startDate.format(formatter) + " - " + endDate.format(formatter) + "\nPlace: " + title + " \ndescription: " + description + '.';
    }
}
