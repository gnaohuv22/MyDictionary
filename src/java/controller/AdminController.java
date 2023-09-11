/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AdminDAO;
import dal.DictionaryDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author hoang
 */
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("adminGranted");
        if (username != null) {
            DictionaryDAO dd = new DictionaryDAO();
            UserDAO ud = new UserDAO();
            AdminDAO ad = new AdminDAO();

            request.setAttribute("a", ad.getById(username));
            request.setAttribute("users", ud.getListUser());
            request.setAttribute("dictionaries", dd.getListDictionary());
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getParameter("delete-user") != null) {
            String username = request.getParameter("username");
            AdminDAO ad = new AdminDAO();
            if (ad.deleteUser(username)) {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Delete successful.');");
                    out.println("location='admin'");
                    out.println("</script>");
                }
            } else {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Delete unsuccessful.');");
                    out.println("location='admin'");
                    out.println("</script>");
                }
            }
        } else if (request.getParameter("delete-dictionary") != null) {
            String dictId = request.getParameter("dictId");
            AdminDAO ad = new AdminDAO();
            if (ad.deleteDictionary(dictId)) {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Delete successful.');");
                    out.println("location='admin'");
                    out.println("</script>");
                }
            } else {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Delete unsuccessful.');");
                    out.println("location='admin'");
                    out.println("</script>");
                }
            }
        } else if (request.getParameter("logout") != null) {
            HttpSession session = request.getSession();
            session.removeAttribute("adminGranted");

            response.sendRedirect("login");
        } else {
            request.getRequestDispatcher("forbidden.jsp").forward(request, response);
        }
    }
}
