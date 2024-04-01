<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Home</title>
        <meta charset="UTF-8>
              <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="styles.css" />
    </head>
    <body>
        <% response.setHeader("Cache-Control", "no-cache, no-store,must - revalidate"); // HTTP 1.1. 
            response.setDateHeader("Expires", 0); // Proxies. 
            String username = null;
            String role = null; // Check if the username is null before getting the username and role
            if (session == null
                    || session.getAttribute("username") == null) {
                response.sendRedirect("error_session.jsp");
            } else {
                username = (String) session.getAttribute("username");
                role = (String) session.getAttribute("role");
            }%>
        <main id="main">
            <header>
                <span>
                    <button id="title">
                        <img src="https://i.imgur.com/blchS4w.png" alt="Icon" />
                        ${applicationScope.header}
                    </button>
                    <h2>${applicationScope.hiddenheader}</h2>
                </span>
            </header>

            <div class="body">
                <div class="logout">
                    <img src="https://i.imgur.com/vokvwP8.png" alt="Login Image" />
                </div>

                <h1 class="logout">Welcome <%= username%>!</h1>
                <h2 class="logout">You are logged in as <%= role%>.</h2>

                <div class="logout">
                    <form action="LogoutServlet" method="POST">
                        <button class="logout" id="logout" type="submit">LOGOUT</button>
                    </form>
                </div>
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
