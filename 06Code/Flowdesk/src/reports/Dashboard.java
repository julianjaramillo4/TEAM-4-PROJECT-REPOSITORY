package reports;

import enums.OrderStatus;
import models.WorkOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard {
    private List<WorkOrder> orders;
    private Map<OrderStatus, Integer> quantityByStatus;

    public Dashboard() {
        this.quantityByStatus = new HashMap<>();
    }

    public void setOrders(List<WorkOrder> orders) {
        this.orders = orders;
    }

    public void mostrarDashboard() {
        System.out.println("\n\n>>> CARGANDO DASHBOARD <<<");
        
        if (orders == null || orders.isEmpty()) {
            System.out.println("[!] ERROR: La lista de ordenes esta vacia.");
            System.out.println("[!] Asegurate de crear una orden primero.");
            return;
        }

        // 1. CONTEO POR ESTADOS
        quantityByStatus.clear();
        for (OrderStatus status : OrderStatus.values()) {
            quantityByStatus.put(status, 0);
        }

        for (WorkOrder order : orders) {
            OrderStatus status = order.getStatus();
            quantityByStatus.put(status, quantityByStatus.get(status) + 1);
        }

        System.out.println("\n=============================================");
        System.out.println("         DASHBOARD - ESTADO DE ORDENES       ");
        System.out.println("=============================================");
        System.out.println("Total de ordenes en el sistema: " + orders.size());
        System.out.println("---------------------------------------------");
        
        for (Map.Entry<OrderStatus, Integer> entry : quantityByStatus.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue() + " orden(es)");
        }
        System.out.println("=============================================");

        // 2. ORDENES PENDIENTES
        System.out.println("\n--- ORDENES PENDIENTES (WAITING / IN_PROGRESS) ---");
        boolean hayPendientes = false;
        for (WorkOrder wo : orders) {
            if (wo.getStatus() == OrderStatus.WAITING || wo.getStatus() == OrderStatus.IN_PROGRESS) {
                System.out.println("- [" + wo.getOrderId() + "] Cliente: " + wo.getCustomer().getName() + " | Estado: " + wo.getStatus());
                hayPendientes = true;
            }
        }
        if (!hayPendientes) {
            System.out.println("No hay ordenes pendientes. ¡Todo al dia!");
        }

        // 3. LISTADO COMPLETO DE ORDENES (Lo que antes hacía el Listar Ordenes)
        System.out.println("\n--- HISTORIAL COMPLETO DE ORDENES ---");
        for (WorkOrder wo : orders) {
            System.out.println("- [" + wo.getOrderId() + "] Fecha: " + wo.getDate().toString() + 
                               " | Cliente: " + wo.getCustomer().getName() + 
                               " | Estado: " + wo.getStatus() + 
                               " | Total: $" + wo.getTotal() + 
                               " | Saldo: $" + wo.getBalance());
        }
        
        System.out.println("=============================================");
        System.out.println("\n>>> FIN DEL DASHBOARD <<<\n");
    }

    public List<WorkOrder> getOrders() { return orders; }
    public Map<OrderStatus, Integer> getQuantityByStatus() { return quantityByStatus; }
}
FlowDesk.java
package com.mycompany.flowdesk;

import enums.Role;
import models.User;
import models.Customer;
import models.Category;
import models.Product;
import models.Service;
import models.CashRegister;
import models.CashMovement;
import models.WorkOrder;
import models.Payment;
import reports.Dashboard; // <-- IMPORTANTE
import java.util.Scanner;

public class FlowDesk {

