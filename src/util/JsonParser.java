package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Section;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(Writer writer, T object) {
        GSON.toJson(object, writer);
    }

    public static <T> T read(String secValue, Class<T> clazz) {
        return GSON.fromJson(secValue, clazz);
    }

    public static <T> String write(T obj) {
        return GSON.toJson(obj);
    }

    public static <T> String write(T obj, Class<T> clazz) {
        return GSON.toJson(obj, clazz);

    }
}
