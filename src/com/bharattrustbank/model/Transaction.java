package com.bharattrustbank.model;

import java.util.Date;

public class Transaction {
    private String pin;
    private Date date;
    private TransactionType type;
    private double amount;
    
    public Transaction() {
    }
    
    public Transaction(String pin, Date date, TransactionType type, double amount) {
        this.pin = pin;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public String getTypeString() {
        return type != null ? type.getValue() : "";
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
}

