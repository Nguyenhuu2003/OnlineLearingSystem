/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.CourseDAO;
import dal.Impl.CourseDAOImpl;
import dal.Impl.LessonDAOImpl;
import dal.LessonDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import model.Course;
import model.chapters;
import model.lessons;
import static utils.Constans.PAGINATION_DEFAULT_PAGE_SIZE;



/**
 *
 * @author MH
 */
@MultipartConfig
@WebServlet(name = "addLesson", urlPatterns = {"/addLesson"})
public class addLesson extends HttpServlet {

    private static final String VIEW_PATH = "/Dashboard/admin/LessonList.jsp";
    
    private static final String VIEW_PATH_FORM = "/Dashboard/admin/addLesson.jsp";

    private final LessonDAO daoLesson = new LessonDAOImpl();

    private final CourseDAO daoCourse = new CourseDAOImpl();

    

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        String action = request.getParameter("action");
        request.setAttribute("action", action);
        

        int courseID = Integer.parseInt(request.getParameter("courseId"));
        Course course = daoCourse.getCourseByCourseId(courseID);
        request.setAttribute("course", course);
        
        
        int status = request.getParameter("status")==null?0:1;

        String pageIndexS = request.getParameter("page") == null ? "1" : request.getParameter("page");
        int pageIndex = Integer.parseInt(pageIndexS);
        request.setAttribute("pageIndex", pageIndex);
        

        // add lesson start
        
        int chapter_id = Integer.parseInt(request.getParameter("chapterId"));
        String lesson_name = request.getParameter("lessonName");
        

        Part video = request.getPart("video");
        String realPath = request.getServletContext().getRealPath("/video");
        String fileName = Paths.get(video.getSubmittedFileName()).getFileName().toString();

        if (!Files.exists(Paths.get(realPath))) {
            Files.createDirectory(Paths.get(realPath) );
        }
        
        video.write(realPath+"/"+fileName);
        
        lessons lesson = new lessons(0, lesson_name, chapter_id, fileName,status);
        System.out.println(lesson.toString());
        daoLesson.insertLesson(lesson);
        
        message = "add Lesson successful!";
            
        //add end
        int countChapter = daoLesson.countChapterByCourseID(courseID);
        int countLesson = daoLesson.countLessonByCourseID(courseID);

        int endPage = 0;

        endPage = countChapter / PAGINATION_DEFAULT_PAGE_SIZE;
        if (countChapter % PAGINATION_DEFAULT_PAGE_SIZE != 0) {
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

        request.setAttribute("courseId", courseID);

        List<chapters> listChapter = daoLesson.getChapterByCourseIDPaging(courseID, pageIndex, PAGINATION_DEFAULT_PAGE_SIZE);
        request.setAttribute("listChapter", listChapter);

        request.setAttribute("chapterId", chapter_id);
        

        List<lessons> listLesson = daoLesson.getLessonsByCourseID(courseID);

        
        request.setAttribute("listLesson", listLesson);
        request.setAttribute("message", message);

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
