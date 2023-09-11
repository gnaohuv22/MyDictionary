<%-- 
    Document   : editDictionary
    Created on : Jul 12, 2023, 12:27:45 PM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Dictionary</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon-16x16.png">
        <link rel="stylesheet" href="CSS/editDictionary.css">
        <link rel="stylesheet" href="CSS/header.css">
    </head>
    <body>
        <input type="hidden" name="username" value="${u.getUsername()}">

        <header>
            <%@include file="header.jsp" %>

            <div class="default-profile-img">
                <a onclick="toggleMenu();"> 
                    <img src="default-profile.png" width="60" height="60" alt="default-profile-icon">
                </a> 
            </div>
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
            <div class="edit-box">
                <button class="back-button" onclick="goBack()"><< Back</button> <!--Make a function that close the dialog box after clicked -->
                <form action="dictionary" method="POST">
                    <h2>Edit Dictionary</h2>
                    <input type="hidden" name="id" value="${d.getId()}">
                    <label for="title">Title</label><br>
                    <input type="text" name="title" placeholder="Title" maxlength="200" value="${d.getTitle()}" required><br>

                    <label for="description">Description</label><br>
                    <textarea style="resize: none;" id="description" name="description" placeholder="Description" rows="5" cols="20" maxlength="750">${d.getDescription()}</textarea>

                    <label for="author">Author</label><br>
                    <input type="text" name="author" value="${d.getAuthor()}" readonly required><br>

                    <p style="font-family: sans-serif; color: red">${errorMessage}</p>

                    <input type="submit" name="save" value="Save Changes">
                </form>
            </div>
        </div>

        <script>
            function goBack() {
                window.history.back();
            }

            function toggleMenu() {
                var menu = document.querySelector('.task');
                menu.classList.toggle('show-menu');
            }
        </script>
    </body>
</html>
