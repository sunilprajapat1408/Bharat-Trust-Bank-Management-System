package com.bharattrustbank.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditLogger {
    private static final String LOG_FILE = "audit.log";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static void log(String action, String details) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = DATE_FORMAT.format(new Date());
            writer.println(String.format("[%s] %s - %s", timestamp, action, details));
        } catch (IOException e) {
            System.err.println("Failed to write audit log: " + e.getMessage());
        }
    }
    
    public static void logLogin(String cardNumber, boolean success) {
        log("LOGIN", String.format("Card: %s, Success: %s", maskCardNumber(cardNumber), success));
    }
    
    public static void logTransaction(String pin, String transactionType, double amount) {
        log("TRANSACTION", String.format("PIN: %s, Type: %s, Amount: %.2f", maskPin(pin), transactionType, amount));
    }
    
    public static void logFundTransfer(String sourcePin, String targetPin, double amount) {
        log("FUND_TRANSFER", String.format("Source: %s, Target: %s, Amount: %.2f", 
            maskPin(sourcePin), maskPin(targetPin), amount));
    }
    
    public static void logAccountOperation(String pin, String operation) {
        log("ACCOUNT_OPERATION", String.format("PIN: %s, Operation: %s", maskPin(pin), operation));
    }
    
    private static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return cardNumber.substring(0, 4) + "****" + cardNumber.substring(cardNumber.length() - 4);
    }
    
    private static String maskPin(String pin) {
        if (pin == null || pin.length() < 4) {
            return "****";
        }
        return "****";
    }
}

