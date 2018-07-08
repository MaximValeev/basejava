package storage.serializer;

import model.*;
import util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {

    private XmlParser xmlParser = new XmlParser(Resume.class, Link.class, SectionPlace.class,
            SectionItemsList.class, SectionText.class,
            Place.class, Place.WorkPosition.class);
    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try(Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try(Reader reader =  new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
