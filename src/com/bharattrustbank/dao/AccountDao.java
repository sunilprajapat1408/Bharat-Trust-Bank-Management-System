package com.bharattrustbank.dao;

import com.bharattrustbank.model.Account;
import com.bharattrustbank.model.AccountStatus;
import com.bharattrustbank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDao {
    
    public Account getAccountByHashedPin(String hashedPin) throws SQLException {
        String query =
                "SELECT a.card_number, a.account_type, a.status, a.balance " +
                "FROM accounts a JOIN users u ON a.user_id = u.user_id " +
                "WHERE u.hashed_pin = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, hashedPin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setPin(hashedPin);
                account.setCardNumber(rs.getString("card_number"));
                account.setAccountType(rs.getString("account_type"));
                account.setStatus(AccountStatus.fromString(rs.getString("status")));
                account.setBalance(rs.getDouble("balance"));
                return account;
            }
        }
        return null;
    }
    
    public boolean accountExistsByHashedPin(String hashedPin) throws SQLException {
        String query = "SELECT COUNT(*) as count FROM users WHERE hashed_pin = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, hashedPin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        }
        return false;
    }

    public int createAccount(int userId, String accountNumber, String cardNumber, String accountType) throws SQLException {
        String sql =
                "INSERT INTO accounts (user_id, account_number, card_number, account_type, balance, status) " +
                "VALUES (?, ?, ?, ?, 0.00, 'ACTIVE')";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setString(2, accountNumber);
            ps.setString(3, cardNumber);
            ps.setString(4, accountType);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to create account (no generated key)");
    }

    public Integer getAccountIdByHashedPin(String hashedPin) throws SQLException {
        String sql =
                "SELECT a.account_id " +
                "FROM accounts a JOIN users u ON a.user_id = u.user_id " +
                "WHERE u.hashed_pin = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashedPin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("account_id");
                }
            }
        }
        return null;
    }
}

