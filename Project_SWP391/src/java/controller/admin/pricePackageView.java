/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.CourseDAO;
import dal.Impl.CourseDAOImpl;
import dal.Impl.PriceCourseDAOImpl;
import dal.PriceCourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import model.Course;
import model.price_package;

/**
 *
 * @author MH
 */
@WebServlet(name = "pricePackageView", urlPatterns = {"/pricePackageView"})
public class pricePackageView extends HttpServlet {

    private final CourseDAO daoCourse = new CourseDAOImpl();

    private final PriceCourseDAO daoPrice = new PriceCourseDAOImpl();

    private static final String VIEW_PATH = "/Dashboard/admin/PricePackage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            
            
            String action = request.getParameter("action");
            request.setAttribute("action", action);

            int courseId = Integer.parseInt(request.getParameter("courseId"));
            Course course = daoCourse.getCourseByCourseId(courseId);
            request.setAttribute("course", course);

            List<price_package> listPrice = daoPrice.getPricePackageByCourseID(courseId);
            
            for (price_package object : listPrice) {
                System.out.println(object.toString());
            }

            String priceKeyword = request.getParameter("priceKeyword") == null ? "" : request.getParameter("priceKeyword");

            request.setAttribute("priceKeyword", priceKeyword);

            int duration = Integer.parseInt(request.getParameter("duration") == null ? "-1" : request.getParameter("duration"));
            request.setAttribute("duration", duration);

            int status = Integer.parseInt(request.getParameter("status") == null ? "1" : request.getParameter("status"));
            request.setAttribute("status", status);

            
            if (duration > 0) {

                switch (duration) {
                    case 1:
                        listPrice = listPrice.stream()
                                .filter(price
                                        -> price.getName().trim().toLowerCase().contains(priceKeyword.toLowerCase().trim())
                                && price.getStatus() == status
                                && price.getDuration() >= 1
                                && price.getDuration() <= 3
                                )
                                .collect(Collectors.toList());
                        break;
                    case 2:
                        listPrice = listPrice.stream()
                                .filter(price -> price.getName().trim().toLowerCase().contains(priceKeyword.toLowerCase().trim())
                                && price.getStatus() == status
                                && price.getDuration() >= 3
                                && price.getDuration() <= 6
                                )
                                .collect(Collectors.toList());
                        break;
                    case 3:
                        listPrice = listPrice.stream()
                                .filter(price -> price.getName().toLowerCase().contains(priceKeyword.toLowerCase().trim())
                                && price.getStatus() == status
                                && price.getDuration() >= 6
                                && price.getDuration() <= 12
                                )
                                .collect(Collectors.toList());
                        break;
                    case 0:
                        listPrice = listPrice.stream()
                                .filter(price -> price.getName().trim().toLowerCase().contains(priceKeyword.toLowerCase().trim())
                                && price.getStatus() == status
                                && price.getDuration() == 0
                                )
                                .collect(Collectors.toList());
                        break;
                    default:

                        break;

                }
            } else {
                listPrice = listPrice.stream()
                        .filter(price -> price.getName().trim().toLowerCase().contains(priceKeyword.toLowerCase().trim())
                        && price.getStatus() == status
                        ).collect(Collectors.toList());
            }

            String message = "";
            request.setAttribute("message", message);

            for (price_package object : listPrice) {
                System.out.println(object.toString());
            }

            request.setAttribute("listPrice", listPrice);
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
