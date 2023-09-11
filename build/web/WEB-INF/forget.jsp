<%-- 
    Document   : forget
    Created on : Jul 7, 2023, 7:56:17 PM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
        <link rel="stylesheet" href="CSS/header.css">
        <link rel="stylesheet" href="CSS/forget.css">
    </head>
    
    <body>
        <header>
            <%@include file="header.jsp" %>
        </header>
        
        <div class="container">
            <div class="forget-box">
                <form action="forget" method="POST">
                    <h2>Forget Password</h2>
                    <label for="username">Username</label><br>
                    <input type="text" name="username" placeholder="Username" value="${username}" maxlength="32" required><br>

                    <label for="secretcode">Secret Code</label><br>
                    <input type="password" name="secretcode" placeholder="Secret Code" maxlength="6" required><br>

                    <p style="font-family: sans-serif; color: red">${errorMessage}</p>

                    <input type="submit" name="forget" value="Reset Password">
                </form>
            </div>
        </div>
    </body>
    <footer style="position: fixed">
        <%@include file="footer.jsp" %>
    </footer>
</html>
