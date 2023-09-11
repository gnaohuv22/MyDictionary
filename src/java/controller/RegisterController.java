/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author hoang
 */
public class RegisterController extends HttpServlet {
   

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        
        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String displayName = request.getParameter("displayname");
        String email = (request.getParameter("email") == null || request.getParameter("email").isEmpty() ? "" : request.getParameter("email"));
        String dob = request.getParameter("dob");
        String bio = (request.getParameter("bio") == null || request.getParameter("bio").isEmpty() ? "" : request.getParameter("bio"));
        String secretcode = request.getParameter("secretcode");
        User u = new User(username, password, displayName, email, dob, bio, secretcode);
        
        UserDAO ud = new UserDAO();
        if (ud.registerUser(u)) {
            //Set successful message
            request.setAttribute("errorMessage", "Register successfully. You will be directed to the Login page.");
            request.setAttribute("confirmSuccess", "1");
            
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        } else {
            request.removeAttribute("confirmSuccess");
            request.setAttribute("username", username);
            request.setAttribute("displayName", displayName);
            request.setAttribute("email", email);
            request.setAttribute("bio", bio);
            request.setAttribute("errorMessage", "Register not successful. Your username may already existed.");
            
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
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
