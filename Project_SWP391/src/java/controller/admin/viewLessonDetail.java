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
@WebServlet(name = "viewLessonDetail", urlPatterns = {"/viewLessonDetail"})
public class viewLessonDetail extends HttpServlet {

    private static final String VIEW_PATH = "/Dashboard/admin/LessonDetail.jsp";

    private final LessonDAO daoLesson = new LessonDAOImpl();

    private final CourseDAO daoCourse = new CourseDAOImpl();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String message = "";

            String action = request.getParameter("action");
            request.setAttribute("action", action);

            int courseID = Integer.parseInt(request.getParameter("courseId"));
            Course course = daoCourse.getCourseByCourseId(courseID);
            request.setAttribute("course", course);

            String pageIndexS = request.getParameter("page") == null ? "1" : request.getParameter("page");
            int pageIndex = Integer.parseInt(pageIndexS);
            request.setAttribute("pageIndex", pageIndex);
            int pageSize = 3;

            int countChapter = daoLesson.countChapterByCourseID(courseID);
            int countLesson = daoLesson.countLessonByCourseID(courseID);

            int endPage = 0;

            endPage = countChapter / pageSize;
            if (countChapter % pageSize != 0) {
                endPage = endPage + 1;
            }
            if (pageIndex > endPage) {
                pageIndex = endPage;
            }

            request.setAttribute("endPage", endPage);

            int numberOfChapter = daoLesson.countChapterByCourseID(courseID);
            request.setAttribute("numberOfChapter", numberOfChapter);

            int numberOfLesson = daoLesson.countLessonByCourseID(courseID);

            request.setAttribute("numberOfLesson", numberOfLesson);

            String lesson_idS = request.getParameter("lesson_id") == null ? "0" : request.getParameter("lesson_id");
            int lesson_id = Integer.parseInt(lesson_idS);

            lessons lesson = new lessons();

            List<chapters> listChapter = daoLesson.getChapterByCourseIDPaging(courseID, pageIndex, pageSize);

            request.setAttribute("listChapter", listChapter);

            List<lessons> listLesson = daoLesson.getLessonsByCourseID(courseID);
            request.setAttribute("listLesson", listLesson);

            if (listLesson.isEmpty()) {
                lesson = null;
            } else {
                if (lesson_id > 0) {
                    lesson = daoLesson.getLessonByLessonID(lesson_id);
                } else {
                    lesson = daoLesson.getLessonByLessonID(listLesson.get(0).getLesson_id());
                }
            }

            request.setAttribute("lesson", lesson);

            request.getRequestDispatcher(VIEW_PATH).forward(request, response);

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
