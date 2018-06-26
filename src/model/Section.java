package model;

abstract class Section<T> {
    private T data;

    Section(T data) {
        this.data = data;
    }

    T getData() {
        return data;
    }
}
