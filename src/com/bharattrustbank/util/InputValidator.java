package com.bharattrustbank.util;

public class InputValidator {
    
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    public static boolean isNumeric(String value) {
        if (isEmpty(value)) {
            return false;
        }
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isPositiveNumber(String value) {
        if (!isNumeric(value)) {
            return false;
        }
        try {
            double num = Double.parseDouble(value);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidInteger(String value) {
        if (isEmpty(value)) {
            return false;
        }
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidCardNumber(String cardNumber) {
        if (isEmpty(cardNumber)) {
            return false;
        }
        String cleaned = cardNumber.replaceAll("[^0-9]", "");
        return cleaned.length() == 16;
    }
    
    public static boolean isValidPIN(String pin) {
        if (isEmpty(pin)) {
            return false;
        }
        return pin.length() == 4 && isValidInteger(pin);
    }
    
    public static void validateNotEmpty(String value, String fieldName) {
        if (isEmpty(value)) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }
    
    public static void validatePositiveAmount(String amount) {
        if (!isPositiveNumber(amount)) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
    }
}

