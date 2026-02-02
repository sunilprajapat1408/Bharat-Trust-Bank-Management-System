package com.bharattrustbank.dao;

import com.bharattrustbank.model.Transaction;
import com.bharattrustbank.model.TransactionType;
import com.bharattrustbank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionDao {
    
    public void createTransaction(int accountId, TransactionType type, double amount) throws SQLException {
        createTransaction(accountId, type, amount, null);
    }

    public void createTransaction(int accountId, TransactionType type, double amount, Connection conn) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, transaction_type, amount) VALUES (?, ?, ?)";
        boolean useExternalConnection = (conn != null);
        if (!useExternalConnection) {
            conn = DBConnection.getInstance().getConnection();
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.setString(2, type.getValue());
            ps.setDouble(3, amount);
            ps.executeUpdate();
        }
    }

    public List<Transaction> getTransactionsByHashedPin(String hashedPin) throws SQLException {
        String sql =
                "SELECT u.hashed_pin, t.transaction_time, t.transaction_type, t.amount " +
                "FROM transactions t " +
                "JOIN accounts a ON t.account_id = a.account_id " +
                "JOIN users u ON a.user_id = u.user_id " +
                "WHERE u.hashed_pin = ? " +
                "ORDER BY t.transaction_time DESC";
        Connection conn = DBConnection.getInstance().getConnection();
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashedPin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction tx = new Transaction();
                    tx.setPin(rs.getString("hashed_pin"));
                    tx.setDate(new Date(rs.getTimestamp("transaction_time").getTime()));
                    tx.setType(TransactionType.fromString(rs.getString("transaction_type")));
                    tx.setAmount(rs.getDouble("amount"));
                    transactions.add(tx);
                }
            }
        }
        return transactions;
    }

    public double calculateBalanceByHashedPin(String hashedPin) throws SQLException {
        String sql =
                "SELECT COALESCE(SUM(CASE " +
                "  WHEN t.transaction_type = 'Deposit' THEN t.amount " +
                "  WHEN t.transaction_type = 'Transfer' THEN -t.amount " +
                "  ELSE -t.amount " +
                "END), 0) AS balance " +
                "FROM transactions t " +
                "JOIN accounts a ON t.account_id = a.account_id " +
                "JOIN users u ON a.user_id = u.user_id " +
                "WHERE u.hashed_pin = ?";

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashedPin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        }
        return 0.0;
    }
}

