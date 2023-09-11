<%-- 
    Document   : addDictionary
    Created on : Jul 10, 2023, 2:01:52 PM
    Author     : hoang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon-16x16.png">
        <link rel="stylesheet" href="CSS/addDictionary.css">
    </head>

    <body>
        <input type="hidden" name="username" value="${u.getUsername()}">

        <div class="container">
            <div class="add-box">
                <button class="close-button" onclick="parent.closeDialog();">Close</button>
                <form id="add-dict-form" action="dictionary" method="POST">
                    <h2>Add New Dictionary</h2>
                    <label for="title">Title<span class="required">*</span></label><br>
                    <input type="text" name="title" placeholder="Eg: Vietnamese - English Dictionary" maxlength="200" required><br>

                    <label for="description">Description</label><br>
                    <textarea style="resize: none;" id="description" name="description" placeholder="Description" rows="5" cols="20" maxlength="750"></textarea>

                    <label for="author">Author</label><br>
                    <input type="text" name="author" value="${u.getUsername()}" readonly required><br>

                    <p style="font-family: sans-serif; color: red">${errorMessage}</p>

                    <c:if test="${not empty confirmSuccess}">
                        <script>
                            //Set a delay for 3 seconds
                            alert('Add new dictionary successfully.');
                        </script>
                    </c:if>

                    <button class="submit" name="add" onclick="submitForm(event);">Add New Dictionary</button>
                </form>
            </div>
        </div>
        <script>
            function submitForm(event) {
                event.preventDefault();
                //prevent default from submission behavior
                var form = document.getElementById('add-dict-form');
                var formData = new FormData(form);
                formData.append('add', '1');

                fetch('dictionary', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: new URLSearchParams(formData)
                }).then(function (response) {
                    if (response.ok) {
                        parent.closeDialog();
                        window.parent.postMessage('reload', '*');
                    } else {
                    }
                });
            }
        </script>
    </body>
</html>
