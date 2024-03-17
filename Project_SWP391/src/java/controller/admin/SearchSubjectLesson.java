/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.CourseDAO;
import dal.Impl.CourseDAOImpl;
import dal.Impl.LessonDAOImpl;
import dal.LessonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Course;
import model.chapters;
import model.lessons;

/**
 *
 * @author MH
 */
@WebServlet(name = "SearchSubjectLesson", urlPatterns = {"/SearchSubjectLesson"})
public class SearchSubjectLesson extends HttpServlet {

    private static final String VIEW_PATH = "/Dashboard/admin/LessonList.jsp";

    private final LessonDAO daoLesson = new LessonDAOImpl();

    private final CourseDAO daoCourse = new CourseDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lessonKeyword = request.getParameter("lessonKeyword").trim();
        request.setAttribute("lessonKeyword", lessonKeyword);
        
        String action = request.getParameter("action");
        request.setAttribute("action", action);

        String pageIndexS = request.getParameter("page");
        int pageIndex = Integer.parseInt(pageIndexS);
        request.setAttribute("pageIndex", pageIndex);
        int status;
        String statusS = request.getParameter("status");
        if (statusS == null || statusS.isEmpty()) {
            status = -1;
        } else {
            status = Integer.parseInt(statusS);
        }
        request.setAttribute("status", status);

        int courseId = Integer.parseInt(request.getParameter("courseId"));
        request.setAttribute("courseId", courseId);

        StringBuilder sql = new StringBuilder("SELECT *FROM lessons WHERE chapter_id IN (SELECT chapter_id FROM chapters WHERE course_id = ").append(courseId).append(")");
        sql.append(" and lesson_name  like CONCAT('%','").append(lessonKeyword).append("','%') ");

        if (status != -1) {
            sql.append(" and status = ").append(status);
        }

        sql.append(" order by chapter_id asc");

        int numberOfLesson = daoLesson.findAll(sql.toString()).size();
        request.setAttribute("numberOfLesson", numberOfLesson);
        int numberOfChapter;
        if(numberOfLesson != 0){
        StringBuilder sqlChapter = new StringBuilder("SELECT DISTINCT ch.*\n"
                + "FROM chapters ch\n"
                + "INNER JOIN lessons l ON ch.chapter_id = l.chapter_id\n"
                + "WHERE l.lesson_id IN (");
        for (lessons lesson : daoLesson.findAll(sql.toString())) {
            sqlChapter.append(lesson.getLesson_id()).append(",");
        }

        if (sqlChapter.length() >= 0 && sqlChapter.charAt(sqlChapter.length() - 1) == ',') {
            sqlChapter.deleteCharAt(sqlChapter.length() - 1);
        }
        sqlChapter.append(")");
        numberOfChapter =daoLesson.findAllChapter(sqlChapter.toString()).size();
        List<chapters> listChapter = daoLesson.findAllChapter(sqlChapter.toString());
        request.setAttribute("listChapter", listChapter);  
        System.out.println(sqlChapter.toString());
        }
        else{
        numberOfChapter = 0;
        }
        
        request.setAttribute("numberOfChapter", numberOfChapter);
        String message="";

            request.setAttribute("message", message);                    
            List<lessons> listLesson = daoLesson.findAll(sql.toString());                                   
            request.setAttribute("listLesson", listLesson);

        request.getRequestDispatcher(VIEW_PATH).forward(request, response);    
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
