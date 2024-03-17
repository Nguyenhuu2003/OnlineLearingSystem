/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.CategoryDAO;
import dal.CourseDAO;
import dal.DAOCategory;
import dal.Impl.CategoryDAOImpl;
import dal.Impl.CourseDAOImpl;

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
import model.Course;

@MultipartConfig
@WebServlet(name="UpdateOverviewCourse", urlPatterns={"/UpdateOverviewCourse"})
public class UpdateOverviewCourse extends HttpServlet {
   private final CourseDAO daoCourse = new CourseDAOImpl();   
    
    private final CategoryDAO daoCate = new CategoryDAOImpl();
              
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        

        int courseId = Integer.parseInt(request.getParameter("courseId"));
        
        String action = request.getParameter("action");
        request.setAttribute("action", action);
        
        
        int expertId = Integer.parseInt(request.getParameter("expertId"));
        int cateID = Integer.parseInt(request.getParameter("cateID"));
        
        String courseName = request.getParameter("courseName");
        String tagLine = request.getParameter("courseTagLine");
        int feature = Integer.parseInt(request.getParameter("feature"));
        int status = Integer.parseInt(request.getParameter("publish"));
        
//        System.out.println("ft"+feature);
//        System.out.println("status"+status);
        
        String description = request.getParameter("description");
        
        
        Part image =  request.getPart("image");
       
        
        String realPath = request.getServletContext().getRealPath("/img/course");
        

        String fileName;
        if (image.getSubmittedFileName()==null||image.getSubmittedFileName().equals("")) {
            fileName = daoCourse.getCourseByCourseId(courseId).getImage();
        }else{
            fileName = Paths.get(image.getSubmittedFileName()).getFileName().toString();
        }
       
        
        
        if (!Files.exists(Paths.get(realPath))) {
            Files.createDirectory(Paths.get(realPath));
        }

//        System.out.println(realPath+"\\"+fileName);
        image.write(realPath + "\\" + fileName);
        
        
        Course course = new Course(courseId, expertId, cateID, courseName, fileName, tagLine, status, description,daoCourse.getCourseByCourseId(courseId).getCreated_at() , feature);
        
//        System.out.println(course);
        daoCourse.updateCourse(course);
        
        
        response.sendRedirect("ShowInformationCourse?courseId="+courseId);
        
        
        
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
