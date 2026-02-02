package com.bharattrustbank.model;

public class Account {
    private String pin;
    private String cardNumber;
    private String accountType;
    private double balance;
    private AccountStatus status;
    
    public Account() {
    }
    
    public Account(String pin, String cardNumber, String accountType) {
        this.pin = pin;
        this.cardNumber = cardNumber;
        this.accountType = accountType;
        this.balance = 0.0;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getAccountType() {
        return accountType;
    }
    
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public AccountStatus getStatus() {
        return status != null ? status : AccountStatus.ACTIVE;
    }
    
    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}

