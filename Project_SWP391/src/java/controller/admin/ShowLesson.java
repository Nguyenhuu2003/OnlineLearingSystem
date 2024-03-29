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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.Course;
import model.chapters;
import model.lessons;
import static utils.Constans.PAGINATION_DEFAULT_PAGE_SIZE;


@WebServlet(name="ShowLesson", urlPatterns={"/ShowLesson"})
public class ShowLesson extends HttpServlet {
    private static final String VIEW_PATH = "/Dashboard/admin/LessonList.jsp";
    
    private final LessonDAO daoLesson = new LessonDAOImpl();
    
    private final CourseDAO daoCourse = new CourseDAOImpl();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String message = "";
            
            String action = request.getParameter("action");
            request.setAttribute("action", action);
            
            int courseID = Integer.parseInt(request.getParameter("courseId"));
            Course course = daoCourse.getCourseByCourseId(courseID);
            request.setAttribute("course", course);
            request.setAttribute("courseId", courseID);
            
            String pageIndexS = request.getParameter("page")==null?"1":request.getParameter("page");
            int pageIndex = Integer.parseInt(pageIndexS);
            request.setAttribute("pageIndex", pageIndex);
            
            
        

        
        int numberOfChapter = daoLesson.countChapterByCourseID(courseID);
        request.setAttribute("numberOfChapter", numberOfChapter);

        int numberOfLesson = daoLesson.countLessonByCourseID(courseID);
        request.setAttribute("numberOfLesson", numberOfLesson);

        int endPage = 0;

        endPage = numberOfChapter / PAGINATION_DEFAULT_PAGE_SIZE;
        if (numberOfChapter % PAGINATION_DEFAULT_PAGE_SIZE != 0) {
            endPage = endPage + 1;
        }
        if (pageIndex > endPage) {
            pageIndex = endPage;
        }

        request.setAttribute("endPage", endPage);

        List<chapters> listChapter = daoLesson.getChapterByCourseIDPaging(courseID, pageIndex, PAGINATION_DEFAULT_PAGE_SIZE);
        request.setAttribute("listChapter", listChapter);

        List<lessons> listLesson = daoLesson.getLessonsByCourseID(courseID);
        List<lessons> listLesson1 = new ArrayList<>();
//        listLesson.stream().filter(lesson -> lesson.getChapter_id()==1).forEach(lesson -> System.out.println(lesson.toString()));
         
        listLesson1= listLesson.stream().filter(lesson -> lesson.getChapter_id()==3).collect(Collectors.toList());
         
            
                
        
            

        request.setAttribute("listLesson", listLesson);
        request.setAttribute("message", message);
        
        

        request.getRequestDispatcher(VIEW_PATH).forward(request, response);
            
        }
    } 

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
