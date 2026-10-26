package models;

import enums.MovementType;
import enums.PaymentMethod;
import utils.Date;
import java.util.Scanner;

public class Payment {
    private String paymentId;
    private double amount;
    private Date date;
    private PaymentMethod paymentMethod;
    private WorkOrder workOrder;

    public Payment(String paymentId, double amount, Date date, PaymentMethod paymentMethod, WorkOrder workOrder) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.workOrder = workOrder;
    }

    public void registerPayment() {
        workOrder.addPayment(this);
    }

    public static void registrarDesdeConsola(Scanner scanner, CashRegister caja) {
        System.out.println("\n--- REGISTRAR PAGO ---");
        
        System.out.print("Ingrese el numero de orden a pagar (ej. WO-001): ");
        String idOrden = scanner.nextLine();
        
        WorkOrder orden = null;
        for (WorkOrder wo : WorkOrder.getAll()) {
            if (wo.getOrderId().equalsIgnoreCase(idOrden)) {
                orden = wo;
                break;
            }
        }

        if (orden == null) {
            System.out.println("Error: Orden no encontrada.");
            return;
        }

        if (orden.getBalance() <= 0) {
            System.out.println("Esta orden ya esta totalmente pagada.");
            return;
        }

        System.out.println("Cliente: " + orden.getCustomer().getName());
        System.out.println("Total de la Orden: $" + orden.getTotal());
        System.out.println("Saldo Pendiente: $" + orden.getBalance());

        System.out.print("\nMonto a pagar ahora: $");
        double monto = Double.parseDouble(scanner.nextLine());

        if (monto <= 0) {
            System.out.println("El monto debe ser mayor a 0.");
            return;
        }

        if (monto > orden.getBalance()) {
            System.out.println("Error: El monto excede el saldo pendiente ($" + orden.getBalance() + ").");
            return;
        }

        System.out.print("Metodo de pago (1. EFECTIVO / 2. TRANSFERENCIA): ");
        int metOpcion = Integer.parseInt(scanner.nextLine());
        PaymentMethod metodo = (metOpcion == 1) ? PaymentMethod.CASH : PaymentMethod.TRANSFER;

        String nuevoIdPago = "P-" + System.currentTimeMillis() % 10000;
        
        Payment nuevoPago = new Payment(nuevoIdPago, monto, new Date(24, 10, 2026), metodo, orden);
        nuevoPago.registerPayment();

        String idMovimiento = "MOV-" + nuevoIdPago;
        CashMovement movimientoCaja = new CashMovement(
            idMovimiento, 
            MovementType.INCOME, 
            monto, 
            new Date(24, 10, 2026), 
            "Pago Orden " + orden.getOrderId(), 
            metodo
        );
        movimientoCaja.registerMovement(caja); 

        System.out.println("\n¡Pago registrado exitosamente!");
        System.out.println("Se ha generado un ingreso automatico en la caja.");
        System.out.println("Nuevo saldo pendiente de la orden: $" + orden.getBalance());
        
        if (orden.getBalance() == 0) {
            System.out.println("*** ¡LA ORDEN HA SIDO TOTALMENTE PAGADA! ***");
        }
    }

    public String getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public Date getDate() { return date; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public WorkOrder getWorkOrder() { return workOrder; }
}