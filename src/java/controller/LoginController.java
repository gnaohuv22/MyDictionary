/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AdminDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author hoang
 */
public class LoginController extends HttpServlet {

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
        Cookie[] cookies = request.getCookies();
        boolean authenticated = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("authenticated") &&
                    cookie.getValue().equals("true")) {
                    authenticated = true;
                    break;
                }
            }
        }
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("adminGranted");

        if (authenticated) {
            response.sendRedirect("home");
        } else if (username != null) {
            response.sendRedirect("admin");
        } else {
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
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
        HttpSession session = request.getSession();
        //Login task
        if (request.getParameter("login") != null) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                request.setAttribute("errorMessage", "Username and password must not empty!");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            } else {
                 UserDAO ud = new UserDAO();
                 AdminDAO ad = new AdminDAO();
            
                if (ud.checkLogin(username, password)) {
                    //Direct to homepage at /home
                    Cookie authCookie = new Cookie("authenticated", "true");
                    authCookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(authCookie);
                    
                    User u = ud.getUserByUsername(username);
                    session.setAttribute("u", u);
                    
                    String userInfo = u.getUsername();
                    Cookie userCookie = new Cookie("user", userInfo);
                    userCookie.setMaxAge(60 * 60 *24);
                    response.addCookie(userCookie);
                    
                    response.sendRedirect("home");
                } else if (ad.checkLogin(username, password)) {
                    session.setAttribute("adminGranted", username);
                    response.sendRedirect("admin");
                } else {
                    //Send a message that maybe username or password is not correct.
                    request.setAttribute("username", username);
                    request.setAttribute("errorMessage", "Username or password is incorrect.");
                    request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
            }
        //Forget password task -> Direct to /reset
        } else if (request.getParameter("forget") != null) {
            response.sendRedirect("forget");
        //Direct to /register
        } else if (request.getParameter("register") != null) {
            response.sendRedirect("register");
        }
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
