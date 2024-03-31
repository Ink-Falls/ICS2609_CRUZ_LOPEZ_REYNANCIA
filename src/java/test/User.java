package test;

class User {

    private final String username;
    private final Role role;
    private final String password;

    // Constructor
    public User(String username, Role role, String password) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        this.username = username;
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.role = role;
        if (password == null) {
            throw new IllegalArgumentException("Stored encrypted password cannot be null");
        }
        this.password = password;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    // Removed the getter for password

    public boolean verifyPassword(String inputPassword) {
        return password.equals(inputPassword);
    }
    public Role getRole() {
        return role;
    }

}

enum Role {
    Admin,
    Guest
}
