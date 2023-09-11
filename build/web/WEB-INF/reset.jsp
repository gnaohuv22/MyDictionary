<%-- 
    Document   : reset
    Created on : Jul 7, 2023, 8:10:15 PM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset</title>
        <link rel="stylesheet" href="CSS/header.css">
        <link rel="stylesheet" href="CSS/reset.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon-16x16.png">
    </head>
    
    <body>
        <header>
            <%@include file="header.jsp" %>
        </header>
        
        <div class="container">
            <div class="reset-box">
                <form action="reset" method="POST">
                    <h2>Reset Password</h2>
                    <label for="username">Username</label><br>
                    <input type="text" name="username" value="${username}" readonly><br>

                    <label for="password">Password</label><br>
                    <input type="password" name="password" placeholder="Password" maxlength="32" required><br>

                    <label for="repassword">Re-enter Password</label><br>
                    <input type="password" name="repassword" placeholder="Re-enter Password" maxlength="32" required><br>

                    <p style="font-family: sans-serif; color: red">${errorMessage}</p>

                    <input type="submit" name="reset" value="Reset" onclick="validatePassword()">
                </form>
            </div>
        </div>
    </body>

    <script>
        function validatePassword() {
            const password = document.querySelector('input[name = "password"]');
            const repassword = document.querySelector('input[name = "repassword"]');

            if (repassword.value !== password.value) {
                event.preventDefault();
                repassword.setCustomValidity('Passwords do not match!');
                repassword.reportValidity();
            } else {
                repassword.setCustomValidity('');
            }

            if (password.value.trim().length < 8) {
                event.preventDefault();
                password.setCustomValidity('Password must be at least 8 characters long.');
                password.reportValidity();
            } else {
                password.setCustomValidity('');
            }
        }
    </script>
</html>
