package model;

import java.util.List;

public class SectionItemsList extends Section {

    private List<String> data;

    public SectionItemsList(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.join("\n", data);
    }
}
