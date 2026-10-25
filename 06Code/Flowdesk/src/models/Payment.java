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

    public Payment(String paymentId, double amount, Date date, PaymentMethod paymentMethod) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
    }

    public void registerPayment() {
    }

    public boolean validateAmount() {
        return false;
    }

    public String getPaymentDetails() {
        return "";
    }

    /**
     * @return the paymentId
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * @param paymentId the paymentId to set
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the paymentMethod
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @return the workOrder
     */
    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    /**
     * @param workOrder the workOrder to set
     */
    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    /**
     * @return the cashMovement
     */
    public CashMovement getCashMovement() {
        return cashMovement;
    }

    /**
     * @param cashMovement the cashMovement to set
     */
    public void setCashMovement(CashMovement cashMovement) {
        this.cashMovement = cashMovement;
    }
}
