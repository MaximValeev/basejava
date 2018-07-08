package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * com.urise.webapp.model.model.Resume class
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private String uuid;

    private String fullName;

    private Map<ContactType, String> contacts;

    private Map<SectionType, Section> sections;

    public Resume() {
    }

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

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
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

    public void addSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }


}
