/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.CategoryDAO;
import dal.CourseDAO;


import dal.DAOUsers;
import dal.DimensionDAO;
import dal.Impl.CategoryDAOImpl;
import dal.Impl.CourseDAOImpl;
import dal.Impl.DimensionDAOImpl;
import dal.Impl.PriceCourseDAOImpl;
import dal.PriceCourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Course;
import model.cate_dimension;
import model.categories;
import model.dimension;
import model.price_package;
import model.users;

/**
 *
 * @author MH
 */
@WebServlet(name = "ShowInformationCourse", urlPatterns = {"/ShowInformationCourse"})
public class ShowInformationCourse extends HttpServlet {
   
    private final DimensionDAO daoDimen = new DimensionDAOImpl();
    
    private final PriceCourseDAO daoPrice = new PriceCourseDAOImpl();
    
    private final CourseDAO daoCourse = new CourseDAOImpl();   
    
    private final CategoryDAO daoCate = new CategoryDAOImpl();
    
    private static final String VIEW_PATH = "/Dashboard/admin/CourseDetail_Admin.jsp";
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            

            DAOUsers daoUser = new DAOUsers();
            HttpSession session = request.getSession();
            
            String action = request.getParameter("action");
            request.setAttribute("action", action);

            int courseId = Integer.parseInt(request.getParameter("courseId"));
            
            Course course = daoCourse.getCourseByCourseId(courseId);
//            categories cate = daoCate.getCateByCourseId(courseId);
            List<price_package> listPrice = daoPrice.getPricePackageByCourseID(courseId);
           
            
            //dimension
            List<dimension> listDimen = daoDimen.getDimensionByCourseId(courseId);
            List<cate_dimension> listCateDimen = daoCate.getAllCateDimen();

            List<users> listUserByRole = daoUser.getUserByRole(4);
            List<categories> listCate = daoCate.getAllCategory();
            
            request.setAttribute("listUserByRole", listUserByRole);
            request.setAttribute("listDimen", listDimen);
            request.setAttribute("listCateDimen", listCateDimen);
            request.setAttribute("listPrice", listPrice);
            request.setAttribute("listCate", listCate);

            request.setAttribute("course", course);
            request.getRequestDispatcher(VIEW_PATH).forward(request, response);
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String action = request.getParameter("action");
        String pk_id = request.getParameter("pk");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int pkId ;
        if (pk_id.equals("") || pk_id==null) {
            pkId = 0;
        }
        else{
            pkId = Integer.parseInt(pk_id);
        }
        
        String Dimen_id = request.getParameter("dimenId");
        int dimenId;
        if (Dimen_id==null || Dimen_id.equals("")) {
            
            dimenId=0;
        }
        else{
            dimenId = Integer.parseInt(Dimen_id);
        }
        
        List<price_package> listPkOld = daoPrice.getPricePackageByCourseID(1);
        price_package pkOld = daoPrice.getPricePackageByPricePacKageID(pkId);
        dimension dimenOld = daoDimen.getDimensionByDimensionID(dimenId);
               
        String message;

        if (action.equals("editDimesion")) {
            
            String dimenName = request.getParameter("dimensionName");
            int cateDimenId = Integer.parseInt(request.getParameter("cate_dimension_id"));
            String description = request.getParameter("description");
            dimension dimen = new dimension(dimenId, courseId, dimenName, cateDimenId, description);

            daoDimen.updateDimension(dimen);
            message = "Update Successful!";
            request.setAttribute("message", message);
            response.sendRedirect("ShowInformationCourse?courseId="+courseId);
        }
        
        if (action.equals("deleteDimesion")) {
            daoDimen.deleteDimension(dimenId);
            message = "Delete Successful!";
            request.setAttribute("message", message);
            response.sendRedirect("ShowInformationCourse?courseId="+courseId);
        }
    }
    
        

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
