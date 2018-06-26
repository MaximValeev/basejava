package model;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static model.SectionType.*;

/**
 * com.urise.webapp.model.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private HashMap<ContactType, String> contacts;

    private HashMap<SectionType, Section> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        contacts = new HashMap<>();
        sections = new HashMap<>();
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
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int nameCompare = fullName.compareTo(o.getFullName());
        return nameCompare != 0 ? nameCompare : uuid.compareTo(o.getUuid());
    }

    public void setContact(ContactType contact, String data) {
        contacts.put(contact, data);
    }

    public String getContact(ContactType contact) {
        return contacts.get(contact);
    }

    public void setPersonalData(String data) {
        sections.put(PERSONAL, new SectionText(data));
    }

    public String getPersonalData() {
        if (sections.get(PERSONAL) == null) {
            setPersonalData(null);
        }
        return (String) sections.get(PERSONAL).getData();
    }

    public void setObjectiveData(String data) {
        sections.put(OBJECTIVE, new SectionText(data));
    }

    public String getObjectiveData() {
        if (sections.get(OBJECTIVE) == null) {
            setObjectiveData(null);
        }
        return (String) sections.get(OBJECTIVE).getData();
    }

    public void setAchievementsData(List<String> data) {
        sections.put(ACHIEVEMENTS, new SectionTextList(data));
    }

    public List<String> getAchievementsData() {
        if (sections.get(ACHIEVEMENTS) == null) {
            setAchievementsData(null);
        }
        return (List<String>) sections.get(ACHIEVEMENTS).getData();
    }

    public void setQualificationData(List<String> data) {
        sections.put(QUALIFICATIONS, new SectionTextList(data));
    }

    public List<String> getQualificationData() {
        if (sections.get(QUALIFICATIONS) == null) {
            setQualificationData(null);
        }
        return (List<String>) sections.get(QUALIFICATIONS).getData();
    }

    public void setExperienceData(HashMap<String, String> data) {
        sections.put(EXPERIENCE, new SectionTitleAndText(data));
    }

    public HashMap<String, String> getExperienceData() {
        if (sections.get(EXPERIENCE) == null) {
            setExperienceData(null);
        }
        return (HashMap<String, String>) sections.get(EXPERIENCE).getData();
    }

    public void setEducationData(HashMap<String, String> data) {
        sections.put(EDUCATION, new SectionTitleAndText(data));
    }

    public HashMap<String, String> getEducationData() {
        if (sections.get(EDUCATION) == null) {
            setEducationData(null);
        }
        return (HashMap<String, String>) sections.get(EDUCATION).getData();
    }


}
