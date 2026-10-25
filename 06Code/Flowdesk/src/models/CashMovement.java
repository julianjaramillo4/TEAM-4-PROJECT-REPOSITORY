package models;

import enums.MovementType;
import enums.PaymentMethod;
import utils.Date;

public class CashMovement {
    private String movementId;
    private MovementType type;
    private double amount;
    private Date date;
    private String reason;
    private PaymentMethod paymentMethod;
    private CashRegister cashRegister;
    private Payment payment;

    public CashMovement(String movementId, MovementType type, double amount, Date date, String reason, PaymentMethod paymentMethod) {
        this.movementId = movementId;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.reason = reason;
        this.paymentMethod = paymentMethod;
    }

    public void registerMovement() {
    }

    public CashMovement search(String id) {
        return null;
    }

    /**
     * @return the movementId
     */
    public String getMovementId() {
        return movementId;
    }

    /**
     * @param movementId the movementId to set
     */
    public void setMovementId(String movementId) {
        this.movementId = movementId;
    }

    /**
     * @return the type
     */
    public MovementType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MovementType type) {
        this.type = type;
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
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
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
     * @return the cashRegister
     */
    public CashRegister getCashRegister() {
        return cashRegister;
    }

    /**
     * @param cashRegister the cashRegister to set
     */
    public void setCashRegister(CashRegister cashRegister) {
        this.cashRegister = cashRegister;
    }

    /**
     * @return the payment
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
