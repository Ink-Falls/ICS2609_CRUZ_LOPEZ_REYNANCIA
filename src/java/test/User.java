package test;

class User {

    private String username;
    private Role role;
    private String password;

    // Constructor
    public User(String username, Role role, String password) {
        this.username = username;
        this.role = role;
        setPassword(password);
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Security.encrypt(password, null);
    }
}

enum Role {
    Admin,
    Guest
}
