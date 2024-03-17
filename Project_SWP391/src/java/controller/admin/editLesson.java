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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Files;
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
@WebServlet(name = "editLesson", urlPatterns = {"/editLesson"})
public class editLesson extends HttpServlet {

    private static final String VIEW_PATH = "/Dashboard/admin/LessonList.jsp";
    
    private static final String VIEW_PATH_EDIT = "/Dashboard/admin/editLesson.jsp";

    private final LessonDAO daoLesson = new LessonDAOImpl();

    private final CourseDAO daoCourse = new CourseDAOImpl();

    

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        String action = request.getParameter("action");
        request.setAttribute("action", action);
        
        int chapterId = Integer.parseInt(request.getParameter("chapterId"));
        request.setAttribute("chapterId", chapterId);
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        request.setAttribute("courseId", courseId);
        int lessonId = Integer.parseInt(request.getParameter("lessonId"));
        request.setAttribute("lessonId", lessonId);
        
        lessons lesson = daoLesson.getLessonByLessonID(lessonId);
        request.setAttribute("lesson", lesson);
        
        request.getRequestDispatcher(VIEW_PATH_EDIT).forward(request, response);
        
        
        
    } 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

            String message = "";
            
            int courseID = Integer.parseInt(request.getParameter("courseId"));
            Course course = daoCourse.getCourseByCourseId(courseID);
            request.setAttribute("course", course);
            request.setAttribute("courseId", courseID);
            
            String pageIndexS = request.getParameter("page")==null?"1":request.getParameter("page");
            int pageIndex = Integer.parseInt(pageIndexS);
            request.setAttribute("pageIndex", pageIndex);
            
           
        
       
            
        // add lesson start
        int status = request.getParameter("status")==null?0:1;
        int chapter_id = Integer.parseInt(request.getParameter("chapterId"));
        String lesson_name = request.getParameter("lessonName");
        int lesson_id = Integer.parseInt(request.getParameter("lessonId"));

        lessons lesson = daoLesson.getLessonByLessonID(lesson_id);
        
        
        
        Part video = request.getPart("video");
        String realPath = request.getServletContext().getRealPath("/video");
       
        
        String fileName;
        if (video.getSubmittedFileName()==null ||video.getSubmittedFileName().equals("")) {
            fileName = lesson.getContent();
        }else{
            fileName = Paths.get(video.getSubmittedFileName()).getFileName().toString();
        }
       
        
        
        if (!Files.exists(Paths.get(realPath))) {
            Files.createDirectory(Paths.get(realPath));
        }

        video.write(realPath + "/" + fileName);
        lesson = new lessons(lesson_id, lesson_name, chapter_id, fileName,status);
        daoLesson.updateLesson(lesson);

        message = "edit Lesson successful!";

        //add end
        
        
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
