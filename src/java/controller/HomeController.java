/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DictionaryDAO;
import dal.MeaningDAO;
import dal.MeaningTypeDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Dictionary;
import model.Meaning;
import model.MeaningType;
import model.User;

/**
 *
 * @author hoang
 */
public class HomeController extends HttpServlet {

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
        if (request.getParameter("logout") != null) {
            Cookie authCookie = new Cookie("authenticated", "false");
            Cookie userCookie = new Cookie("user", null);
            userCookie.setMaxAge(0);
            authCookie.setMaxAge(0);
            response.addCookie(authCookie);
            response.addCookie(userCookie);

            // Invalidate the session
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();

                response.sendRedirect("login");
            }

        } else if (request.getParameter("id") != null) {
            //Get the dictionary information and get all the words with its meaning
        } else {
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
            DictionaryDAO dd = new DictionaryDAO();
            ArrayList<Dictionary> dictionaryList = dd.getListDictionary();
            
            MeaningDAO md = new MeaningDAO();
            ArrayList<Meaning> meaningList = md.getListMeaning();
            
            MeaningTypeDAO mtd = new MeaningTypeDAO();
            ArrayList<MeaningType> meaningType = mtd.getListMeaningType();

            if (u != null) {
                request.setAttribute("u", u);
                request.setAttribute("dictionaryList", dictionaryList);
                request.setAttribute("meaningList", meaningList);
                request.setAttribute("meaningType", meaningType);
                request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("forbidden.jsp").forward(request, response);
            }
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
        if (request.getParameter("add") != null) {
            request.getRequestDispatcher("/WEB-INF/addDictionary.jsp").forward(request, response);
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
