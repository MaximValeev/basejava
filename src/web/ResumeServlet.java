package web;

import config.Config;
import exception.NotExistStorageException;
import model.*;
import storage.Storage;
import util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Month;

public class ResumeServlet extends HttpServlet {
    private static final long serialVersionUID = -1099922417153534550L;

    private Storage storage = Config.getInstance().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        boolean toUpdate = true;
        if(!uuid.equals("")){
            r = storage.get(uuid);
            r.setFullName(fullName);
        } else {
            toUpdate = false;
            r = new Resume(fullName);
            uuid = r.getUuid();
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (deleteOrAddSection(value)) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    if (deleteOrAddSection(value)) {
                        r.addSection(sectionType, new SectionText(value));
                    } else {
                        r.getSections().remove(sectionType);
                    }
                    break;
                case QUALIFICATIONS:
                case ACHIEVEMENTS:
                    if (deleteOrAddSection(value)) {
                        String[] items = value.split("\r\n");
                        r.addSection(sectionType, new SectionItemsList(items));
                    } else {
                        r.getSections().remove(sectionType);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    String placeName = request.getParameter(sectionType + "placeName");
                    String placeUrl = request.getParameter(sectionType + "placeUrl");
                    String workPosition = request.getParameter(sectionType + "workPosition");
                    String positionDescr = request.getParameter(sectionType + "description");
                    String startDate = request.getParameter(sectionType +"startDate");
                    String endDate = request.getParameter(sectionType +"endDate");

                    if (deleteOrAddSection(placeName)) {
                        r.addSection(sectionType, new SectionPlace(new Place(placeName, placeUrl,
                                new Place.WorkPosition(workPosition, positionDescr, DateUtil.parse(startDate), DateUtil.parse(endDate)))));
                    } else {
                        r.getSections().remove(sectionType);
                    }
                    break;
            }

        }
        if (toUpdate) {
            storage.update(r);
        } else {
            storage.save(r);
        }
        response.sendRedirect("resume?uuid=" + uuid + "&action=view");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "add":
                r = new Resume("", "");
                break;
            case "edit":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + "is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }

    private boolean deleteOrAddSection(String checkString) {
        return (checkString != null && checkString.trim().length() != 0);
    }
}
