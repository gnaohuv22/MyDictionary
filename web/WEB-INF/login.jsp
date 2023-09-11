<%-- 
    Document   : login
    Created on : Jul 7, 2023, 12:14:46 AM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/header.css">
        <link rel="stylesheet" href="CSS/login.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon-16x16.png">
        <title>Login</title>
    </head>
    
    <body>
        <header>
            <%@include file="header.jsp" %>
        </header>
        
        <div class="container"> 
            <div class="login-box">
                <form action="login" method="POST">
                    <h2>WELCOME</h2>
                    <label for="username">Username</label><br>
                    <input type="text" name="username" placeholder="Username" maxlength="32" value="${username}"><br>

                    <label for="password">Password</label><br>
                    <input type="password" name="password" placeholder="Password" maxlength="32"><br>

                    <p style="font-family: sans-serif; color: red">${errorMessage}</p>

                    <input type="submit" name="login" value="Login">
                    <div class="button-row">
                        <input type="submit" name="forget" value="Forget Password?">
                        <input type="submit" name="register" value="Register">
                    </div>
                </form>
            </div>
        </div>
    </body>
    <footer style="position: fixed">
        <%@include file="footer.jsp" %>
    </footer>
</html>
