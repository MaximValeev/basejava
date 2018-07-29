package web;

import config.Config;
import model.Resume;
import storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private static final long serialVersionUID = -1099922417153534550L;

    private Storage storage;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println("<title>Resume table</title>");
        writer.println("<b>Resume table</b>");
        writer.println("<table border = 1>");
        writer.println("<tr>");
        writer.println("<td>UUID</td>");
        writer.println("<td>Full name</td>");
        writer.println("</tr>");
        if (name == null) {
            List<Resume> resumesList = storage.getAllSorted();
            for (Resume resume : resumesList) {
                writer.println("<tr>");
                writer.println("<td>" + resume.getUuid() + "</td>");
                writer.println("<td>" + resume.getFullName() + "</td>");
                writer.println("</tr>");
            }
        } else {
            writer.println("<tr>");
            writer.println("<td>" + storage.get(name).getUuid() + "</td>");
            writer.println("<td>" + storage.get(name).getFullName() + "</td>");
            writer.println("</tr>");
        }
        writer.println("</table>");
    }
}
