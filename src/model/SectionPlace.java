package model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SectionPlace extends Section {
    private static final long serialVersionUID = 1L;

    private final List<Place> places;

    public SectionPlace(Place... places) {
        this(Arrays.asList(places));
    }

    public SectionPlace(List<Place> places) {
        Objects.requireNonNull(places, "places must not be null");
        this.places = places;
    }

    public List<Place> getPlaces() {
        return places;
    }

    @Override
    public String toString() {
        return places.stream().map(Place::toString).collect(Collectors.joining(""));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SectionPlace that = (SectionPlace) o;

        return places.equals(that.places);
    }

    @Override
    public int hashCode() {
        return places.hashCode();
    }
}
