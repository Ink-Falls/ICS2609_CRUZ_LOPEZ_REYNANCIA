<%-- 
    Document   : error_1
    Created on : Feb 17, 2024, 5:16:53 PM
    Author     : Darwin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <style>
            * {
                font-family: Tahoma;
            }

            body {
                background: #FFFFFF;
                margin-top: 200px;
                margin-bottom: 7%;
            }

            header {
                width: 100%;
                height: 5%;
                min-height: 60px;
                background-color: #373944;
                align-items: center;
                position: fixed;
                display: flex;
                top: 0;
                left: 0;
                right: 0;
                z-index: 1;
            }

            header button {
                color: #FFFFFF;
                padding-left: 65px;
                padding-top: 10px;
                padding-bottom: 11px;
                padding-right: 30px;
                white-space: nowrap;
                height: 100%;
                background-color: #1E1E25;
                position: relative;
                border-top-right-radius: 100px;
                border-bottom-right-radius: 100px;
                z-index: 1;
                line-height: 1.2;
                user-select: none;
                font-size: 200%;
                font-weight: bold;
                border: none;
            }

            header button img {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                width: 50px;
                margin-left: -55px;
                user-select: none;
            }

            header h2 {
                color: #373944;
                position: absolute;
                margin-top: -45px;
                left: 50%;
                transform: translateX(-50%);
                white-space: nowrap;
                user-select: none;
            }

            button.error:hover,
            header button:hover,
            footer button:hover {
                background-color: #D3FCCC;
                color: #1E1E25;
            }

            button.error:active,
            header button:active,
            footer button:active {
                background-color: #6A8C8C;
                color: #1E1E25;
                transition: background-color 0.01s ease-in-out, color 0.01s ease-in-out;
            }

            button.error,
            header button,
            footer button {
                transition: background-color 0.3s ease-in-out, color 0.1s ease-in-out;
            }

            .carousel-item,
            header h2 {
                transition: background-color 1.3s ease-in-out, color 1.1s ease-in-out;
            }

            footer {
                width: 100%;
                height: 7%;
                background: #1E1E25;
                align-items: center;
                position: fixed;
                display: flex;
                bottom: 0;
                left: 0;
                right: 0;
            }

            .carousel {
                width: 100%;
                overflow: hidden;
                position: absolute;
                top: 0;
                left: 0;
            }

            .carousel-inner {
                display: flex;
                animation: scroll 10s linear infinite;
            }

            .carousel-item {
                padding: 20px;
                text-align: center;
                color: #1E1E25;
                white-space: nowrap;
                margin-top: -5px;
                user-select: none;
            }

            @media (min-width: 1080px) {
                @keyframes scroll {
                    0% {
                        transform: translateX(100%);
                    }
                    100% {
                        transform: translateX(-100%);
                    }
                }

                .carousel-item:hover {
                    color: #D3FCCC;
                }
            }

            @media (min-width: 1280px) {
                header h2:hover {
                    color: #D3FCCC;
                }
            }

            footer button {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                text-align: center;
                color: #FFFFFF;
                max-height: 80px;
                font-size: 100%;
                background-color: #373944;
                border-radius: 100px;
                padding: 15px;
                width: 100px;
                white-space: nowrap;
                z-index: 1;
                user-select: none;
                border: none;
                font-weight: bold;
            }

            div.error {
                display: flex;
                justify-content: center;
                align-items: center;
                user-select: none;
            }

            div.error img {
                max-width: 300px;
            }

            h1.error {
                display: flex;
                justify-content: center;
                align-items: center;
                color: #373944;
                user-select: none;
                font-size: 3em;
                margin-top: -5px;
            }

            h2.error {
                display: flex;
                justify-content: center;
                align-items: center;
                color: #373944;
                user-select: none;
                font-size: 2em;
                margin-top: -20px;
            }

            .body {
                justify-content: center;
                align-items: center;
                margin: 0;
            }

            button.error {
                text-align: center;
                color: #FFFFFF;
                font-size: 1em;
                background-color: #373944;
                border-radius: 100px;
                padding: 1em 2em;
                width: 250px;
                white-space: nowrap;
                user-select: none;
                border: none;
                font-weight: bold;
                margin-top: 30px;
            }

            img {
                -webkit-user-drag: none;
                pointer-events: none;
            }

        </style>
        <title>Home</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="styles.css" />
    </head>
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

                <h1 class="error">ERROR 404</h1>
                <h2 class="error">PAGE NOT FOUND.</h2>

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
