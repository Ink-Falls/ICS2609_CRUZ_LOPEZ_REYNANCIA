package test;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;

public class AuthenticationService {

    private final String url;
    private final String dbUsername;
    private final String dbPassword;

    public AuthenticationService(String url, String dbUsername, String dbPassword, ServletContext servletContext) {
        this.url = url;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    private Map<String, User> loadUserData() {
        Map<String, User> users = new HashMap<>();
        try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT * FROM USER_INFO ORDER BY username";

            try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String username = null;
                    String encryptedPassword = null;
                    Role role = null;

                    // Handle potential null values
                    if (rs.getString("USERNAME") != null) {
                        username = rs.getString("USERNAME").trim();
                    }
                    if (rs.getString("PASSWORD") != null) {
                        encryptedPassword = rs.getString("PASSWORD").trim();
                    }
                    if (rs.getString("ROLE") != null) {
                        String roleString = rs.getString("ROLE").trim();
                        if (roleString != null) {
                            role = Role.valueOf(roleString);
                        }
                    }

                    // Create a User object only if username and role are not null
                    if (username != null && role != null) {
                        users.put(username, new User(username, role, encryptedPassword));
                    } else {
                        // Log or handle the case when username or role is null
                        System.out.println("Skipping user record with null username or role");
                    }
                }
            }
        } catch (SQLException e) {
            // Log the exception or perform any necessary error handling
            System.out.println("Failed to load user data: " + e.getMessage());
        } catch (NullPointerException e) {
            // Log the exception or perform any necessary error handling
            System.out.println("NullPointerException occurred while loading user data: " + e.getMessage());
        }
        return users;
    }

    private boolean isUsernameValid(String username, Map<String, User> users) {
        return users.containsKey(username);
    }

    private boolean isPasswordValid(String username, String password, Map<String, User> users) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .anyMatch(user -> user.getPassword().equals(password));
    }

    public User authenticate(String username, String password) throws NullValueException, AuthenticationException {
        try {
            if (username == null || username.isEmpty()) {
                throw new NullValueException("Username cannot be empty");
            }

            Map<String, User> users = loadUserData();

            if (!isUsernameValid(username, users)) {
                throw new AuthenticationException("Invalid username");
            }

            if (password == null || password.isEmpty()) {
                throw new NullValueException("Password cannot be empty");
            }

            if (isPasswordValid(username, password, users)) {
                return users.get(username);
            } else {
                throw new AuthenticationException("Invalid password");
            }
        } catch (NullPointerException e) {
            // Log the exception or perform any necessary error handling
            throw new AuthenticationException("An error occurred during authentication", e);
        }
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
