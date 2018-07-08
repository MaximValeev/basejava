package storage.serializer;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> contactEntry : resume.getContacts().entrySet()) {
                dos.writeUTF(contactEntry.getKey().name());
                dos.writeUTF(contactEntry.getValue());
            }

            //TODO implements section

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                String sectionName = entry.getKey().name();
                dos.writeUTF(sectionName);
                switch (SectionType.valueOf(sectionName)) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((SectionText) entry.getValue()).getContent());
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENTS:
                        List<String> items = ((SectionItemsList) entry.getValue()).getItems();
                        dos.writeInt(items.size());
                        for (String item : items) {
                            dos.writeUTF(item);
                        }
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Place> places = ((SectionPlace) entry.getValue()).getPlaces();
                        dos.writeInt(places.size());
                        for (Place place : places) {
                            List<Place.WorkPosition> workPositions = place.getWorkPositions();
                            dos.writeInt(workPositions.size());
                            for (Place.WorkPosition workPosition : workPositions) {
                                dos.writeUTF(workPosition.getTitle());
                                dos.writeUTF(workPosition.getDescription());
                                writeDate(dos, workPosition.getStartDate());
                                writeDate(dos, workPosition.getEndDate());
                            }
                            dos.writeUTF(place.getHomePage().getName());
                            dos.writeUTF(place.getHomePage().getUrl());
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            //TODO implements section

            int sectionCount = dis.readInt();
            for (int i = 0; i < sectionCount; i++) {
                String sectionName = dis.readUTF();
                switch (SectionType.valueOf(sectionName)) {
                    case PERSONAL:
                    case OBJECTIVE:
                        String content = dis.readUTF();
                        resume.addSection(SectionType.valueOf(sectionName), new SectionText(content));
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        int countItems = dis.readInt();
                        String[] items = new String[countItems];
                        for (int j = 0; j < countItems; j++) {
                            items[j] = dis.readUTF();
                        }
                        resume.addSection(SectionType.valueOf(sectionName), new SectionItemsList(items));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int countPlaces = dis.readInt();
                        Place[] places = new Place[countPlaces];
                        for (int k = 0; k < countPlaces; k++) {
                            int countPositions = dis.readInt();
                            Place.WorkPosition[] workPositions = new Place.WorkPosition[countPositions];
                            for (int l = 0; l < countPositions; l++) {
                                workPositions[l] = new Place.WorkPosition(dis.readUTF(), dis.readUTF(),
                                        LocalDate.of(dis.readInt(), dis.readInt(), 1),
                                        LocalDate.of(dis.readInt(), dis.readInt(), 1));
                            }
                            places[k] = new Place(dis.readUTF(), dis.readUTF(), workPositions);
                        }
                        resume.addSection(SectionType.valueOf(sectionName), new SectionPlace(places));
                        break;
                }
            }
            return resume;
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
    }
}
