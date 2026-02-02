-- Single source of truth schema aligned with the current Java Swing + JDBC codebase.
-- Notes:
-- - The app passes around the *hashed PIN* as the session identifier after login.
-- - SHA-256 hashes are 64 hex characters, so hashed_pin columns must be VARCHAR(64).
-- - Account types are stored as: SAVINGS, CURRENT, FD, RD.
-- - Transaction types are stored as: Deposit, Withdrawal, Transfer (matches TransactionType.getValue()).

CREATE DATABASE IF NOT EXISTS bharat_trust_bank;
USE bharat_trust_bank;

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    form_no VARCHAR(30) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    father_name VARCHAR(100),
    dob DATE,
    gender VARCHAR(10),
    email VARCHAR(100) UNIQUE,
    marital_status VARCHAR(30),
    address VARCHAR(200),
    city VARCHAR(50),
    pincode VARCHAR(10),
    state VARCHAR(50),

    religion VARCHAR(30),
    category VARCHAR(30),
    income VARCHAR(30),
    education VARCHAR(30),
    occupation VARCHAR(60),
    pan VARCHAR(30),
    aadhar VARCHAR(60),
    senior_citizen VARCHAR(10),
    existing_account VARCHAR(10),

    hashed_pin VARCHAR(64) UNIQUE,
    role ENUM('ADMIN','CUSTOMER') DEFAULT 'CUSTOMER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    card_number VARCHAR(20) UNIQUE NOT NULL,
    account_type ENUM('SAVINGS','CURRENT','FD','RD') NOT NULL,
    balance DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    status ENUM('ACTIVE','INACTIVE','CLOSED') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_accounts_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    transaction_type ENUM('Deposit','Withdrawal','Transfer') NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remarks VARCHAR(255),
    CONSTRAINT fk_transactions_account FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

CREATE TABLE IF NOT EXISTS account_services (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    service_name VARCHAR(50) NOT NULL,
    CONSTRAINT fk_services_account FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

CREATE INDEX idx_accounts_card_number ON accounts(card_number);
CREATE INDEX idx_accounts_user_id ON accounts(user_id);
CREATE INDEX idx_transactions_account_id ON transactions(account_id);


