package util;

import model.Resume;
import model.Section;
import model.SectionText;
import org.junit.Assert;
import org.junit.Test;

import static data.TestData.resume1;

public class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(resume1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(resume1, resume);
    }

    @Test
    public void write() {
        Section section1 = new SectionText("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}