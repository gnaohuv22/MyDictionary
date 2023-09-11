<%-- 
    Document   : profile
    Created on : Jul 17, 2023, 5:21:46 PM
    Author     : hoang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${u.getDisplayname()} | My Dictionary</title>
        <link rel="stylesheet" href="CSS/header.css">
        <link rel="stylesheet" href="CSS/profile.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon-16x16.png">
    </head>
    <body>
        <header>
            <%@include file="header.jsp" %>
            <c:if test="${not empty userInfo}">
                <div class="default-profile-img">
                    <a onclick="toggleMenu();"> 
                        <img src="default-profile.png" width="60" height="60" alt="default-profile-icon">
                    </a> 
                </div>
            </c:if>
        </header>

        <ul class="task">
            <a href="profile?username=${u.getUsername()}">
                <li>View Profile</li>
            </a>

            <a href="accountsettings">
                <li>Account Settings</li>
            </a>

            <a href="home?logout=1">
                <li>Log Out</li>
            </a>
        </ul>


        <div class="container">
            <div class="view-box">
                <div class="summary-inf">
                    <img class="img" src="default-profile.png" width="400" height="400" alt="default-profile-icon">
                    <span class="displayname">${u.getDisplayname()}</span><br>
                    <span class="username">@${u.getUsername()}</span>
                </div>

                <div class="information">
                    <h1>${u.getDisplayname()} Profile Card</h1>
                    <label for="email">Email</label><br>
                    <input type="text" value="${u.getEmail()}" readonly><br>

                    <label for="dob">Date of Birth</label><br>
                    <input type="text" value="${u.getDob()}" readonly><br>

                    <label for="username">Bio</label><br>
                    <textarea style="resize: none;" rows="5" cols="10" readonly>${u.getBio()}</textarea><br>

                    <div class="dictionary-collection">
                        <h3>${u.getDisplayname()}'s Dictionary Collection</h3>
                        <c:forEach items="${dictionaryList}" var="d">
                            <c:if test="${d.getAuthor() eq u.getUsername()}">
                                <a href="home?dictId=${d.getId()}">
                                    <div class="dictionary-ele">
                                        <img src="default-dictionary.png" width="30" height="30" alt="default dictionary icon"/>
                                        <span class="dict-name">${d.getTitle()}</span><br>
                                    </div>
                                </a>
                            </c:if>
                        </c:forEach>
                    </div>

                </div>
            </div>
        </div>
        <script>
            function toggleMenu() {
                var menu = document.querySelector('.task');
                menu.classList.toggle('show-menu');
            }
        </script>
    </body>
</html>
