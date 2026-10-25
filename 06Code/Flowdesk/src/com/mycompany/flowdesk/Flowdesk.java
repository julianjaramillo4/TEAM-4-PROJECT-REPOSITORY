package com.mycompany.flowdesk;

import enums.Role;
import models.User;
import models.Category;
import models.Product;
import models.Service;
import models.WorkOrder;
import java.util.Scanner;

public class Flowdesk {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salirPrograma = false;

        while (!salirPrograma) {
            System.out.println("\n=============================================");
            System.out.println("        FLOWDESK - SISTEMA DE GESTION        ");
            System.out.println("=============================================");
            System.out.println("1. MANAGER (Gerente)");
            System.out.println("2. CASHIER (Cajero)");
            System.out.println("3. EMPLOYEE (Empleado)");
            System.out.println("4. Salir del programa");
            System.out.print("Seleccione su rol: ");

            int opcionRol = leerEntero();

            if (opcionRol == 4) {
                System.out.println("Saliendo del programa...");
                break;
            }

            Role rolSeleccionado = null;
            if (opcionRol == 1) rolSeleccionado = Role.MANAGER;
            else if (opcionRol == 2) rolSeleccionado = Role.CASHIER;
            else if (opcionRol == 3) rolSeleccionado = Role.EMPLOYEE;
            else {
                System.out.println("Opcion no valida.");
                continue;
            }

            User usuarioLogueado = User.gestionarAcceso(rolSeleccionado, scanner);

            if (usuarioLogueado != null) {
                usuarioLogueado.login();
                menuRol(usuarioLogueado);
            }
        }
    }
    static void menuRol(User usuario) {
        boolean cerrarSesion = false;
        while (!cerrarSesion) {
            System.out.println("\nMENU DE: " + usuario.getRole() + " | Usuario: " + usuario.getUsername());
            if (usuario.getRole() == Role.EMPLOYEE) {
                System.out.println("1. Gestionar Productos y Servicios");
                System.out.println("2. Crear Nueva Orden de Trabajo");
                System.out.println("3. Listar Ordenes");
                System.out.println("4. Cerrar Sesion");
                System.out.println("5. Salir del programa");
                System.out.print("Opcion: ");
                switch (leerEntero()) {
                    case 1 -> menuCatalogo(scanner);
                    case 2 -> WorkOrder.registrarDesdeConsola(scanner, usuario);
                    case 3 -> WorkOrder.listarDesdeConsola();
                    case 4 -> { usuario.logout(); cerrarSesion = true; }
                    case 5 -> { usuario.logout(); System.exit(0); }
                    default -> System.out.println("Opcion no valida.");
                }
            } else {
                System.out.println("1. Cerrar Sesion");
                System.out.println("2. Salir del programa");
                System.out.print("Opcion: ");
                switch (leerEntero()) {
                    case 1 -> { usuario.logout(); cerrarSesion = true; }
                    case 2 -> { usuario.logout(); System.exit(0); }
                    default -> System.out.println("Opcion no valida.");
                }
            }
        }
    }
    static void menuCatalogo(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTION DE CATALOGO ---");
            System.out.println("1. Registrar nueva Categoria");
            System.out.println("2. Registrar Producto");
            System.out.println("3. Registrar Servicio");
            System.out.println("4. Listar Productos");
            System.out.println("5. Listar Servicios");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            int opcion = leerEntero();
            switch (opcion) {
                case 1 -> Category.registrarDesdeConsola(scanner);
                case 2 -> Product.registrarDesdeConsola(scanner);
                case 3 -> Service.registrarDesdeConsola(scanner);
                case 4 -> Product.listarDesdeConsola();
                case 5 -> Service.listarDesdeConsola();
                case 0 -> volver = true;
                default -> System.out.println("Opcion no valida.");
            }
        }
    }
    // Método auxiliar para no llenar el código de try-catch
    static int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Retorna -1 si ingresa letras
        }
    }
}
