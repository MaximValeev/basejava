package model;

public class SectionText extends Section {
    private String data;

    public SectionText(String content) {
        this.data = content;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return data + '\n';
    }
}
