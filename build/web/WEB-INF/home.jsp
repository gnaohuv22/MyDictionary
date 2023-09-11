<%-- 
    Document   : home
    Created on : Jul 8, 2023, 12:51:16 AM
    Author     : hoang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Dictionary</title>
        <link rel="stylesheet" href="CSS/header.css">
        <link rel="stylesheet" href="CSS/home.css">
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

        <div class="add-button">
            <button onclick="openDialog();">
                Add Dictionary
            </button>
        </div>

        <div id="dialog" style="display: none">
            <iframe src="dictionary" frameBorder="0" class="dialog-iframe"></iframe>
        </div>

        <div id="overlay"></div> 


        <div class="dict-unit">
            <c:forEach items="${dictionaryList}" var="d">
                <c:if test="${d.getAuthor() eq u.getUsername()}">

                    <a onclick="displayDictionary(${d.getId()}, event)" href="#">
                        <div>
                            <img src="default-dictionary.png" width="30" height="30"/>
                            <span>${d.getTitle()}</span><br>
                        </div>

                        <p>Author: ${d.getAuthor()}</p>
                    </a>
                </c:if>
            </c:forEach>
        </div>


        <div id="dictionary-view">
            <div class="default-page">
                <h2>Welcome ${u.getDisplayname()}!</h2>
                <p>Choose a dictionary on the left to view its contents for create a new one.</p>
            </div>
        </div>


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

        <script>
            function openDialog() {
                //Remove the shrink effect and then add the blowup effect to the dialog
                document.querySelector('#dialog').classList.remove('shrink');
                document.querySelector('#dialog').classList.add('blowup');
                document.getElementById('dialog').style.display = 'block';
                document.getElementById('overlay').style.display = 'block';
            }

            window.addEventListener('message', function (event) {
                if (event.data === 'reload') {
                    setTimeout(function () {
                        window.location.reload();
                    }, 500);
                }
            });

            document.getElementById('overlay').addEventListener('click', closeDialog);

            function closeDialog() {
                //Remove the blowup effect and then add the shrink effect to the dialog
                document.querySelector('#dialog').classList.remove('blowup');
                document.querySelector('#dialog').classList.add('shrink');
                setTimeout(function () {
                    document.getElementById('dialog').style.display = 'none';
                    document.getElementById('overlay').style.display = 'none';
                }, 500);
            }

            function toggleMenu() {
                var menu = document.querySelector('.task');
                menu.classList.toggle('show-menu');
            }

            window.addEventListener('load', function () {
                var urlParams = new URLSearchParams(window.location.search);
                var dictId = urlParams.get('dictId');
                if (dictId) {
                    displayDictionary(dictId);
                }
            });

            function displayDictionary(id, event) {
                //get the position of the clicked element
                if (event) {
                    var rect = event.currentTarget.getBoundingClientRect();
                    var x = rect.left + (rect.width / 2) - 150;
                    var y = rect.top + (rect.height / 2) + window.scrollY - 220;

                    // Update the transform-origin property of the dictionary-view element
                    document.querySelector('#dictionary-view').style.transformOrigin = x + 'px ' + y + 'px';
                }

                //remove the blowup class from the dictionary-view div
                document.querySelector('#dictionary-view').classList.remove('blowup');

                //add the shrink effect to the dictionary-view div
                document.querySelector('#dictionary-view').classList.add('shrink');

                //use an XMLHttpRequest to fetch() to send a GET request to the Servlet
                //with the id parameter to retrieve the dictionary data

                setTimeout(function () {
                    var xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (this.readyState === 4 && this.status === 200) {
                            //update the content of the dictionary-view div with the response
                            document.querySelector('#dictionary-view').innerHTML = this.responseText;

                            //remove the shrink effect before add blowup effect to the dictionary-view div
                            document.querySelector('#dictionary-view').classList.remove('shrink');

                            //then add a blowup animation to the dictionary-view div
                            document.querySelector('#dictionary-view').classList.add('blowup');
                        }
                    };

                    xhr.open("GET", "dictionary?id=" + id, true);
                    xhr.send();
                }, 500); //Add an half-second delay to the shrink animation effect
            }
        </script>
    </body>
</html>
