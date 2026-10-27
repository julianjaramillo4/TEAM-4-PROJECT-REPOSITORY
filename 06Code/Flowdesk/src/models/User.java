package models;

import enums.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.ConsoleInput;

public class User {

    private String userId;
    private String username;
    private String protectedPassword;
    private Role role;

    private static List<User> users = new ArrayList<>();

    public User() { }

    public User(String userId, String username, String protectedPassword, Role role) {
        this.userId = userId;
        this.username = username;
        this.protectedPassword = protectedPassword;
        this.role = role;
    }

    public static List<User> getAll() {
        return users;
    }

    public static void setAll(List<User> newItems) {
        users = newItems;
    }

    public static User authenticateFromConsole(Role selectedRole, Scanner scanner) {
        User existingUser = findByRole(selectedRole);

        if (existingUser == null) {
            System.out.println("\n--- PRIMER INGRESO: CONFIGURAR CUENTA PARA " + selectedRole + " ---");
            String newUsername = ConsoleInput.readRequiredText(scanner, "Cree un nuevo Usuario: ");

            String newPassword = ConsoleInput.readRequiredText(scanner, "Cree una Contrasena: ");

            User newUser = new User(
                    "U_" + selectedRole.name(),
                    newUsername,
                    newPassword,
                    selectedRole
            );

            users.add(newUser);

            System.out.println("Cuenta creada con exito!");
            return newUser;

        } else {
            System.out.println("\n--- INICIO DE SESION: " + selectedRole + " ---");
            String userInput = ConsoleInput.readRequiredText(scanner, "Usuario: ");

            String passInput = ConsoleInput.readRequiredText(scanner, "Contrasena: ");

            if (existingUser.validateCredentials(userInput, passInput)) {
                return existingUser;
            } else {
                System.out.println("Acceso denegado. Credenciales incorrectas.\n");
                return null;
            }
        }
    }

    private static User findByRole(Role role) {
        for (User u : users) {
            if (u.getRole() == role) {
                return u;
            }
        }
        return null;
    }

    public boolean validateCredentials(String user, String pass) {
        return this.username.equals(user) && this.protectedPassword.equals(pass);
    }

    public void login() {
        System.out.println("Bienvenido/a, " + this.username + " (" + this.role + ")");
    }

    public void logout() {
        System.out.println("Sesion cerrada correctamente para " + this.username);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProtectedPassword() {
        return protectedPassword;
    }

    public void setProtectedPassword(String protectedPassword) {
        this.protectedPassword = protectedPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Usuario{"
                + "identificador=" + userId
                + ", usuario=" + username
                + ", contrasena=[OCULTA]"
                + ", rol=" + role
                + '}';
    }
}
