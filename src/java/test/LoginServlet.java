package test;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final List<User> users = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            String driver = getServletConfig().getInitParameter("DB_driver");
            Class.forName(driver);

            String url = getServletConfig().getInitParameter("DB_url");
            String DBusername = getServletConfig().getInitParameter("DB_username");
            String DBpassword = getServletConfig().getInitParameter("DB_password");
            try (Connection con = DriverManager.getConnection(url, DBusername, DBpassword)) {
                String query = "SELECT * FROM USER_INFO ORDER BY username";

                try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        users.add(new User(rs.getString("USERNAME").trim(),
                                rs.getString("PASSWORD").trim(),
                                rs.getString("ROLE").trim()));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException sqle) {
            throw new ServletException("Database connection problem", sqle);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get user input of username and password
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean checkUsername = false;
        boolean checkPassword = false;
        String redirectPage = "error_3.jsp"; // default redirect page

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                checkUsername = true;
                if (user.getPassword().equals(password)) {
                    checkPassword = true;
                    HttpSession session = request.getSession();
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("role", user.getRole());
                    redirectPage = "success.jsp";
                    break;
                }
            }
        }

        try {
            if (username.isEmpty() && password.isEmpty()) {
                redirectPage = "noLoginCredentials.jsp";
                    throw new NullValueException();
            } else if (!checkUsername && password.isEmpty()) {
                redirectPage = "error_1.jsp";
                    throw new AuthenticationException();
            } else if (checkUsername && !checkPassword) {
                redirectPage = "error_2.jsp";
                    throw new AuthenticationException();
            } else if (!checkUsername && !checkPassword) {
                redirectPage = "error_3.jsp";
                    throw new AuthenticationException();
            } else {
                redirectPage = "success.jsp";
            }
        } catch (NullValueException e) {
            redirectPage = "nullValueError.jsp";
        } catch (AuthenticationException e) {
            redirectPage = "authenticationError.jsp";
        } catch (Exception e) {
            // Handle other exceptions (for example, 404 error)
            redirectPage = "error404.jsp";
        }
        response.sendRedirect(redirectPage);
    }

    public class NullValueException extends Exception {

        public NullValueException() {
            super("Username or password is null.");
        }
    }

    public class AuthenticationException extends Exception {

        public AuthenticationException() {
            super("Incorrect username or password.");
        }
    }
}
