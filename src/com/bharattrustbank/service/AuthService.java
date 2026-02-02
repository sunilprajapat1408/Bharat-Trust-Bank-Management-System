package com.bharattrustbank.service;

import com.bharattrustbank.dao.UserDao;
import com.bharattrustbank.exception.InvalidCredentialsException;
import com.bharattrustbank.util.AuditLogger;
import com.bharattrustbank.util.InputValidator;
import com.bharattrustbank.util.PasswordHasher;

import java.sql.SQLException;

public class AuthService {
    private UserDao userDao;
    
    public AuthService() {
        this.userDao = new UserDao();
    }
    
    public String authenticate(String cardNumber, String pin) throws InvalidCredentialsException {
        if (!InputValidator.isValidCardNumber(cardNumber)) {
            AuditLogger.logLogin(cardNumber, false);
            throw new InvalidCredentialsException("Invalid card number format");
        }
        
        if (!InputValidator.isValidPIN(pin)) {
            AuditLogger.logLogin(cardNumber, false);
            throw new InvalidCredentialsException("Invalid PIN format");
        }
        
        try {
            String hashedPin = userDao.getHashedPinByCardNumber(cardNumber);
            if (hashedPin == null) {
                AuditLogger.logLogin(cardNumber, false);
                throw new InvalidCredentialsException("Card number not found");
            }
            
            if (PasswordHasher.verify(pin, hashedPin)) {
                AuditLogger.logLogin(cardNumber, true);
                return hashedPin;
            } else {
                AuditLogger.logLogin(cardNumber, false);
                throw new InvalidCredentialsException("Incorrect Card Number or PIN");
            }
        } catch (SQLException e) {
            AuditLogger.logLogin(cardNumber, false);
            throw new InvalidCredentialsException("Database error during authentication");
        }
    }
    
    public void changePin(String currentPin, String newPin, String confirmPin) throws InvalidCredentialsException {
        if (!InputValidator.isValidPIN(newPin)) {
            throw new InvalidCredentialsException("New PIN must be 4 digits");
        }
        
        if (!newPin.equals(confirmPin)) {
            throw new InvalidCredentialsException("Entered PIN does not match");
        }
        
        try {
            String hashedNewPin = PasswordHasher.hash(newPin);
            userDao.updatePin(currentPin, hashedNewPin);
            AuditLogger.logAccountOperation(currentPin, "PIN_CHANGE");
        } catch (SQLException e) {
            throw new InvalidCredentialsException("Failed to change PIN");
        }
    }

    public String changePinAndReturnNewHash(String currentPin, String newPin, String confirmPin) throws InvalidCredentialsException {
        changePin(currentPin, newPin, confirmPin);
        return PasswordHasher.hash(newPin);
    }
}

