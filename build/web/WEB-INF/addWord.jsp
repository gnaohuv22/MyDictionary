<%-- 
    Document   : addWord
    Created on : Jul 14, 2023, 8:34:52 AM
    Author     : hoang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Word into Dictionary</title>
        <link rel="stylesheet" href="CSS/header.css">
        <link rel="stylesheet" href="CSS/addWord.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon-16x16.png">
    </head>
    <body>
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
            <div class="add-word-box">
                <form action="dictionary" method="POST">
                    <div class="basic-inf">
                        <label for="dictionary">Dictionary</label><br>
                        <select name="dictId">
                            <c:forEach items="${dictionaryList}" var="d1">
                                <c:if test="${u.getUsername() eq d1.getAuthor()}">
                                    <c:choose>
                                        <c:when test="${d1.getId() eq d.getId()}">
                                            <option value="${d1.getId()}" selected>${d1.getTitle()}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${d1.getId()}">${d1.getTitle()}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:forEach>
                        </select><br>

                        <label for="word">Word</label><br>
                        <input type="text" name="word" placeholder="Word" maxlength="75" value="${word}" required>
                    </div>

                    <div id="meanings">
                        <div class="meaning">
                            <label for="meaning-type">Type</label><br>
                            <select name="meaning-type">
                                <c:forEach items="${meaningType}" var="mt">
                                    <option value="${mt.getId()}">${mt.getType()}</option>
                                </c:forEach>
                            </select><br>

                            <label for="definition">Definition</label><br>
                            <textarea style="resize: none" id="definition" name="definition" required placeholder="Write your definition with that type of word here..." rows="5" cols="20" maxlength="750">${definition}</textarea>
                        </div>
                    </div>

                    <a href="#" onclick="addMeaning(); return false;">Add Meaning</a>

                    <p>${msg}</p>

                    <input type="submit" name="add-word" value="Add New Word">
                </form>
            </div>
        </div>

        <script>
            var maxMeanings = 5;
            var currentMeanings = 1;

            function addMeaning() {
                if (currentMeanings <= maxMeanings) {
                    var meanings = document.getElementById('meanings');
                    var meaning = document.createElement('div');
                    meaning.className = 'meaning';
                    meaning.innerHTML = `
            <div class="meaning-header">
                <label for="meaning-type">Type</label>
                <button class="delete-btn" onclick="deleteMeaning(this)">Delete</button><br>
            </div>
            <select name="meaning-type">
            <c:forEach items="${meaningType}" var="mt">
                    <option value="${mt.getId()}">${mt.getType()}</option>
            </c:forEach>
            </select><br>

            <label for="definition">Definition</label><br>
            <textarea style="resize: none" id="definition" name="definition" required placeholder="Write your definition with that type of word here..." rows="5" cols="20" maxlength="750">${definition}</textarea>
            `;
                    meanings.appendChild(meaning);
                    ++currentMeanings;
                } else {
                    alert('You have reached the maximum meanings for this word!');
                }
            }

            function deleteMeaning(btn) {
                var meaning = btn.closest('.meaning');
                meaning.remove();
                --currentMeanings;
            }

            function toggleMenu() {
                var menu = document.querySelector('.task');
                menu.classList.toggle('show-menu');
            }
        </script>
    </body>
</html>
