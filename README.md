# Bharat Trust Bank – Core Java Bank Management System

This is a **Core Java** desktop **Bank Management System** built with **Java Swing**, **JDBC**, and **MySQL**.  
It simulates real-world ATM-style banking operations including multi-step signup, secure login, account creation, deposits, withdrawals, fund transfers, balance enquiry, PIN change, and mini statement.

This project is intentionally implemented **without frameworks** to demonstrate strong fundamentals in Core Java, JDBC, and clean layered architecture.

---

## 🔧 Tech Stack

| Component | Technology |
|---------|------------|
| Language | Java (Core Java) |
| GUI | Java Swing |
| Database | MySQL (`bharat_trust_bank`) |
| DB Tool | MySQL Workbench |
| Persistence | JDBC (PreparedStatement) |
| Build | `javac` + `bin/` output (no Maven/Gradle) |

---

## 🏗️ Architecture Overview

The application follows a **clean layered architecture**:

### UI Layer (`com.bharattrustbank.app`)
- Java Swing frames and screens:
  `Login`, `SignupStep1/2/3`, `Main`, `Deposit`, `Withdrawal`, `FastCash`,
  `BalanceEnquiry`, `MiniStatement`, `Pin`, `BankApplication`
- Handles:
  - User input
  - Validation messages
  - Screen navigation
- Contains **no SQL and no business logic**

### Service Layer (`com.bharattrustbank.service`)
- `AuthService`: authentication, PIN change, SHA-256 hashing & verification
- `UserService`: multi-step signup and user creation
- `AccountService`: account lookup and status checks
- `TransactionService`: deposit, withdrawal, fund transfer, balance, history

### DAO Layer (`com.bharattrustbank.dao`)
- `UserDao`: CRUD on `users`, hashed PIN handling
- `AccountDao`: CRUD on `accounts`, lookup by hashed PIN
- `TransactionDao`: CRUD on `transactions`, balance calculation

### Model Layer (`com.bharattrustbank.model`)
- Entities: `User`, `Account`, `Transaction`
- Enums:
  - `TransactionType` (`Deposit`, `Withdrawal`, `Transfer`)
  - `AccountStatus` (`ACTIVE`, `INACTIVE`, `CLOSED`)

### Utility Layer (`com.bharattrustbank.util`)
- `DBConnection`: singleton JDBC connection
- `PasswordHasher`: SHA-256 hashing
- `InputValidator`: centralized input validation
- `AuditLogger`: lightweight logging for key operations

### Exception Layer (`com.bharattrustbank.exception`)
- `InvalidCredentialsException`
- `InsufficientBalanceException`
- `AccountNotFoundException`
- `AccountInactiveException`

---

## 🚀 Features

- Multi-step user signup
- Secure login using card number + hashed PIN
- Account types: `SAVINGS`, `CURRENT`, `FD`, `RD`
- PIN security using **SHA-256 hashing**
- Deposit, Withdrawal, Fast Cash
- Fund transfer with JDBC transaction atomicity
- Balance enquiry
- Mini statement / transaction history
- PIN change
- Audit logging for critical actions

---

## 🗄️ Database Setup

- **Database name**: `bharat_trust_bank`
- **Schema file**: `Database for Workbench/BTB database.sql`

### Steps
1. Open **MySQL Workbench**
2. Run the script: `Database for Workbench/BTB database.sql`
3. Tables created:
   - `users`
   - `accounts`
   - `transactions`
   - `account_services`
4. Ensure MySQL is running on `localhost:3306`

---

## 🗃️ Database Schema (High Level)

### `users`
- `user_id` (PK)
- `form_no` (unique)
- Personal, address, and KYC fields
- `hashed_pin` (`VARCHAR(64)` – SHA-256)
- `role`
- `created_at`

### `accounts`
- `account_id` (PK)
- `user_id` (FK)
- `account_number`, `card_number` (unique)
- `account_type`
- `balance`
- `status`
- `created_at`

### `transactions`
- `transaction_id` (PK)
- `account_id` (FK)
- `transaction_type`
- `amount`
- `transaction_time`
- `remarks`

### `account_services`
- Optional banking services linked to accounts

---

## 📦 Dependencies

### MySQL JDBC Driver (Required)

This project requires the **MySQL JDBC driver** to connect to the database.

- Download: **MySQL Connector/J (Platform Independent JAR)**
- Example: `mysql-connector-j-8.0.x.jar`
- Add the JAR to the project classpath (VS Code → Referenced Libraries)

No other external runtime dependencies are required.

---

## 🧩 UI Date Handling

- A lightweight internal date chooser is used for date selection.
- No external UI libraries (such as JCalendar) are required to build or run the project.

---

## ▶️ How to Build and Run

### 1. Prerequisites
- Java JDK 8+
- MySQL Server
- MySQL JDBC Driver
- Database created using `BTB database.sql`

### 2. Configure Database Credentials (Local Use)

In `src/com/bharattrustbank/util/DBConnection.java`:
- Set `DB_URL`, `DB_USER`, and `DB_PASSWORD` for your local MySQL setup.

> Note: Credentials in the code are placeholders for local testing and must be replaced with your own MySQL credentials.

### 3. Compile to `bin/`

```bash
javac -d bin -cp . $(find src -name "*.java")

On Windows PowerShell (already used in this project):

```powershell
Get-ChildItem -Path ".\src" -Recurse -Filter "*.java" |
  ForEach-Object { $_.FullName } |
  Set-Content -Path ".\sources.txt"
javac --release 8 -d "bin" -cp "." (Get-Content ".\sources.txt")
```

### 4. Run the Application

From the project root:

```bash
java -cp bin com.bharattrustbank.app.BankApplication
```

This launches the `Login` screen and drives the full ATM flow.

---

## 📁 Project Structure

```text
BANK-MANAGEMENT-SYSTEM/
├── screenshots/
│   ├── login.png
│   ├── Main.png
│   ├── MiniStatement.png
│   ├── Signupone.png
│   ├── Signuptwo.png
│   └── Signupthree.png
├── Database for Workbench/
│   └── BTB database.sql
├── src/
│   └── com/bharattrustbank/
│       ├── app/          # Swing UI frames and screens
│       ├── dao/          # JDBC DAOs (users, accounts, transactions)
│       ├── service/      # Business logic and rules
│       ├── model/        # POJOs and enums
│       ├── util/         # DBConnection, hashing, validation, logging
│       └── exception/    # Custom exceptions
├── bin/                  # Compiled .class files
├── .gitignore
└── README.md
```

---

## 💬 Resume / Interview Notes

- **Core Java focus**: Swing UI, JDBC, exception handling, collections, enums.  
- **Layered architecture**: clear separation of **UI → Service → DAO → DB**.  
- **Security basics**: PINs stored as **SHA‑256 hashes** (`hashed_pin`), not in plain text.  
- **Realistic banking logic**: account types, status flags, transaction history, and atomic fund transfer.  
- Easy to explain end-to-end: from Swing event → service method → DAO → MySQL and back.

---

## 👤 Author

**Sunil Prajapat**  
B.E. Computer Science Engineering  
Chandigarh University

---

## 📜 Disclaimer

This project is for **educational and portfolio** purposes only and is **not** intended for real banking use.  
In production systems, credentials must be externalized, sensitive data must be encrypted, and full security hardening is required.
