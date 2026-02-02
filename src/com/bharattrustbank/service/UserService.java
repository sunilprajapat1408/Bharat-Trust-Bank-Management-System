package com.bharattrustbank.service;

import com.bharattrustbank.dao.AccountDao;
import com.bharattrustbank.dao.UserDao;
import com.bharattrustbank.model.User;
import com.bharattrustbank.util.PasswordHasher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserService {
    private UserDao userDao;
    private AccountDao accountDao;
    
    public UserService() {
        this.userDao = new UserDao();
        this.accountDao = new AccountDao();
    }
    
    public void registerUserPersonalDetails(User user) throws SQLException {
        userDao.createUserPersonalDetails(user);
    }
    
    public void registerUserAdditionalDetails(User user) throws SQLException {
        userDao.updateUserAdditionalDetails(user.getFormNumber(), user);
    }
    
    public User registerUserAccountDetails(User user) throws SQLException {
        Random ran = new Random();
        long first7 = (ran.nextLong() % 90000000L) + 1409963000000000L;
        String cardNumber = "" + Math.abs(first7);

        long acct = (ran.nextLong() % 9000000000L) + 1000000000L;
        String accountNumber = "" + Math.abs(acct);
        
        long first3 = (ran.nextLong() % 9000L) + 1000L;
        String pin = "" + Math.abs(first3);
        String hashedPin = PasswordHasher.hash(pin);

        String accountTypeEnum = mapAccountTypeToEnum(user.getAccountType());

        Integer userId = userDao.getUserIdByFormNo(user.getFormNumber());
        if (userId == null) {
            throw new SQLException("User not found for form_no=" + user.getFormNumber());
        }

        int accountId = accountDao.createAccount(userId, accountNumber, cardNumber, accountTypeEnum);

        userDao.setHashedPinForUser(user.getFormNumber(), hashedPin);

        // services
        List<String> services = parseFacilities(user.getFacilities());
        if (!services.isEmpty()) {
            // keep it minimal: insert each selected service row
            String sql = "INSERT INTO account_services (account_id, service_name) VALUES (?, ?)";
            java.sql.Connection conn = com.bharattrustbank.util.DBConnection.getInstance().getConnection();
            try (java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
                for (String s : services) {
                    ps.setInt(1, accountId);
                    ps.setString(2, s);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }

        user.setCardNumber(cardNumber);
        user.setPin(pin); // return plain pin for UI display only
        return user;
    }

    private static String mapAccountTypeToEnum(String uiValue) {
        if (uiValue == null) return "SAVINGS";
        String v = uiValue.trim().toLowerCase();
        if (v.contains("saving")) return "SAVINGS";
        if (v.contains("current")) return "CURRENT";
        if (v.contains("fixed")) return "FD";
        if (v.contains("recurring")) return "RD";
        return "SAVINGS";
    }

    private static List<String> parseFacilities(String facilities) {
        List<String> out = new ArrayList<>();
        if (facilities == null) return out;
        String trimmed = facilities.trim();
        if (trimmed.isEmpty()) return out;

        // original UI builds a single string with trailing spaces. Split on 2+ spaces or by known tokens.
        String[] parts = trimmed.split("\\s{2,}|\\s(?=[A-Z])");
        for (String p : parts) {
            String s = p.trim();
            if (!s.isEmpty()) out.add(s);
        }
        return out;
    }
}

