package storage.serializer;

import model.Resume;
import util.JsonParser;

import java.io.*;

public class JsonStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream)) {
            JsonParser.write(writer, resume);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
