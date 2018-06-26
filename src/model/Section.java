package model;

public abstract class Section<T> {
    private T data;

    Section(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
