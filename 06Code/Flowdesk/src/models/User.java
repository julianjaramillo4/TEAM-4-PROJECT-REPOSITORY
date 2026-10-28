package models;

import enums.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private Role role;

    private static List<User> users = new ArrayList<>();

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void register() {
        if (!users.contains(this)) {
            users.add(this);
        }
    }

    public void login() {
        System.out.println("Sesion iniciada exitosamente para el usuario: " + username);
    }

    public void logout() {
        System.out.println("Sesion cerrada.");
    }

    public static List<User> getAll() {
        return users;
    }

    @SuppressWarnings("unchecked")
    public static void setAll(List userList) {
        if (userList != null) {
            users = new ArrayList<>(userList);
        }
    }

    public static void setUsers(List<User> userList) {
        setAll(userList);
    }

    public static User authenticateFromConsole(Role role, Scanner scanner, Runnable saveCallback) {
        System.out.println("\n--- INICIO DE SESION: " + role + " ---");
        System.out.print("Usuario: ");
        String username = scanner.nextLine().trim();
        System.out.print("Contrasena: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty()) {
            username = "admin";
        }

        User user = new User(username, password, role);
        user.register();
        return user;
    }
}