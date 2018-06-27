package model;

import java.util.List;

public class SectionPlace extends Section {
    private List<Place> data;

    public SectionPlace(List<Place> data) {
        this.data = data;
    }

    public List<Place> getData() {
        return data;
    }

    public void setData(List<Place> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder resultContent = new StringBuilder();
        for (Place contentElement : data) {
            resultContent.append(contentElement.toString()).append('\n');
        }
        return resultContent.toString();
    }
}
