
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
    
    public utils.Date getDate() { return date; }
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
    
    public WorkOrder() { } 

    public static void setAll(List<WorkOrder> nuevaLista) {
        listaOrdenes = nuevaLista;
        if (nuevaLista != null && !nuevaLista.isEmpty()) {
            contadorOrdenes = nuevaLista.size() + 1;
        }
    }    

    public void create() {
        listaOrdenes.add(this);
    }

    public static List<WorkOrder> getAll() {
        return listaOrdenes;
    }

    public void addPayment(Payment pago) {
        this.payments.add(pago);
        this.balance = this.total - calcularTotalPagado();
    }

    public double calcularTotalPagado() {
        double pagado = 0;
        for (Payment p : payments) {
            pagado += p.getAmount();
        }
        return pagado;
    }

    public static void setAll(List<WorkOrder> nuevaLista) {
        listaOrdenes = nuevaLista == null
                ? new ArrayList<>()
                : nuevaLista;
        contadorOrdenes = listaOrdenes.size() + 1;
    }

    public void addPayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }

        if (payment.getWorkOrder() != this) {
            throw new IllegalArgumentException(
                    "Payment belongs to another order"
            );
        }

        double amount = payment.getAmount();

        if (!Double.isFinite(amount)
                || amount <= 0
                || amount > balance) {
            throw new IllegalArgumentException("Invalid payment amount");
        }

        payments.add(payment);
        calcularTotales();
    }

    public double calcularTotalPagado() {
        double totalPagado = 0;

        for (Payment payment : payments) {
            totalPagado += payment.getAmount();
        }

        return totalPagado;
    }

    public void calcularTotales() {
        this.total = 0;

        for (OrderDetail detail : details) {
            this.total += detail.calculateSubtotal();
        }

        this.balance = this.total - calcularTotalPagado();
    }

    public static void registrarDesdeConsola(
            Scanner scanner, User empleado) {

        System.out.println("\n--- CREAR NUEVA ORDEN DE TRABAJO ---");

        String nuevoId = String.format(
                "WO-%03d", contadorOrdenes++
        );

        System.out.println("Numero de Orden generado: " + nuevoId);

        System.out.print("Ingrese ID o Cedula del Cliente: ");
        String criterio = scanner.nextLine();

        Customer cliente = Customer.search(criterio);

        if (cliente == null) {
            System.out.println("Error: Cliente no encontrado.");
            return;
        }

        System.out.println("Cliente asignado: " + cliente.getName());

        WorkOrder nuevaOrden = new WorkOrder(
                nuevoId,
                new Date(24, 10, 2026),
                OrderStatus.WAITING
        );

        nuevaOrden.customer = cliente;
        nuevaOrden.user = empleado;

        int contadorDetalles = 1;

        while (true) {
            System.out.println("\n1. Agregar Producto");
            System.out.println("2. Agregar Servicio");
            System.out.println("0. Terminar y guardar Orden");
            System.out.print("Opcion: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion == 0) {
                break;
            }

            OrderDetail detalle = null;

            if (opcion == 1) {

                if (Product.getAll().isEmpty()) {
                    System.out.println("No hay productos.");
                    continue;
                }

                Product.listarDesdeConsola();

                System.out.print("Ingrese ID del Producto: ");
                String prodId = scanner.nextLine();

                for (Product p : Product.getAll()) {

                    if (p.getProductId().equals(prodId)) {

                        System.out.print("Cantidad: ");
                        int cant = Integer.parseInt(scanner.nextLine());
                        double precio = p.getPrice(); 
                        
                        detalle = new OrderDetail("D" + contadorDetalles++, p.getName(), cant, p.getDescription(), precio);
                        int cant = Integer.parseInt(
                                scanner.nextLine()
                        );

                        double precio = p.getPrice();

                        detalle = new OrderDetail(
                                "D" + contadorDetalles++,
                                p.getName(),
                                cant,
                                p.getDescription(),
                                precio
                        );

                        detalle.setProduct(p);
                        break;
                    }
                }

            } else if (opcion == 2) {

                if (Service.getAll().isEmpty()) {
                    System.out.println("No hay servicios.");
                    continue;
                }

                Service.listarDesdeConsola();

                System.out.print("Ingrese ID del Servicio: ");
                String servId = scanner.nextLine();

                for (Service s : Service.getAll()) {

                    if (s.getServiceId().equals(servId)) {

                        System.out.print("Cantidad (Horas/Veces): ");
                        int cant = Integer.parseInt(scanner.nextLine());
                        System.out.print("Precio Unitario a cobrar: $"); 
                        double precio = Double.parseDouble(scanner.nextLine());
                        
                        detalle = new OrderDetail("D" + contadorDetalles++, s.getName(), cant, s.getDescription(), precio);
                        int cant = Integer.parseInt(
                                scanner.nextLine()
                        );

                        System.out.print("Precio Unitario a cobrar: $");
                        double precio = Double.parseDouble(
                                scanner.nextLine()
                        );

                        detalle = new OrderDetail(
                                "D" + contadorDetalles++,
                                s.getName(),
                                cant,
                                s.getDescription(),
                                precio
                        );

                        detalle.setService(s);
                        break;
                    }
                }
            }

            if (detalle != null) {
                nuevaOrden.details.add(detalle);

                System.out.println(
                        "Item agregado. Subtotal: $"
                        + detalle.calculateSubtotal()
                );
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
        this.balance = this.total - calcularTotalPagado(); 

        System.out.println(
                "\nOrden " + nuevaOrden.orderId
                + " creada exitosamente!"
        );

        System.out.println(
                "Total a pagar: $" + nuevaOrden.total
        );
    }

    public static void listarDesdeConsola() {
        System.out.println("\n--- HISTORIAL DE ORDENES ---");

        if (listaOrdenes.isEmpty()) {
            System.out.println("No hay ordenes registradas.");
        } else {

            for (WorkOrder wo : listaOrdenes) {

                String nombreCliente = wo.customer == null
                        ? "Sin cliente"
                        : wo.customer.getName();

                System.out.println(
                        "- [" + wo.orderId + "]"
                        + " Fecha: " + wo.date
                        + " | Cliente: " + nombreCliente
                        + " | Estado: " + wo.status
                        + " | Total: $" + wo.total
                        + " | Saldo: $" + wo.balance
                );
            }
        }
    }


    public void changeStatus(OrderStatus nuevoEstado) {
        this.status = nuevoEstado;
    }

    public static void actualizarEstadoDesdeConsola(Scanner scanner) {

    public static void actualizarEstadoDesdeConsola(
            Scanner scanner) {

        System.out.println("\n--- ACTUALIZAR ESTADO DE ORDEN ---");

        if (listaOrdenes.isEmpty()) {
            System.out.println(
                    "No hay ordenes registradas para actualizar."
            );
            return;
        }

        System.out.print(
                "Ingrese el numero de orden (ej. WO-001): "
        );

        String idBuscar = scanner.nextLine();

        WorkOrder ordenEncontrada = null;

        for (WorkOrder wo : listaOrdenes) {

            if (wo.getOrderId().equalsIgnoreCase(idBuscar)) {
                ordenEncontrada = wo;
                break;
            }
        }

        if (ordenEncontrada == null) {
            System.out.println("Error: Orden no encontrada.");
            return;
        }

        System.out.println(
                "Orden encontrada: " + ordenEncontrada.getOrderId()
        );

        System.out.println(
                "Estado actual: " + ordenEncontrada.getStatus()
        );

        System.out.println("\nSeleccione el nuevo estado:");
        System.out.println("1. WAITING");
        System.out.println("2. APPROVED");
        System.out.println("3. IN_PROGRESS");
        System.out.println("4. COMPLETED");
        System.out.println("5. DELIVERED");
        System.out.print("Opcion: ");

        int opcion = Integer.parseInt(scanner.nextLine());

        OrderStatus nuevoEstado;

        switch (opcion) {
            case 1 -> nuevoEstado = OrderStatus.WAITING;
            case 2 -> nuevoEstado = OrderStatus.APPROVED;
            case 3 -> nuevoEstado = OrderStatus.IN_PROGRESS;
            case 4 -> nuevoEstado = OrderStatus.COMPLETED;
            case 5 -> nuevoEstado = OrderStatus.DELIVERED;
            default -> {
                System.out.println("Opcion no valida.");
                return;
            }
        }

        ordenEncontrada.changeStatus(nuevoEstado);
        System.out.println("¡Estado actualizado exitosamente a: " + ordenEncontrada.getStatus() + "!");
    }

        System.out.println(
                "Estado actualizado a: "
                + ordenEncontrada.getStatus()
        );
    }

    public String getOrderId() { return orderId; }
    public double getTotal() { return total; }
    public double getBalance() { return balance; }
    public OrderStatus getStatus() { return status; }
    public Customer getCustomer() { return customer; }
    public List<OrderDetail> getDetails() { return details; }
    public List<Payment> getPayments() { return payments; }
    public String getOrderId() {
        return orderId;
    }

    public Date getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public double getBalance() {
        return balance;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public List<Payment> getPayments() {
        return payments;
    }
}