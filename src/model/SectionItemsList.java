package model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SectionItemsList extends Section {
    private static final long serialVersionUID = 1L;


    private List<String> items;

    public SectionItemsList() {
    }

    public SectionItemsList(String... items) {
        this(Arrays.asList(items));
    }

    public SectionItemsList(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return String.join("\n", items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SectionItemsList that = (SectionItemsList) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
