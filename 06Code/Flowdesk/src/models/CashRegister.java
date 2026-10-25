package models;

public class CashRegister {
    private double income;
    private double expenses;

    public double calculateIncome() {
        return 0.0;
    }

    public double calculateExpenses() {
        return 0.0;
    }

    public double calculateBalance() {
        return 0.0;
    }

    /**
     * @return the income
     */
    public double getIncome() {
        return income;
    }

    /**
     * @param income the income to set
     */
    public void setIncome(double income) {
        this.income = income;
    }

    /**
     * @return the expenses
     */
    public double getExpenses() {
        return expenses;
    }

    /**
     * @param expenses the expenses to set
     */
    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }
}
