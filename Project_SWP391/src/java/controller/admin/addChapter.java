/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import com.sun.mail.handlers.message_rfc822;
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
@WebServlet(name="addChapter", urlPatterns={"/addChapter"})
public class addChapter extends HttpServlet {
   
    private static final String VIEW_PATH = "/Dashboard/admin/LessonList.jsp";
    
    private final LessonDAO daoLesson = new LessonDAOImpl();
    
    private final CourseDAO daoCourse = new CourseDAOImpl();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            int courseID = Integer.parseInt(request.getParameter("courseId"));
            Course course = daoCourse.getCourseByCourseId(courseID);
            request.setAttribute("course", course);
            
            String action = request.getParameter("action");
            request.setAttribute("action", action);
            
            String message = "";
            String pageIndexS = request.getParameter("page")==null?"1":request.getParameter("page");
            int pageIndex = Integer.parseInt(pageIndexS);
            
            // add start
            request.setAttribute("courseId", courseID);
            
            String chapter_name = request.getParameter("chapter_name");
            chapters chapter = new chapters(courseID, chapter_name, courseID);
            
            daoLesson.insertChapter(chapter);
            message = "add Chapter successful!";
            request.setAttribute("message", message);
            
            //add end
            
            request.setAttribute("pageIndex", pageIndex);
           
            
            
            
            int countChapter = daoLesson.countChapterByCourseID(courseID);
            
            System.out.println("c"+countChapter);
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
            
            
            
            List<chapters> listChapter = daoLesson.getChapterByCourseIDPaging(courseID, pageIndex, PAGINATION_DEFAULT_PAGE_SIZE);
            request.setAttribute("listChapter", listChapter);
            List<lessons> listLesson = daoLesson.getLessonsByCourseID(courseID);
            request.setAttribute("listLesson", listLesson);
            
            
            
            
            request.getRequestDispatcher(VIEW_PATH).forward(request, response);
        
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
