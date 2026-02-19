package model;

public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String role;

    public User(int id, String name, String hash, String role) {
        this.id = id;
        this.username = name;
        this.passwordHash = hash;
        this.role = role;
    }

    public String getRole() { return role; }
    public String getPasswordHash() { return passwordHash; }
    public String getUsername() { return username; }
}
