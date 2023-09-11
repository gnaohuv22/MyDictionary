<%-- 
    Document   : admin
    Created on : Jul 18, 2023, 6:04:53 PM
    Author     : hoang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div style="display: inline-block">
            <span>Hello ${a.getDisplayName()}</span>
            <form action="admin" method="POST">
                <input type="submit" name="logout" value="Log out">
            </form>

        </div>

        <div>
            <h3>Users</h3>
            <table border="1">
                <tr>
                    <td>username</td>
                    <td>display name</td>
                    <td>email</td>
                    <td>dob</td>
                    <td>action</td>
                </tr>

                <c:forEach items="${users}" var="u">
                    <tr>
                        <td>${u.getUsername()}</td>
                        <td>${u.getDisplayname()}</td>
                        <td>${u.getEmail()}</td>
                        <td>${u.getDob()}</td>
                        <td>
                            <form action="admin" method="POST">
                                <input type="hidden" name="username" value="${u.getUsername()}">
                                <input type="submit" name="delete-user" value="Delete">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div>
            <h3>Dictionaries</h3>
            <table border="1">
                <tr>
                    <td>id</td>
                    <td>title</td>
                    <td style="max-width: 300px; word-wrap: break-word;">description</td>
                    <td>author</td>
                    <td>action</td>
                </tr>

                <c:forEach items="${dictionaries}" var="d">
                    <tr>
                        <td>${d.getId()}</td>
                        <td>${d.getTitle()}</td>
                        <td style="max-width: 300px; word-wrap: break-word;">${d.getDescription()}</td>
                        <td>
                            <a style="text-decoration: none; color: black;" href="profile?username=${d.getAuthor()}">
                                ${d.getAuthor()}
                            </a>
                        </td>
                        <td>
                            <form action="admin" method="POST">
                                <input type="hidden" name="dictId" value="${d.getId()}">
                                <input type="submit" name="delete-dictionary" value="Delete">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
