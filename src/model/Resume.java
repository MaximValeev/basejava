package model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * com.urise.webapp.model.model.Resume class
 */
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private Map<ContactType, String> contacts;

    private Map<SectionType, Section> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        contacts = new EnumMap<>(ContactType.class);
        sections = new EnumMap<>(SectionType.class);
        this.fullName = fullName;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override

    public String toString() {

        StringBuilder resultTextResume = new StringBuilder();
        resultTextResume.append(fullName).append('\n');

        for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
            resultTextResume.append(contact.getKey().getTitle()).append('\n');
            resultTextResume.append(contact.getValue()).append('\n');
        }

        for (Map.Entry<SectionType, Section> section : sections.entrySet()) {
            resultTextResume.append(section.getKey().getTitle()).append('\n');
            resultTextResume.append(section.getValue().toString()).append('\n');
        }

        return resultTextResume.toString();


    }

    @Override
    public int compareTo(Resume o) {
        int nameCompare = fullName.compareTo(o.getFullName());
        return nameCompare != 0 ? nameCompare : uuid.compareTo(o.getUuid());
    }

    public void addContact(ContactType contact, String data) {
        contacts.put(contact, data);
    }

    public String getContact(ContactType contact) {
        return contacts.get(contact);
    }

    public Section getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void addSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }


}
