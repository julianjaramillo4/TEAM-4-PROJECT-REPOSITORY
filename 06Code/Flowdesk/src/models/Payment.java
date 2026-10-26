
package models;

import enums.PaymentMethod;
import utils.Date;

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