    static Scanner scanner = new Scanner(System.in);
    static CashRegister caja = new CashRegister();
    static Dashboard dashboard = new Dashboard(); // <-- IMPORTANTE

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
            switch (opcionRol) {
                case 1 -> rolSeleccionado = Role.MANAGER;
                case 2 -> rolSeleccionado = Role.CASHIER;
                case 3 -> rolSeleccionado = Role.EMPLOYEE;
                default -> {
                    System.out.println("Opcion no valida.");
                    continue;
                }
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
            System.out.println("\n=============================================");
            System.out.println("   MENU DE: " + usuario.getRole() + " | Usuario: " + usuario.getUsername());
            System.out.println("=============================================");
            
            if (null == usuario.getRole()) {
                System.out.println("Aqui iran las funcionalidades de " + usuario.getRole() + ".");
                System.out.println("1. Cerrar Sesion");
                System.out.println("2. Salir del programa");
                System.out.print("Opcion: ");

                int opcionMenu = leerEntero();
                switch (opcionMenu) {
                    case 1 -> {
                        usuario.logout(); cerrarSesion = true;
                    }
                    case 2 -> {
                        usuario.logout(); System.exit(0);
                    }
                    default -> System.out.println("Opcion no valida.");
                }
            } 
            else switch (usuario.getRole()) {
                case EMPLOYEE ->                     {
                        System.out.println("1. Gestionar Clientes");
                        System.out.println("2. Gestionar Productos y Servicios");
                        System.out.println("3. Crear Nueva Orden de Trabajo");
                        System.out.println("4. Listar Ordenes");
                        System.out.println("5. Actualizar Estado de Orden");
                        System.out.println("6. Ver Dashboard de Ordenes (US003)"); // <-- OPCIÓN NUEVA
                        System.out.println("7. Cerrar Sesion");
                        System.out.println("8. Salir del programa");
                        System.out.print("Opcion: ");
                        int opcionMenu = leerEntero();
                        switch (opcionMenu) {
                            case 1 -> Customer.menuGestion(scanner);
                            case 2 -> menuCatalogo(scanner);
                            case 3 -> WorkOrder.registrarDesdeConsola(scanner, usuario);
                            case 5 -> WorkOrder.actualizarEstadoDesdeConsola(scanner);
                            case 6 -> { 
                                dashboard.setOrders(WorkOrder.getAll()); // Le pasamos las órdenes
                                dashboard.mostrarDashboard();            // Llamamos al nuevo método
                    }
                            case 7 -> {
                                usuario.logout(); cerrarSesion = true;
                            }
                            case 8 -> {
                                usuario.logout(); System.exit(0);
                            }
                            default -> System.out.println("Opcion no valida.");
                        }                          }
                case CASHIER ->                     {
                        System.out.println("1. Registrar Movimiento de Caja Manual");
                        System.out.println("2. Registrar Pago de Orden (US009)");
                        System.out.println("3. Ver Estado de Caja");
                        System.out.println("4. Historial de Movimientos");
                        System.out.println("5. Cerrar Sesion");
                        System.out.println("6. Salir del programa");
                        System.out.print("Opcion: ");
                        int opcionMenu = leerEntero();
                        switch (opcionMenu) {
                            case 1 -> CashMovement.registrarDesdeConsola(scanner, caja);
                            case 2 -> Payment.registrarDesdeConsola(scanner, caja);
                            case 3 -> caja.imprimirEstado();
                            case 4 -> CashMovement.listarDesdeConsola();
                            case 5 -> {
                                usuario.logout(); cerrarSesion = true;
                            }
                            case 6 -> {
                                usuario.logout(); System.exit(0);
                            }
                            default -> System.out.println("Opcion no valida.");
                        }                          }
                default ->                     {
                        System.out.println("Aqui iran las funcionalidades de " + usuario.getRole() + ".");
                        System.out.println("1. Cerrar Sesion");
                        System.out.println("2. Salir del programa");
                        System.out.print("Opcion: ");
                        int opcionMenu = leerEntero();
                        switch (opcionMenu) {
                            case 1 -> {
                                usuario.logout(); cerrarSesion = true;
                            }
                            case 2 -> {
                                usuario.logout(); System.exit(0);
                            }
                            default -> System.out.println("Opcion no valida.");
                        }                          }
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

    static int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
