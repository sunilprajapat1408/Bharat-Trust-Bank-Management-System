package com.bharattrustbank.service;

import com.bharattrustbank.dao.AccountDao;
import com.bharattrustbank.exception.AccountInactiveException;
import com.bharattrustbank.exception.AccountNotFoundException;
import com.bharattrustbank.model.Account;
import com.bharattrustbank.model.AccountStatus;

import java.sql.SQLException;

public class AccountService {
    private AccountDao accountDao;
    
    public AccountService() {
        this.accountDao = new AccountDao();
    }
    
    public Account getAccountByPin(String pin) throws AccountNotFoundException, AccountInactiveException {
        try {
            Account account = accountDao.getAccountByHashedPin(pin);
            if (account == null) {
                throw new AccountNotFoundException("Account not found for the given PIN");
            }
            
            if (account.getStatus() != AccountStatus.ACTIVE) {
                throw new AccountInactiveException("Account is " + account.getStatus().getValue() + ". Operations are not allowed.");
            }
            
            return account;
        } catch (SQLException e) {
            throw new AccountNotFoundException("Error retrieving account information");
        }
    }
    
    public void validateAccountActive(String pin) throws AccountNotFoundException, AccountInactiveException {
        getAccountByPin(pin);
    }
    
    public boolean accountExists(String pin) {
        try {
            return accountDao.accountExistsByHashedPin(pin);
        } catch (SQLException e) {
            return false;
        }
    }
}

