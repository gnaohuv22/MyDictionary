/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.DictionaryDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Dictionary;
import model.User;

/**
 *
 * @author hoang
 */
public class ProfileController extends HttpServlet {
    
    
   
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
        if (userInfo != null) {
            request.setAttribute("userInfo", userInfo);
        }
        if (request.getParameter("username") != null) {
            String username = request.getParameter("username");
            
            UserDAO ud = new UserDAO();
            User u = ud.getUserByUsername(username);
            
            DictionaryDAO dd = new DictionaryDAO();
            ArrayList<Dictionary> dictionaryList = dd.getListDictionary();
            
            if (u != null) {
                request.setAttribute("u", u);
                request.setAttribute("dictionaryList", dictionaryList);
                request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("notfound.jsp").forward(request, response);
            }
        }
    } 


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }
}
