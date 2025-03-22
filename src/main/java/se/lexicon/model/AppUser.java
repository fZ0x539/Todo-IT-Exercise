package se.lexicon.model;

import java.util.Objects;

public class AppUser {
    private String username;
    private String password;
    private AppRole role;

    public AppUser(String username, String password, AppRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is null or empty");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is null or empty");
        }

        this.password = password;
    }

    public AppRole getRole() {
        return role;
    }

    public void setRole(AppRole role) {
        Objects.requireNonNull(role, "Role cannot be null!");
        this.role = role;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "username='" + username + '\'' +
                ", role=" + role +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser appUser)) return false;
        return Objects.equals(getUsername(), appUser.getUsername()) && Objects.equals(getRole(), appUser.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getRole());
    }
}
