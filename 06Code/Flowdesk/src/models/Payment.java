
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
    private CashMovement cashMovement;

    public Payment(String paymentId, double amount, Date date,
                   PaymentMethod paymentMethod) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
    }

    public Payment(String paymentId, double amount, Date date,
                   PaymentMethod paymentMethod, WorkOrder workOrder) {
        this(paymentId, amount, date, paymentMethod);
        this.workOrder = workOrder;
    }

    public void registerPayment() {
        if (workOrder == null) {
            throw new IllegalStateException("Work order is required");
        }

        if (!validateAmount()) {
            throw new IllegalArgumentException("Invalid payment amount");
        }

        workOrder.addPayment(this);
    }

    public boolean validateAmount() {
        return workOrder != null
                && Double.isFinite(amount)
                && amount > 0
                && amount <= workOrder.getBalance();
    }

    public String getPaymentDetails() {
        return "Payment ID: " + paymentId
                + ", Amount: " + amount
                + ", Method: " + paymentMethod;
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

        String nombreCliente = (orden.getCustomer() != null)
                ? orden.getCustomer().getName()
                : "Sin cliente";

        System.out.println("Cliente: " + nombreCliente);
        System.out.println("Total de la Orden: $" + orden.getTotal());
        System.out.println("Saldo Pendiente: $" + orden.getBalance());

        System.out.print("\nMonto a pagar ahora: $");
        double monto;

        try {
            monto = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un monto valido.");
            return;
        }

        if (!Double.isFinite(monto) || monto <= 0) {
            System.out.println("El monto debe ser mayor a 0 y finito.");
            return;
        }

        if (monto > orden.getBalance()) {
            System.out.println("Error: El monto excede el saldo pendiente ($"
                    + orden.getBalance() + ").");
            return;
        }

        System.out.print("Metodo de pago (1. EFECTIVO / 2. TRANSFERENCIA): ");
        String opcion = scanner.nextLine();

        PaymentMethod metodo;

        switch (opcion) {
            case "1" -> metodo = PaymentMethod.CASH;
            case "2" -> metodo = PaymentMethod.TRANSFER;
            default -> {
                System.out.println("Metodo de pago no valido.");
                return;
            }
        }

        String nuevoIdPago = "P-" + System.currentTimeMillis();

        Payment nuevoPago = new Payment(
                nuevoIdPago,
                monto,
                new Date(24, 10, 2026),
                metodo,
                orden
        );

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
        nuevoPago.setCashMovement(movimientoCaja);

        System.out.println("\nPago registrado exitosamente!");
        System.out.println("Se ha generado un ingreso automatico en la caja.");
        System.out.println("Nuevo saldo pendiente de la orden: $"
                + orden.getBalance());

        if (orden.getBalance() == 0) {
            System.out.println("LA ORDEN HA SIDO TOTALMENTE PAGADA!");
        }
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public CashMovement getCashMovement() {
        return cashMovement;
    }

    public void setCashMovement(CashMovement cashMovement) {
        this.cashMovement = cashMovement;
    }
}