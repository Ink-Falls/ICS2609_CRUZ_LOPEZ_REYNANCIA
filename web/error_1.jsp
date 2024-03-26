<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles.css">
    <body>
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

            <div class ="body">
                <div class="error">
                    <img src="https://i.imgur.com/F3NmRxz.png" alt="Error Image">
                </div>

                <h1 class="error">INVALID LOGIN</h1>
                <h2 class="error">INCORRECT USERNAME.</h2>

                <div class="error">
                    <button class="error" onclick="logout()"Log out">BACK TO LOGIN PAGE</button>
                </div>
            </div>

            <script>
                function logout() {
                    // Redirect to the success page directly
                    window.location.href = 'index.jsp';
                }
            </script>

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