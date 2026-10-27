package models;

import utils.Validation;

public class CashRegister {
    private double income;
    private double expenses;

    public CashRegister() {
        this.income = 0;
        this.expenses = 0;
    }

    public CashRegister(double income, double expenses) {
        Validation.requireNonNegativeAmount(income, "ingresos");
        Validation.requireNonNegativeAmount(expenses, "egresos");
        this.income = income;
        this.expenses = expenses;
    }

    public void addIncome(double amount) {
        Validation.requirePositiveAmount(amount, "monto");
        Validation.requireNonNegativeAmount(income + amount, "ingresos acumulados");
        this.income += amount;
    }

    public void addExpense(double amount) {
        Validation.requirePositiveAmount(amount, "monto");
        Validation.requireNonNegativeAmount(expenses + amount, "egresos acumulados");
        this.expenses += amount;
    }

    public double calculateIncome() {
        return income;
    }

    public double calculateExpenses() {
        return expenses;
    }

    public double calculateBalance() {
        return income - expenses;
    }

    public void printStatus() {
        System.out.println("\n--- ESTADO DE CAJA ---");
        System.out.println("Ingresos Totales: $" + calculateIncome());
        System.out.println("Egresos Totales:  $" + calculateExpenses());
        System.out.println("Balance Actual:   $" + calculateBalance());
        System.out.println("----------------------");
    }

    public double getIncome() { return income; }
    public void setIncome(double income) {
        Validation.requireNonNegativeAmount(income, "ingresos");
        this.income = income;
    }

    public double getExpenses() { return expenses; }
    public void setExpenses(double expenses) {
        Validation.requireNonNegativeAmount(expenses, "egresos");
        this.expenses = expenses;
    }
}
