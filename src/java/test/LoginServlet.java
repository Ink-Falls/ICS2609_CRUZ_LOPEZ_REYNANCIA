package test;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthenticationService authenticationService;

    private static final String ERROR_INVALID_USERNAME = "Invalid username";
    private static final String ERROR_INVALID_PASSWORD = "Invalid password";
    private static final String ERROR_INVALID_USERNAME_PASSWORD = "Invalid username and password";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            String driver = config.getInitParameter("DB_driver");
            Class.forName(driver);
            String url = config.getInitParameter("DB_url");
            String dbUsername = config.getInitParameter("DB_username");
            String dbPassword = config.getInitParameter("DB_password");

            // Get the ServletContext object
            ServletContext servletContext = config.getServletContext();

            // Initialize AuthenticationService here
            authenticationService = new AuthenticationService(url, dbUsername, dbPassword, servletContext);

            // authenticationService.encryptExistingPasswords();
        } catch (ClassNotFoundException e) {
            throw new ServletException("Failed to load JDBC driver", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = authenticationService.authenticate(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());
                String correctCaptcha = (String) session.getAttribute("captcha");
                String enteredCaptcha = request.getParameter("captcha");
                if (correctCaptcha != null && correctCaptcha.equalsIgnoreCase(enteredCaptcha)) {
                    response.sendRedirect("success.jsp");
                } else {
                    response.sendRedirect("index.jsp?error=captcha");
                }
            }
        } catch (AuthenticationService.AuthenticationException e) {
            switch (e.getMessage()) {
                case ERROR_INVALID_USERNAME:
                    if (password == null || password.isEmpty()) {
                        response.sendRedirect("error_1.jsp");
                    } else {
                        response.sendRedirect("error_3.jsp");
                    }
                    break;
                case ERROR_INVALID_PASSWORD:
                    response.sendRedirect("error_2.jsp");
                    break;
                case ERROR_INVALID_USERNAME_PASSWORD:
                    if (username != null && password != null || !username.isEmpty() && !password.isEmpty()) {
                        response.sendRedirect("error_3.jsp");
                    } else {
                        response.sendRedirect("error_4.jsp");
                    }
                    break;
                default:
                    response.sendRedirect("error_4.jsp");
                    break;
            }
        } catch (AuthenticationService.NullValueException e) {
            response.sendRedirect("noLoginCredentials.jsp");
        } catch (IOException e) {
            response.sendRedirect("error404.jsp");
        }
    }
}
