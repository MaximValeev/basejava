package model;

import java.util.Objects;

public class SectionText extends Section {
    private static final long serialVersionUID = 1L;

    private String content;

    public SectionText(String content) {
        Objects.requireNonNull(content, "places must not be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SectionText that = (SectionText) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
