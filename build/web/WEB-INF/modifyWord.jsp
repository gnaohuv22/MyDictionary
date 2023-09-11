<%-- 
    Document   : modifyWord
    Created on : Jul 17, 2023, 11:52:03 AM
    Author     : hoang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Word Modifier</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon-16x16.png">
        <link rel="stylesheet" href="CSS/modifyWord.css">
        <link rel="stylesheet" href="CSS/header.css">
    </head>
    <body>
        <header>
            <%@include file="header.jsp" %>

            <div class="default-profile-img">
                <a href="profile?username=${u.getUsername()}" onclick="toggleMenu();"> 
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
            <div class="modify-box">
                <form action="dictionary" method="POST">
                    <h2>Modify Word</h2>
                    <input type="hidden" name="wordId" value="${w.getId()}">
                    <input type="hidden" name="dictId" value="${dictId}">

                    <div class="word-field">
                        <label for="word">Word</label><br>
                        <input type="text" name="word" placeholder="Word" maxlength="75" value="${w.getWord()}"><br>
                    </div>

                    <div id="meanings" class="meaning">
                        <c:forEach items="${meanings}" var="m"> 
                            <c:if test="${m.getWordId() eq w.getId()}">
                                <div class="meaning">
                                    <label for="meaning-type">Type</label><br>
                                    <select name="meaning-type">
                                        <c:forEach items="${meaningType}" var="mt">
                                            <c:if test="${m.getType() eq mt.getId()}">
                                                <option value="${mt.getId()}" selected>${mt.getType()}</option>
                                            </c:if>
                                            <option value="${mt.getId()}">${mt.getType()}</option>
                                        </c:forEach>
                                    </select><br>

                                    <label for="definition">Definition</label><br>
                                    <textarea style="resize: none" id="definition" name="definition" placeholder="Write your definition with that type of word here..." rows="5" cols="20">${m.getDefine()}</textarea>

                                    <input type="hidden" name="meaningId" value="${m.getId()}">
                                    <input type="checkbox" name="removeMeaning" value="${m.getId()}">
                                    <label>Remove this Meaning</label>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="delete-but">
                        <input type="submit" name="delete" value="Delete Word" onclick="return confirm('Are you sure to delete this word from your dictionary?');">
                    </div>

                    <input type="submit" name="word-change" value="Save Changes">
                </form>
                <a href="#" onclick="addMeaning(); return false;">Add Meaning</a>
            </div>
        </div>

        <script>
            var maxMeanings = 5;
            var currentMeanings = document.querySelectorAll('.meaning').length;

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
            <textarea style="resize: none" id="definition" name="definition" required placeholder="Write your definition with that type of word here..." rows="5" cols="20">${definition}</textarea>
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

            function isCheckboxChecked() {
                var count = 0;
                var checkboxes = document.getElementsByName("removeMeaning");
                for (var i = 0; i < checkboxes.length; i++) {
                    if (checkboxes[i].checked) {
                        ++count;
                    }
                }
                if (currentMeanings === count + 1)
                    return false;
                return true;
            }

            setTimeout(isCheckboxChecked(), 5000);

            document.querySelector('input[name="word-change"]').addEventListener('click', function (event) {
                if (!isCheckboxChecked()) {
                    event.preventDefault();
                    alert('A word must have at least one definition for it.');
                }
            });

            document.querySelector('input[name="word-change"]').addEventListener('click', function (event) {
                var wordFields = document.querySelectorAll('input[name="word"]');
                var definitionFields = document.querySelectorAll('textarea[name="definition"]');
                var allFieldsFilled = true;
                wordFields.forEach(function (field) {
                    if (field.value.trim() === '') {
                        allFieldsFilled = false;
                    }
                });
                definitionFields.forEach(function (field) {
                    if (field.value.trim() === '') {
                        allFieldsFilled = false;
                    }
                });
                if (!allFieldsFilled) {
                    event.preventDefault();
                    alert('Please fill in all the required fields!');
                }
            });
            
            function toggleMenu() {
                var menu = document.querySelector('.task');
                menu.classList.toggle('show-menu');
            }
        </script>
    </body>
</html>
