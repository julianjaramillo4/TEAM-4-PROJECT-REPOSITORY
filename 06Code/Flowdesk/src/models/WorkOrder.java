package models;

import enums.OrderStatus;
import utils.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorkOrder {
    private String orderId;
    private Date date;
    private double total;
    private double balance;
    private OrderStatus status;

    private Customer customer;
    private List<OrderDetail> details;
    private List<Payment> payments;
    private User user;

    private static List<WorkOrder> listaOrdenes = new ArrayList<>();
    private static int contadorOrdenes = 1;

    public WorkOrder(String orderId, Date date, OrderStatus status) {
        this.orderId = orderId;
        this.date = date;
        this.status = status;
        this.details = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.total = 0;
        this.balance = 0;
    }

    public void create() {
        listaOrdenes.add(this);
    }

    public static List<WorkOrder> getAll() {
        return listaOrdenes;
    }

    public static void registrarDesdeConsola(Scanner scanner, User empleado) {
        System.out.println("\n--- CREAR NUEVA ORDEN DE TRABAJO ---");

        String nuevoId = String.format("WO-%03d", contadorOrdenes++);
        System.out.println("Numero de Orden generado: " + nuevoId);

        System.out.print("Ingrese ID o Cedula del Cliente: ");
        String criterio = scanner.nextLine();
        Customer cliente = Customer.search(criterio);

        if (cliente == null) {
            System.out.println("Error: Cliente no encontrado. Debe registrarlo primero.");
            return;
        }
        System.out.println("Cliente asignado: " + cliente.getName());

        WorkOrder nuevaOrden = new WorkOrder(nuevoId, new Date(24, 10, 2026), OrderStatus.WAITING);
        nuevaOrden.customer = cliente;
        nuevaOrden.user = empleado;

        boolean agregarMas = true;
        int contadorDetalles = 1;

        while (agregarMas) {
            System.out.println("\n1. Agregar Producto");
            System.out.println("2. Agregar Servicio");
            System.out.println("0. Terminar y guardar Orden");
            System.out.print("Opcion: ");

            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion == 0) break;

            OrderDetail detalle = null;

            if (opcion == 1) {
                // --- LÓGICA PARA PRODUCTO ---
                if (Product.getAll().isEmpty()) { System.out.println("No hay productos."); continue; }
                Product.listarDesdeConsola();
                System.out.print("Ingrese ID del Producto: ");
                String prodId = scanner.nextLine();

                for (Product p : Product.getAll()) {
                    if (p.getProductId().equals(prodId)) {
                        System.out.print("Cantidad: ");
                        int cant = Integer.parseInt(scanner.nextLine());

                        // NO preguntamos precio, lo tomamos directamente del objeto Product
                        double precio = p.getPrice();

                        detalle = new OrderDetail("D" + contadorDetalles++, p.getName(), cant, p.getDescription(), precio);
                        detalle.setProduct(p);
                        break;
                    }
                }
            } else if (opcion == 2) {
                // --- LÓGICA PARA SERVICIO ---
                if (Service.getAll().isEmpty()) { System.out.println("No hay servicios."); continue; }
                Service.listarDesdeConsola();
                System.out.print("Ingrese ID del Servicio: ");
                String servId = scanner.nextLine();

                for (Service s : Service.getAll()) {
                    if (s.getServiceId().equals(servId)) {
                        System.out.print("Cantidad (Horas/Veces): ");
                        int cant = Integer.parseInt(scanner.nextLine());

                        // AQUI SÍ PREGUNTAMOS EL PRECIO UNITARIO
                        System.out.print("Precio Unitario a cobrar: $");
                        double precio = Double.parseDouble(scanner.nextLine());

                        detalle = new OrderDetail("D" + contadorDetalles++, s.getName(), cant, s.getDescription(), precio);
                        detalle.setService(s);
                        break;
                    }
                }
            }

            if (detalle != null) {
                nuevaOrden.details.add(detalle);
                System.out.println("Item agregado. Subtotal: $" + detalle.calculateSubtotal());
            } else {
                System.out.println("Item no valido.");
            }
        }

        nuevaOrden.calcularTotales();
        nuevaOrden.create();

        System.out.println("\n¡Orden " + nuevaOrden.orderId + " creada exitosamente!");
        System.out.println("Total a pagar: $" + nuevaOrden.total);
    }

    public void calcularTotales() {
        this.total = 0;
        for (OrderDetail d : this.details) {
            this.total += d.calculateSubtotal();
        }
        if (this.payments.isEmpty()) {
            this.balance = this.total;
        }
    }

    public static void listarDesdeConsola() {
        System.out.println("\n--- HISTORIAL DE ORDENES ---");
        if (listaOrdenes.isEmpty()) {
            System.out.println("No hay ordenes registradas.");
        } else {
            for (WorkOrder wo : listaOrdenes) {
                System.out.println("- [" + wo.orderId + "] Fecha: " + wo.date.toString() +
                                   " | Cliente: " + wo.customer.getName() +
                                   " | Estado: " + wo.status +
                                   " | Total: $" + wo.total +
                                   " | Saldo: $" + wo.balance);
            }
        }
    }

    public String getOrderId() { return orderId; }
    public double getTotal() { return total; }
    public double getBalance() { return balance; }
    public OrderStatus getStatus() { return status; }
    public Customer getCustomer() { return customer; }
    public List<OrderDetail> getDetails() { return details; }


    public void changeStatus(OrderStatus newStatus) {
    }

    public double calculateTotal() {
        return 0.0;
    }

    public double calculateBalance() {
        return 0.0;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
