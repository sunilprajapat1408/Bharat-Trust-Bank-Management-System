package com.bharattrustbank.service;

import com.bharattrustbank.dao.TransactionDao;
import com.bharattrustbank.dao.AccountDao;
import com.bharattrustbank.exception.AccountInactiveException;
import com.bharattrustbank.exception.AccountNotFoundException;
import com.bharattrustbank.exception.InsufficientBalanceException;
import com.bharattrustbank.model.Transaction;
import com.bharattrustbank.model.TransactionType;
import com.bharattrustbank.util.AuditLogger;
import com.bharattrustbank.util.DBConnection;
import com.bharattrustbank.util.InputValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TransactionService {
    private TransactionDao transactionDao;
    private AccountService accountService;
    private AccountDao accountDao;
    private static final double MAX_WITHDRAWAL = 10000.0;
    
    public TransactionService() {
        this.transactionDao = new TransactionDao();
        this.accountService = new AccountService();
        this.accountDao = new AccountDao();
    }
    
    public void deposit(String pin, double amount) throws AccountNotFoundException, AccountInactiveException {
        accountService.validateAccountActive(pin);
        
        if (!InputValidator.isPositiveNumber(String.valueOf(amount))) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        
        try {
            Integer accountId = accountDao.getAccountIdByHashedPin(pin);
            if (accountId == null) {
                throw new AccountNotFoundException("Account not found");
            }
            transactionDao.createTransaction(accountId, TransactionType.DEPOSIT, amount);
            AuditLogger.logTransaction(pin, TransactionType.DEPOSIT.getValue(), amount);
        } catch (SQLException e) {
            throw new AccountNotFoundException("Failed to process deposit");
        }
    }
    
    public void withdraw(String pin, double amount) throws InsufficientBalanceException, AccountNotFoundException, AccountInactiveException {
        accountService.validateAccountActive(pin);
        
        if (!InputValidator.isPositiveNumber(String.valueOf(amount))) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        
        if (amount > MAX_WITHDRAWAL) {
            throw new IllegalArgumentException("Maximum withdrawal limit is Rs. " + MAX_WITHDRAWAL);
        }
        
        try {
            double currentBalance = getBalance(pin);
            
            if (currentBalance < amount) {
                throw new InsufficientBalanceException("Insufficient Balance");
            }

            Integer accountId = accountDao.getAccountIdByHashedPin(pin);
            if (accountId == null) {
                throw new AccountNotFoundException("Account not found");
            }
            transactionDao.createTransaction(accountId, TransactionType.WITHDRAWAL, amount);
            AuditLogger.logTransaction(pin, TransactionType.WITHDRAWAL.getValue(), amount);
        } catch (SQLException e) {
            throw new AccountNotFoundException("Failed to process withdrawal");
        }
    }
    
    public double getBalance(String pin) throws AccountNotFoundException {
        try {
            return transactionDao.calculateBalanceByHashedPin(pin);
        } catch (SQLException e) {
            throw new AccountNotFoundException("Failed to retrieve balance");
        }
    }
    
    public List<Transaction> getTransactionHistory(String pin) throws AccountNotFoundException {
        try {
            return transactionDao.getTransactionsByHashedPin(pin);
        } catch (SQLException e) {
            throw new AccountNotFoundException("Failed to retrieve transaction history");
        }
    }
    
    public void transferFunds(String sourcePin, String targetPin, double amount) 
            throws InsufficientBalanceException, AccountNotFoundException, AccountInactiveException {
        accountService.validateAccountActive(sourcePin);
        accountService.validateAccountActive(targetPin);
        
        if (!InputValidator.isPositiveNumber(String.valueOf(amount))) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            double sourceBalance = getBalance(sourcePin);
            if (sourceBalance < amount) {
                throw new InsufficientBalanceException("Insufficient Balance");
            }
            
            if (!accountService.accountExists(targetPin)) {
                throw new AccountNotFoundException("Target account not found");
            }

            Integer sourceAccountId = accountDao.getAccountIdByHashedPin(sourcePin);
            Integer targetAccountId = accountDao.getAccountIdByHashedPin(targetPin);
            if (sourceAccountId == null || targetAccountId == null) {
                throw new AccountNotFoundException("Account not found");
            }

            // store as Withdrawal for source and Deposit for target (atomic)
            transactionDao.createTransaction(sourceAccountId, TransactionType.WITHDRAWAL, amount, conn);
            transactionDao.createTransaction(targetAccountId, TransactionType.DEPOSIT, amount, conn);
            
            conn.commit();
            AuditLogger.logFundTransfer(sourcePin, targetPin, amount);
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Failed to rollback transaction: " + rollbackEx.getMessage());
                }
            }
            throw new AccountNotFoundException("Failed to transfer funds: " + e.getMessage());
        } catch (InsufficientBalanceException | AccountNotFoundException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Failed to rollback transaction: " + rollbackEx.getMessage());
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Failed to reset auto-commit: " + e.getMessage());
                }
            }
        }
    }
}
