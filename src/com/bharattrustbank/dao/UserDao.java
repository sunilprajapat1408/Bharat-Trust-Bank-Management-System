package com.bharattrustbank.dao;

import com.bharattrustbank.model.User;
import com.bharattrustbank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;

public class UserDao {
    
    public int createUserPersonalDetails(User user) throws SQLException {
        String sql =
                "INSERT INTO users (form_no, full_name, father_name, dob, gender, email, marital_status, address, city, pincode, state) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, trim(user.getFormNumber()));
            ps.setString(2, trim(user.getName()));
            ps.setString(3, trim(user.getFatherName()));

            if (user.getDateOfBirth() != null) {
                LocalDate localDate = user.getDateOfBirth().toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();
                ps.setDate(4, java.sql.Date.valueOf(localDate));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, trim(user.getGender()));
            ps.setString(6, trim(user.getEmail()));
            ps.setString(7, trim(user.getMaritalStatus()));
            ps.setString(8, trim(user.getAddress()));
            ps.setString(9, trim(user.getCity()));
            ps.setString(10, trim(user.getPinCode()));
            ps.setString(11, trim(user.getState()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to create user (no generated key)");
    }
    
    public void updateUserAdditionalDetails(String formNo, User user) throws SQLException {
        String sql =
                "UPDATE users SET religion=?, category=?, income=?, education=?, occupation=?, pan=?, aadhar=?, senior_citizen=?, existing_account=? " +
                "WHERE form_no=?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trim(user.getReligion()));
            ps.setString(2, trim(user.getCategory()));
            ps.setString(3, trim(user.getIncome()));
            ps.setString(4, trim(user.getEducation()));
            ps.setString(5, trim(user.getOccupation()));
            ps.setString(6, trim(user.getPanNumber()));
            ps.setString(7, trim(user.getAadharNumber()));
            ps.setString(8, trim(user.getSeniorCitizen()));
            ps.setString(9, trim(user.getExistingAccount()));
            ps.setString(10, trim(formNo));
            if (ps.executeUpdate() == 0) {
                throw new SQLException("User not found for form_no=" + formNo);
            }
        }
    }
    
    public void setHashedPinForUser(String formNo, String hashedPin) throws SQLException {
        String sql = "UPDATE users SET hashed_pin=? WHERE form_no=?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trim(hashedPin));
            ps.setString(2, trim(formNo));
            if (ps.executeUpdate() == 0) {
                throw new SQLException("User not found for form_no=" + formNo);
            }
        }
    }

    public Integer getUserIdByFormNo(String formNo) throws SQLException {
        String sql = "SELECT user_id FROM users WHERE form_no=?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trim(formNo));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        }
        return null;
    }
    
    public String getHashedPinByCardNumber(String cardNumber) throws SQLException {
        String query =
                "SELECT u.hashed_pin " +
                "FROM accounts a JOIN users u ON a.user_id = u.user_id " +
                "WHERE a.card_number = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, trim(cardNumber));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("hashed_pin");
            }
        }
        return null;
    }
    
    public String getCardNumberByPin(String pin) throws SQLException {
        String query =
                "SELECT a.card_number " +
                "FROM accounts a JOIN users u ON a.user_id = u.user_id " +
                "WHERE u.hashed_pin = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, trim(pin));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("card_number");
            }
        }
        return null;
    }
    
    public void updatePin(String oldPin, String newHashedPin) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();

        String sql = "UPDATE users SET hashed_pin=? WHERE hashed_pin=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trim(newHashedPin));
            ps.setString(2, trim(oldPin));
            if (ps.executeUpdate() == 0) {
                throw new SQLException("PIN update failed (current PIN not found)");
            }
        }
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }
}

