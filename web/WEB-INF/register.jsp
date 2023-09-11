<%-- 
    Document   : register
    Created on : Jul 7, 2023, 10:07:29 PM
    Author     : hoang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="CSS/header.css">
        <link rel="stylesheet" href="CSS/register.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon-16x16.png">
    </head>
    
    <body>
        <header>
            <%@include file="header.jsp" %>
        </header>
        
        <div class="container">
            <div class="register-box">
                <form action="register" method="POST">
                    <label for="username">Username<span class="required">*</span></label><br>
                    <input type="text" id="username" name="username" value="${username}" placeholder="Username" maxlength="32" required><br>

                    <label for="password">Password<span class="required">*</span></label><br>
                    <input type="password" id="password" name="password" placeholder="Password" maxlength="32" required><br>

                    <label for="repassword">Re-enter Password<span class="required">*</span></label><br>
                    <input type="password" id="repassword" name="repassword" placeholder="Re-enter Password" maxlength="32" required><br>

                    <label for="displayName">Display Name<span class="required">*</span></label><br>
                    <input type="text" id="displayname" name="displayname" value="${displayName}" placeholder="Eg: Nguyen Van A, gnaohuv55" maxlength="32" required><br>

                    <label for="email">Email<span class="required">*</span></label><br>
                    <input type="email" id="email" name="email" placeholder="Eg: example@example.com" value="${email}" maxlength="32"><br>

                    <label for="dob">Date of Birth<span class="required">*</span></label><br>
                    <input type="date" id="dob" name="dob" required><br>
                    <p style="color: graytext; font-family: sans-serif; font-size: 0.8em">User must be 12yo or older.</p>

                    <label for="bio">Bio</label><br>
                    <textarea style="resize: none" id="bio" name="bio" rows="5" cols="20" placeholder="Say something about youself..." maxlength="750">${bio}</textarea><br>

                    <label for="secretcode">Secret Code<span class="required">*</span></label><br>
                    <input type="password" id="secretcode" name="secretcode" maxlength="6" placeholder="For reset your password" required><br>

                    <p style="font-family: sans-serif; color: red">${errorMessage}</p>
                    <c:if test="${not empty confirmSuccess}">
                        <script>
                            //Set a delay for 3 seconds
                            setTimeout(function () {
                                window.location.href = "login";
                            }, 3000);
                        </script>
                    </c:if>

                    <input type="submit" value="Register" onclick="handleSubmit()">
                </form>
            </div>
        </div>
                    
        <footer style="position: relative">
            <%@include file="footer.jsp" %>
        </footer>
        
        <script>
            function validateForm() {
                var username = document.getElementById("username");
                var password = document.getElementById("password");
                var repassword = document.getElementById("repassword");
                var displayname = document.getElementById("displayname");
                var email = document.getElementById("email");
                var dob = document.getElementById("dob");
                var secretcode = document.getElementById("secretcode");

                if (!/^[a-zA-Z0-9]{8,}$/.test(username.value.trim())) {
                    username.setCustomValidity("Username must be at least 8 characters long and contains only number and alphabet characters.");
                    username.reportValidity();
                    return false;
                } else {
                    username.setCustomValidity("");
                }

                if (password.value.trim().length < 8) {
                    password.setCustomValidity("Password must be at least 8 characters long.");
                    password.reportValidity();
                    return false;
                } else {
                    password.setCustomValidity("");
                }

                if (password.value.trim() !== repassword.value.trim()) {
                    repassword.setCustomValidity("Re-entered password must be the same as password.");
                    repassword.reportValidity();
                    return false;
                } else {
                    repassword.setCustomValidity("");
                }

                if (!/^[a-zA-Z0-9 ]+$/.test(displayname.value.trim())) {
                    displayname.setCustomValidity("Display name must contain only alphabet, number or space characters.");
                    displayname.reportValidity();
                    return false;
                } else {
                    displayname.setCustomValidity("");
                }

                if (email.value.trim() && !/^([a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z]{2,})+)*$/.test(email.value.trim())) {
                    email.setCustomValidity("Please enter a valid email address.");
                    email.reportValidity();
                    return false;
                } else {
                    email.setCustomValidity("");
                }

                var today = new Date();
                var birthDate = new Date(dob.value.trim());
                var age = today.getFullYear() - birthDate.getFullYear();
                var m = today.getMonth() - birthDate.getMonth();
                if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                    age--;
                }

                if (age < 12) {
                    dob.setCustomValidity("User must be at least 12 years old.");
                    dob.reportValidity();
                    return false;
                } else {
                    dob.setCustomValidity("");
                }

                if (!/^\d{6}$/.test(secretcode.value.trim())) {
                    secretcode.setCustomValidity("Secret code must contain only numbers and must be exactly 6 digits.");
                    secretcode.reportValidity();
                    return false;
                } else {
                    secretcode.setCustomValidity("");
                }

                return true;
            }

            function handleSubmit() {
                if (!validateForm()) {
                    event.preventDefault();
                }
            }
        </script>
    </body>
</html>
