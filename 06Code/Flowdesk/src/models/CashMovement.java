package models;

import enums.MovementType;
import enums.PaymentMethod;
import utils.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CashMovement {
    private String movementId;
    private MovementType type;
    private double amount;
    private Date date;
    private String reason;
    private PaymentMethod paymentMethod;

    private static List<CashMovement> listaMovimientos = new ArrayList<>();

    public CashMovement(String movementId, MovementType type, double amount, Date date, String reason, PaymentMethod paymentMethod) {
        this.movementId = movementId;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.reason = reason;
        this.paymentMethod = paymentMethod;
    }

    public void registerMovement(CashRegister caja) {
        listaMovimientos.add(this);
        
        // Actualizamos la caja según el tipo
        if (this.type == MovementType.INCOME) {
            caja.addIncome(this.amount);
        } else {
            caja.addExpense(this.amount);
        }
    }

    public static void registrarDesdeConsola(Scanner scanner, CashRegister caja) {
        System.out.println("\n--- REGISTRAR MOVIMIENTO DE CAJA ---");
        
        System.out.print("ID del Movimiento: ");
        String id = scanner.nextLine();
        
        System.out.print("Tipo (1. INGRESO / 2. EGRESO): ");
        int tipoOpcion = Integer.parseInt(scanner.nextLine());
        MovementType tipo = (tipoOpcion == 1) ? MovementType.INCOME : MovementType.EXPENSE;
        
        System.out.print("Monto: $");
        double monto = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Razon/Descripcion: ");
        String razon = scanner.nextLine();
        
        System.out.print("Metodo de pago (1. EFECTIVO / 2. TRANSFERENCIA): ");
        int metOpcion = Integer.parseInt(scanner.nextLine());
        PaymentMethod metodo = (metOpcion == 1) ? PaymentMethod.CASH : PaymentMethod.TRANSFER;


        Date fechaActual = new Date(24, 10, 2026); 

        CashMovement nuevoMov = new CashMovement(id, tipo, monto, fechaActual, razon, metodo);
        nuevoMov.registerMovement(caja); 

        System.out.println("¡Movimiento registrado exitosamente!");
    }

    public static void listarDesdeConsola() {
        System.out.println("\n--- HISTORIAL DE MOVIMIENTOS ---");
        if (listaMovimientos.isEmpty()) {
            System.out.println("No hay movimientos registrados.");
        } else {
            for (CashMovement m : listaMovimientos) {
                String signo = (m.type == MovementType.INCOME) ? "+" : "-";
                System.out.println("- [" + m.movementId + "] " + m.date.toString() + 
                                   " | " + m.type + " | " + signo + "$" + m.amount + 
                                   " | " + m.reason + " (" + m.paymentMethod + ")");
            }
        }
    }
}