package storage.serializer;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeSequence(dos, resume.getContacts().entrySet(), contactsEntry -> {
                dos.writeUTF(contactsEntry.getKey().name());
                dos.writeUTF(contactsEntry.getValue());
            });
            writeSequence(dos, resume.getSections().entrySet(), sectionsEntry -> {
                String sectionName = sectionsEntry.getKey().name();
                dos.writeUTF(sectionName);
                switch (SectionType.valueOf(sectionName)) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((SectionText) sectionsEntry.getValue()).getContent());
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENTS:
                        writeSequence(dos, ((SectionItemsList) sectionsEntry.getValue()).getItems(), dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeSequence(dos, ((SectionPlace) sectionsEntry.getValue()).getPlaces(), place -> {
                            writeSequence(dos, place.getWorkPositions(), workPosition -> {
                                dos.writeUTF(workPosition.getTitle());
                                dos.writeUTF(workPosition.getDescription());
                                writeDate(dos, workPosition.getStartDate());
                                writeDate(dos, workPosition.getEndDate());
                            });
                            dos.writeUTF(place.getHomePage().getName());
                            dos.writeUTF(place.getHomePage().getUrl());
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readSequence(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readSequence(dis, () -> {
                SectionType section = SectionType.valueOf(dis.readUTF());
                resume.addSection(section, readSection(dis, section));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new SectionText(dis.readUTF());
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                List<String> items = new ArrayList<>();
                readSequence(dis, () -> items.add(dis.readUTF()));
                return new SectionItemsList(items);
            case EDUCATION:
            case EXPERIENCE:
                List<Place> places = new ArrayList<>();
                readSequence(dis, () -> {
                    List<Place.WorkPosition> workPositions = new ArrayList<>();
                    readSequence(dis, () ->
                            workPositions.add(new Place.WorkPosition(dis.readUTF(), dis.readUTF(),
                                    LocalDate.of(dis.readInt(), dis.readInt(), 1),
                                    LocalDate.of(dis.readInt(), dis.readInt(), 1))));
                    places.add(new Place(new Link(dis.readUTF(), dis.readUTF()), workPositions));
                });
                return new SectionPlace(places);
            default:
                throw new IllegalStateException();
        }
    }


    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
    }

    private interface unitWriter<T> {
        void write(T unit) throws IOException;
    }

    private interface unitReader {
        void read() throws IOException;

    }

    private <T> void writeSequence(DataOutputStream dos, Collection<T> collection, unitWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T unit : collection) {
            writer.write(unit);
        }
    }

    private void readSequence(DataInputStream dis, unitReader reader) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            reader.read();
        }
    }
}
