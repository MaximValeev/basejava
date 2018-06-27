package model;

public enum ContactType {
    PHONE("Телефон"),
    EMAIL("Почта"),
    GITHUB("Профиль в GitHub"),
    WEBPAGE("Домашняя страница");


    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
