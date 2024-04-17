package test;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;

public class AuthenticationService {

    private final String url;
    private final String dbUsername;
    private final String dbPassword;
    private Map<String, User> users;
    private ServletContext servletContext;

    public AuthenticationService(String url, String dbUsername, String dbPassword, ServletContext servletContext) {
        this.url = url;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.servletContext = servletContext;
    }

    public void encryptExistingPasswords() {
        try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            // Start transaction
            con.setAutoCommit(false);

            String selectQuery = "SELECT username, password FROM USER_INFO";
            String updateQuery = "UPDATE USER_INFO SET password = ? WHERE username = ?";

            try (PreparedStatement selectStmt = con.prepareStatement(selectQuery);
                    ResultSet rs = selectStmt.executeQuery();
                    PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {

                while (rs.next()) {
                    String username = rs.getString("username");
                    String plaintextPassword = rs.getString("password");

                    if (plaintextPassword != null) {
                        String key = servletContext.getInitParameter("ENCRYPTION_KEY");
                        String encryptedPassword = Security.encrypt(plaintextPassword.trim(), key);

                        updateStmt.setString(1, encryptedPassword);
                        updateStmt.setString(2, username);
                        updateStmt.executeUpdate();
                    }
                }
                // Commit transaction
                con.commit();
            } catch (Exception e) {
                // Rollback transaction in case of error
                con.rollback();
                System.out.println("Failed to encrypt and update passwords: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Database connection error during password encryption: " + e.getMessage());
        }
    }

    private boolean isPasswordValid(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public User authenticate(String username, String password) throws NullValueException, AuthenticationException {
        if (username == null || password == null || username.isEmpty()) {
            throw new NullValueException("Username and password must not be null");
        }

        try {
            if (users == null) {
                users = loadUserData();
            }

            if (!isUsernameValid(username)) {
                if (password.isEmpty()) {
                    throw new AuthenticationException("Invalid username");
                } else {
                    throw new AuthenticationException("Invalid username and password");
                }
            } else {
                if (isPasswordValid(username, password)) {
                    return users.get(username);
                } else {
                    throw new AuthenticationException("Invalid password");
                }
            }
        } catch (NullPointerException e) {
            // Log the exception or perform any necessary error handling
            throw new AuthenticationException("An error occurred during authentication", e);
        }
    }

    public Map<String, User> loadUserData() {
        Map<String, User> users = new HashMap<>();
        try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT * FROM USER_INFO ORDER BY username";

            try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String username = null;
                    String encryptedPassword = null;
                    String role = null;

                    // Handle potential null values
                    String usernameValue = rs.getString("USERNAME");
                    if (usernameValue != null) {
                        username = usernameValue.trim();
                    }
                    String passwordValue = rs.getString("PASSWORD");
                    if (passwordValue != null) {
                        encryptedPassword = passwordValue.trim();
                    }
                    String roleValue = rs.getString("ROLE");
                    if (roleValue != null) {
                        role = roleValue.trim();
                    }

                    String decryptedPassword = null;
                    if (encryptedPassword != null) {
                        try {
                            ServletContext servletContext = this.servletContext;
                            decryptedPassword = Security.decrypt(encryptedPassword, servletContext);
                        } catch (Exception e) {
                            System.out.println("Failed to decrypt password: " + e.getMessage());
                        }
                    }

                    // Create a User object only if username and role are not null
                    if (username != null && role != null) {
                        users.put(username, new User(username, role, decryptedPassword));
                    } else {
                        // Log or handle the case when username or role is null
                        System.out.println("Skipping user record with null username or role");
                    }
                }
            }
        } catch (NullPointerException e) {
            // Log the exception or perform any necessary error handling
            System.out.println("NullPointerException occurred while loading user data: " + e.getMessage());
        } catch (SQLException e) {
            // Log the exception or perform any necessary error handling
            System.out.println("Failed to load user data: " + e.getMessage());
        }
        return users;
    }

    private boolean isUsernameValid(String username) {
        return users.containsKey(username);
    }

    public static class AuthenticationException extends Exception {

        public AuthenticationException(String message) {
            super(message);
        }

        public AuthenticationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class NullValueException extends Exception {

        public NullValueException(String message) {
            super(message);
        }
    }
}
