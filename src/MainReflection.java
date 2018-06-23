import model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) {
        Resume resume = new Resume("");
        Method resumeToString;

        try {
            resumeToString = resume.getClass().getMethod("toString", (Class<?>[]) null);
            System.out.println("Invocation resume.toString() via reflection: " + resumeToString.invoke(resume));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        System.out.println("Standard resume.toString() invocation: " + resume.getUuid());

    }
}
