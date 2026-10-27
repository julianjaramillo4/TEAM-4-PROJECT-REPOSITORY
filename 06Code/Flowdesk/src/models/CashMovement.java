package models;

import enums.MovementType;
import enums.PaymentMethod;
import utils.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.ConsoleInput;

public class CashMovement {
    private String movementId;
    private MovementType type;
    private double amount;
    private Date date;
    private String reason;
    private PaymentMethod paymentMethod;

    private static List<CashMovement> movements = new ArrayList<>();

    public CashMovement(String movementId, MovementType type, double amount, Date date, String reason, PaymentMethod paymentMethod) {
        this.movementId = movementId;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.reason = reason;
        this.paymentMethod = paymentMethod;
    }

    public void registerMovement(CashRegister cashRegister) {
        movements.add(this);

        if (this.type == MovementType.INCOME) {
            cashRegister.addIncome(this.amount);
        } else {
            cashRegister.addExpense(this.amount);
        }
    }

    public static void registerFromConsole(Scanner scanner, CashRegister cashRegister) {
        System.out.println("\n--- REGISTRAR MOVIMIENTO DE CAJA ---");

        String id = ConsoleInput.readRequiredText(scanner, "ID del Movimiento: ");

        int typeOption = ConsoleInput.readOption(scanner, "Tipo (1. INGRESO / 2. EGRESO): ", 1, 2);
        MovementType type = (typeOption == 1) ? MovementType.INCOME : MovementType.EXPENSE;

        double amount = ConsoleInput.readPositiveAmount(scanner, "Monto: $");

        String reason = ConsoleInput.readRequiredText(scanner, "Razon/Descripcion: ");

        int methodOption = ConsoleInput.readOption(scanner, "Metodo de pago (1. EFECTIVO / 2. TRANSFERENCIA): ", 1, 2);
        PaymentMethod method = (methodOption == 1) ? PaymentMethod.CASH : PaymentMethod.TRANSFER;

        Date fechaActual = new Date(24, 10, 2026);

        CashMovement newMovement = new CashMovement(id, type, amount, fechaActual, reason, method);
        newMovement.registerMovement(cashRegister);

        System.out.println("¡Movimiento registrado exitosamente!");
    }

    public static void listFromConsole() {
        System.out.println("\n--- HISTORIAL DE MOVIMIENTOS ---");
        if (movements.isEmpty()) {
            System.out.println("No hay movimientos registrados.");
        } else {
            for (CashMovement m : movements) {
                String sign = (m.type == MovementType.INCOME) ? "+" : "-";
                System.out.println("- [" + m.movementId + "] " + m.date.toString() +
                                   " | " + m.type + " | " + sign + "$" + m.amount +
                                   " | " + m.reason + " (" + m.paymentMethod + ")");
            }
        }
    }
}
