/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author hoang
 */
public class AccountSettingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Cookie[] cookies = request.getCookies();
        String userInfo = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    userInfo = cookie.getValue();
                    break;
                }
            }
        }
        User u = null;

        if (userInfo != null) {
            UserDAO ud = new UserDAO();
            u = ud.getUserByUsername(userInfo);
        }
        
        if (u != null) {
            request.setAttribute("u", u);
            request.getRequestDispatcher("/WEB-INF/accountSetting.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("forbidden.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String displayname = request.getParameter("displayname");
        String email = request.getParameter("email");
        String dob = request.getParameter("dob");
        String bio = request.getParameter("bio");
        
        User u = new User(username, "", displayname, email, dob, bio, "");
        UserDAO ud = new UserDAO();
        ud.updateInfo(u);
        
        request.setAttribute("msg", "Your information has been updated.");
        doGet(request, response);
    }
}
