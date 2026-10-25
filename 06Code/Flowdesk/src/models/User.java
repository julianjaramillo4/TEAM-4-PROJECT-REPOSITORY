package models;

import enums.Role;

public class User {
    private String userId;
    private String username;
    private String protectedPassword;
    private Role role;


    public User(String userId, String username, String protectedPassword, Role role) {
        this.userId = userId;
        this.username = username;
        this.protectedPassword = protectedPassword;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + getUserId() + ", username=" + getUsername() + ", protectedPassword=" + getProtectedPassword() + ", role=" + getRole() + '}';
    }

    public boolean login() {
        return true;
    }

    public void logout() {
    }

    public boolean validateCredentials(String user, String pass) {
        return false;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the protectedPassword
     */
    public String getProtectedPassword() {
        return protectedPassword;
    }

    /**
     * @param protectedPassword the protectedPassword to set
     */
    public void setProtectedPassword(String protectedPassword) {
        this.protectedPassword = protectedPassword;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }


}
