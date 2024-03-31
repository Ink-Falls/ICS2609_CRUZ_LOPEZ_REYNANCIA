package test;

class User {

    private String username;
    private String role;
    private String password;

    // Constructor
    public User(String username, String role, String password) {
        this.username = username;
        this.role = role;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}

enum Role {
    Admin,
    Guest
}
