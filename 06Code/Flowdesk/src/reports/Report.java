package reports;

import enums.OrderStatus;
import enums.ReportType;
import models.CashRegister;
import models.WorkOrder;
import utils.Date;
import java.util.Scanner;

public class Report {
    private String reportId;
    private Date startDate;
    private Date endDate;
    private ReportType reportType;

    public Report(String reportId, Date startDate, Date endDate, ReportType reportType) {
        this.reportId = reportId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reportType = reportType;
    }

    // =========================================================
    // LÓGICA PARA GENERAR REPORTES (US005)
    // =========================================================
    public static void generarDesdeConsola(Scanner scanner, CashRegister caja) {
        System.out.println("\n--- GENERAR REPORTE ---");
        System.out.println("Seleccione el tipo de reporte:");
        System.out.println("1. Diario");
        System.out.println("2. Semanal");
        System.out.println("3. Mensual");
        System.out.print("Opcion: ");

        int opcion = Integer.parseInt(scanner.nextLine());
        ReportType tipo = null;

        switch (opcion) {
            case 1 -> tipo = ReportType.DAILY;
            case 2 -> tipo = ReportType.WEEKLY;
            case 3 -> tipo = ReportType.MONTHLY;
            default -> {
                System.out.println("Opcion no valida.");
                return;
            }
        }

        // Imprimimos el encabezado del reporte
        System.out.println("\n=============================================");
        System.out.println("        REPORTE " + tipo + " DE FLOWDESK      ");
        System.out.println("=============================================");
        System.out.println("Fecha de emision: 24/10/2026 (Simulada)");
        System.out.println("---------------------------------------------");

        // 1. Resumen de Ordenes
        int totalOrdenes = WorkOrder.getAll().size();
        int completadas = 0;
        int pendientes = 0;
        double dineroPorCobrar = 0;

        for (WorkOrder wo : WorkOrder.getAll()) {
            if (wo.getStatus() == OrderStatus.COMPLETED || wo.getStatus() == OrderStatus.DELIVERED) {
                completadas++;
            } else {
                pendientes++;
            }
            dineroPorCobrar += wo.getBalance();
        }

        System.out.println("[*] ESTADO DE ORDENES:");
        System.out.println("    - Total de ordenes: " + totalOrdenes);
        System.out.println("    - Ordenes terminadas: " + completadas);
        System.out.println("    - Ordenes pendientes: " + pendientes);
        System.out.println("    - Dinero por cobrar (Saldos): $" + dineroPorCobrar);

        // 2. Resumen Financiero (Caja)
        System.out.println("\n[*] RESUMEN FINANCIERO (CAJA):");
        System.out.println("    - Ingresos totales: $" + caja.calculateIncome());
        System.out.println("    - Egresos totales:  $" + caja.calculateExpenses());
        System.out.println("    - Balance neto:     $" + caja.calculateBalance());

        System.out.println("=============================================");
        System.out.println("             FIN DEL REPORTE                 ");
        System.out.println("=============================================\n");
    }

    // Getters
    public String getReportId() { return reportId; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public ReportType getReportType() { return reportType; }
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
import reports.Dashboard;
import reports.Report; // <-- IMPORTANTE: Importar Report
import java.util.Scanner;

public class FlowDesk {

    static Scanner scanner = new Scanner(System.in);
    static CashRegister caja = new CashRegister();
    static Dashboard dashboard = new Dashboard();

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

    // =========================================================
    // MENÚ ESPECÍFICO PARA CADA ROL
    // =========================================================
    static void menuRol(User usuario) {
        boolean cerrarSesion = false;

        while (!cerrarSesion) {
            System.out.println("\n=============================================");
            System.out.println("   MENU DE: " + usuario.getRole() + " | Usuario: " + usuario.getUsername());
            System.out.println("=============================================");
            
            if (null == usuario.getRole()) {
                System.out.println("1. Generar Reporte (US005)");
                System.out.println("2. Ver Estado de Caja");
                System.out.println("3. Cerrar Sesion");
                System.out.println("4. Salir del programa");
                System.out.print("Opcion: ");

                int opcionMenu = leerEntero();
                switch (opcionMenu) {
                    case 1 -> Report.generarDesdeConsola(scanner, caja);
                    case 2 -> caja.imprimirEstado();
                    case 3 -> {
                        usuario.logout();
                        cerrarSesion = true;
                    }
                    case 4 -> {
                        usuario.logout();
                        System.exit(0);
                    }
                    default -> System.out.println("Opcion no valida.");
                }
            } 
            // 2. MENÚ DEL CAJERO
            else // 1. MENÚ DEL EMPLEADO
            switch (usuario.getRole()) {
                case EMPLOYEE ->                     {
                        System.out.println("1. Gestionar Clientes");
                        System.out.println("2. Gestionar Productos y Servicios");
                        System.out.println("3. Crear Nueva Orden de Trabajo");
                        System.out.println("4. Actualizar Estado de Orden");
                        System.out.println("5. Ver Dashboard de Ordenes (US003)");
                        System.out.println("6. Cerrar Sesion");
                        System.out.println("7. Salir del programa");
                        System.out.print("Opcion: ");
                        int opcionMenu = leerEntero();
                        switch (opcionMenu) {
                            case 1 -> Customer.menuGestion(scanner);
                            case 2 -> menuCatalogo(scanner);
                            case 3 -> WorkOrder.registrarDesdeConsola(scanner, usuario);
                            case 4 -> WorkOrder.actualizarEstadoDesdeConsola(scanner);
                            case 5 -> {
                                dashboard.setOrders(WorkOrder.getAll());
                                dashboard.mostrarDashboard();
                    }
                            case 6 -> {
                                usuario.logout(); cerrarSesion = true;
                    }
                            case 7 -> {
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
                        System.out.println("1. Generar Reporte (US005)");
                        System.out.println("2. Ver Estado de Caja");
                        System.out.println("3. Cerrar Sesion");
                        System.out.println("4. Salir del programa");
                        System.out.print("Opcion: ");
                        int opcionMenu = leerEntero();
                        switch (opcionMenu) {
                            case 1 -> Report.generarDesdeConsola(scanner, caja);
                            case 2 -> caja.imprimirEstado();
                            case 3 -> {
                                usuario.logout();
                                cerrarSesion = true;
                    }
                            case 4 -> {
                                usuario.logout();
                                System.exit(0);
                    }
                            default -> System.out.println("Opcion no valida.");
                        }                          }
            }
        }
    }

    // =========================================================
    // ENRUTADOR PARA GESTIÓN DE CATÁLOGO (US004)
    // =========================================================
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

    // =========================================================
    // MÉTODOS AUXILIARES
    // =========================================================
    static int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
