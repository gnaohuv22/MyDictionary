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
        <title>Account Settings</title>
        <link rel="stylesheet" href="CSS/header.css">
        <link rel="stylesheet" href="CSS/accountSetting.css">
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

        <form action="accountsettings" method="POST">
            <div class="container">
                <div class="account-box">
                    <div class="summary-inf">
                        <img class="img" src="default-profile.png" width="400" height="400" alt="default-profile-icon">
                    </div>

                    <div class="information">
                        <label for="username">Username<span class="required">*</span></label><br>
                        <input type="text" id="username" name="username" value="${u.getUsername()}" placeholder="Username" maxlength="32" readonly><br>

                        <label for="displayName">Display Name<span class="required">*</span></label><br>
                        <input type="text" id="displayname" name="displayname" value="${u.getDisplayname()}" placeholder="Eg: Nguyen Van A, gnaohuv55" maxlength="32" required><br>

                        <label for="email">Email<span class="required">*</span></label><br>
                        <input type="email" id="email" name="email" placeholder="Eg: example@example.com" value="${u.getEmail()}" maxlength="32"><br>

                        <label for="dob">Date of Birth<span class="required">*</span></label><br>
                        <input type="date" id="dob" name="dob" value="${u.getDob()}" required><br>
                        <p style="color: graytext; font-family: sans-serif; font-size: 0.8em">User must be 12yo or older.</p>

                        <label for="bio">Bio</label><br>
                        <textarea style="resize: none" id="bio" name="bio" rows="5" cols="20" placeholder="Say something about youself..." maxlength="750">${u.getBio()}</textarea><br>

                        <p style="font-family: sans-serif; color: red;">${msg}</p>

                        <input type="submit" value="Save" onclick="handleSubmit()">
                    </div>
                </div>
            </div>
        </form>
        <script>
            function toggleMenu() {
                var menu = document.querySelector('.task');
                menu.classList.toggle('show-menu');
            }
            function validateForm() {
                var username = document.getElementById("username");
                var displayname = document.getElementById("displayname");
                var email = document.getElementById("email");
                var dob = document.getElementById("dob");
                if (!/^[a-zA-Z0-9]{8,}$/.test(username.value.trim())) {
                    username.setCustomValidity("Username must be at least 8 characters long and contains only number and alphabet characters.");
                    username.reportValidity();
                    return false;
                } else {
                    username.setCustomValidity("");
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
