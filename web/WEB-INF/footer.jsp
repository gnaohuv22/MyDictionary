<%-- 
    Document   : footer
    Created on : Jul 7, 2023, 11:24:35 PM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            body {
                margin: 0;
            }
            .content {
                min-height: 100vh;
                margin-bottom: -50px;
            }
            .content:after {
                content: "";
                display: block;
                height: 10vh;
            }
            footer {
                bottom: 0;
                width: 100%;
                height: 8vh;
                background-color: #333;
                color: white;
                font-family: sans-serif;
                display: flex;
                align-items: center;
                justify-content: center;
                box-shadow: 4px 4px 8px 2px rgba(0, 0, 0, 0.2);
                z-index: 1;
            }
        </style>
    </head>
    <body>
        <div class="content">
            <!-- Page content goes here -->
        </div>
        <footer>
            <p>Copyright by Hoang Vu. All rights reserved.</p>
        </footer>
    </body>
</html>
