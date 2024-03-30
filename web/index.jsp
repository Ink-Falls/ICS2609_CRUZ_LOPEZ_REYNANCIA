<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0); // Proxies.
            
            if (session != null && session.getAttribute("username") != null) {
                session.invalidate();
            }
        %>
        <main id="main">
            <header>
                <span>
                    <button id="title">
                        <img src="https://i.imgur.com/blchS4w.png" alt="Icon">
                        ${applicationScope.header}
                    </button>
                    <h2>${applicationScope.hiddenheader}</h2>
                </span>
            </header>

            <div class="body">
                <div class="login">
                    <img src="https://i.imgur.com/vokvwP8.png" alt="Login Image">
                </div>
                <form action="LoginServlet" method="post">
                    <div class="form-container">
                        <input type="text" id="username" name="username" class="username-input" placeholder="Username">
                        <input type="password" id="password" name="password" class="password-input" placeholder="Password">
                    </div>
                    <div class="login">
                        <button class="login" type="submit">LOGIN</button>
                    </div>
                </form>
            </div>
                
            <footer>
                <div class="carousel">
                    <div class="carousel-inner">
                        <h1 class="carousel-item">
                            <span>${applicationScope.hiddenfooter}</span>
                        </h1>
                    </div>
                </div>
                <button>${applicationScope.footer}</button>
            </footer>
        </main>
    </body>
</html>