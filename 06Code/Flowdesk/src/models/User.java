package models;

import enums.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private String userId;
    private String username;
    private String protectedPassword;
    private Role role;

    private static List<User> listaUsuarios = new ArrayList<>();

    public User(String userId, String username, String protectedPassword, Role role) {
        this.userId = userId;
        this.username = username;
        this.protectedPassword = protectedPassword;
        this.role = role;
    }
    
    public User() { }

    public static List<User> getAll() {
        return listaUsuarios;
    }

    public static void setAll(List<User> nuevaLista) {
        listaUsuarios = nuevaLista;
    }

    public static User gestionarAcceso(Role rolSeleccionado, Scanner scanner) {
        User usuarioExistente = buscarPorRol(rolSeleccionado);

        if (usuarioExistente == null) {
            System.out.println("\n--- PRIMER INGRESO: CONFIGURAR CUENTA PARA " + rolSeleccionado + " ---");
            System.out.print("Cree un nuevo Usuario: ");
            String nuevoUser = scanner.nextLine();
            System.out.print("Cree una Contrasena: ");
            String nuevaPass = scanner.nextLine();

            User nuevo = new User("U_" + rolSeleccionado, nuevoUser, nuevaPass, rolSeleccionado);
            listaUsuarios.add(nuevo);
            System.out.println("¡Cuenta creada con exito!");
            return nuevo;
            
        } else {
            System.out.println("\n--- INICIO DE SESION: " + rolSeleccionado + " ---");
            System.out.print("Usuario: ");
            String userInput = scanner.nextLine();
            System.out.print("Contrasena: ");
            String passInput = scanner.nextLine();

            if (usuarioExistente.validateCredentials(userInput, passInput)) {
                return usuarioExistente;
            } else {
                System.out.println("Acceso denegado. Credenciales incorrectas.\n");
                return null; 
            }
        }
    }

    private static User buscarPorRol(Role rol) {
        for (User u : listaUsuarios) {
            if (u.getRole() == rol) return u;
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
        System.out.println("Sesión cerrada correctamente para " + this.username);
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getProtectedPassword() { return protectedPassword; }
    public Role getRole() { return role; }
}