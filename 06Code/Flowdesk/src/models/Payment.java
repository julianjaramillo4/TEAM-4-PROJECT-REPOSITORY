
package models;

import enums.MovementType;
import enums.PaymentMethod;
import utils.Date;
import java.util.Scanner;
import utils.ConsoleInput;

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
            throw new IllegalStateException("Se requiere una orden de trabajo");
        }

        if (!validateAmount()) {
            throw new IllegalArgumentException("El monto del pago no es valido");
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
        return "Identificador del pago: " + paymentId
                + ", Monto: " + amount
                + ", Metodo: " + paymentMethod;
    }

    public static void registerFromConsole(Scanner scanner, CashRegister cashRegister) {
        System.out.println("\n--- REGISTRAR PAGO ---");

        String orderId = ConsoleInput.readRequiredText(scanner, "Ingrese el numero de orden a pagar (ej. WO-001): ");

        WorkOrder order = null;

        for (WorkOrder wo : WorkOrder.getAll()) {
            if (wo.getOrderId().equalsIgnoreCase(orderId)) {
                order = wo;
                break;
            }
        }

        if (order == null) {
            System.out.println("Error: Orden no encontrada.");
            return;
        }

        if (order.getBalance() <= 0) {
            System.out.println("Esta orden ya esta totalmente pagada.");
            return;
        }

        String customerName = (order.getCustomer() != null)
                ? order.getCustomer().getName()
                : "Sin cliente";

        System.out.println("Cliente: " + customerName);
        System.out.println("Total de la Orden: $" + order.getTotal());
        System.out.println("Saldo Pendiente: $" + order.getBalance());

        double amount = ConsoleInput.readPositiveAmount(scanner, "\nMonto a pagar ahora: $");

        if (amount > order.getBalance()) {
            System.out.println("Error: El monto excede el saldo pendiente ($"
                    + order.getBalance() + ").");
            return;
        }

        int option = ConsoleInput.readOption(scanner, "Metodo de pago (1. EFECTIVO / 2. TRANSFERENCIA): ", 1, 2);

        PaymentMethod method;

        switch (option) {
            case 1 -> method = PaymentMethod.CASH;
            case 2 -> method = PaymentMethod.TRANSFER;
            default -> {
                System.out.println("Metodo de pago no valido.");
                return;
            }
        }

        String newPaymentId = "P-" + System.currentTimeMillis();

        Payment newPayment = new Payment(
                newPaymentId,
                amount,
                new Date(24, 10, 2026),
                method,
                order
        );

        newPayment.registerPayment();

        String movementId = "MOV-" + newPaymentId;

        CashMovement cashMovement = new CashMovement(
                movementId,
                MovementType.INCOME,
                amount,
                new Date(24, 10, 2026),
                "Pago Orden " + order.getOrderId(),
                method
        );

        cashMovement.registerMovement(cashRegister);
        newPayment.setCashMovement(cashMovement);

        System.out.println("\nPago registrado exitosamente!");
        System.out.println("Se ha generado un ingreso automatico en la caja.");
        System.out.println("Nuevo saldo pendiente de la orden: $"
                + order.getBalance());

        if (order.getBalance() == 0) {
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
