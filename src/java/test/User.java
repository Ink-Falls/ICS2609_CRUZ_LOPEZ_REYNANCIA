package test;

import javax.servlet.ServletContext;

class User {

    private final String username;
    private final Role role;
    private final String storedEncryptedPassword;

    // Constructor
    public User(String username, Role role, String storedEncryptedPassword) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        this.username = username;
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.role = role;
        if (storedEncryptedPassword == null) {
            throw new IllegalArgumentException("Stored encrypted password cannot be null");
        }
        this.storedEncryptedPassword = storedEncryptedPassword;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    // Removed the getter for storedEncryptedPassword

    public boolean verifyPassword(String inputPassword, ServletContext servletContext) {
        if (inputPassword == null) {
            return false;
        }
        String encryptionKey = servletContext.getInitParameter("ENCRYPTION_KEY");
        try {
            String decryptedStoredPassword = decryptStoredPassword(encryptionKey);
            return decryptedStoredPassword.equals(inputPassword);
        } catch (Exception e) {
            return false;
        }
    }

    public Role getRole() {
        return role;
    }

}

enum Role {
    Admin,
    Guest
}
