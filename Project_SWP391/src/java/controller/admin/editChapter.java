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
import static utils.Constans.PAGINATION_DEFAULT_PAGE_SIZE;

/**
 *
 * @author MH
 */
@WebServlet(name="editChapter", urlPatterns={"/editChapter"})
public class editChapter extends HttpServlet {
    
    private static final String VIEW_PATH = "/Dashboard/admin/LessonList.jsp";
    
    private final LessonDAO daoLesson = new LessonDAOImpl();
    
    private final CourseDAO daoCourse = new CourseDAOImpl();
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
            String action = request.getParameter("action");
        request.setAttribute("action", action);
        
            int courseID = Integer.parseInt(request.getParameter("courseId"));
            Course course = daoCourse.getCourseByCourseId(courseID);
            request.setAttribute("course", course);
            
            String message = "";
            String pageIndexS = request.getParameter("page")==null?"1":request.getParameter("page");
            int pageIndex = Integer.parseInt(pageIndexS);
            
            //edit start
            String chapter_name = request.getParameter("chapter_name");
            String chapter_idS = request.getParameter("chapter_id");
            
            
            int chapter_id = Integer.parseInt(chapter_idS);
            
            chapters chapter = new chapters(chapter_id, chapter_name, courseID);
            
            daoLesson.updateChapter(chapter);
            message = "update Chapter successful!";
            System.out.println(message);
            request.setAttribute("message", message);
            //edit end
            
            request.setAttribute("pageIndex", pageIndex);

            
            int countChapter = daoLesson.countChapterByCourseID(courseID);
            int countLesson = daoLesson.countLessonByCourseID(courseID);
            
            int endPage = 0;
        
            endPage= countChapter/PAGINATION_DEFAULT_PAGE_SIZE;
            if (countChapter % PAGINATION_DEFAULT_PAGE_SIZE != 0) {
                endPage = endPage + 1;
            }
            if (pageIndex > endPage) {
                pageIndex = endPage;
            }
        
            request.setAttribute("endPage", endPage);
            
            
            request.setAttribute("numberOfChapter", countChapter);
            
            
            request.setAttribute("numberOfLesson", countLesson);
            
            request.setAttribute("courseId", courseID);
            
            
            
            List<chapters> listChapter = daoLesson.getChapterByCourseIDPaging(courseID, pageIndex, PAGINATION_DEFAULT_PAGE_SIZE);
            request.setAttribute("listChapter", listChapter);
            List<lessons> listLesson = daoLesson.getLessonsByCourseID(courseID);
            request.setAttribute("listLesson", listLesson);
            
            
            
            
            request.getRequestDispatcher(VIEW_PATH).forward(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
