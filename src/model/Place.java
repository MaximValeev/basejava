package model;

public class Place {
    private String title;
    private String description;

    public Place(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Place: " + title + " \ndescription: " + description + '.';
    }
}
