# Bharat Trust Bank – Bank Management System

A Java-based desktop **Bank Management System** developed using **Java Swing** for
the graphical user interface and **JDBC** for database connectivity with
**MySQL Workbench**.

This project simulates real-world banking operations such as user authentication,
account creation, balance enquiry, cash deposit, withdrawal, PIN management,
and fast cash transactions. It demonstrates strong fundamentals of **Core Java**,
**Object-Oriented Programming (OOP)**, **GUI development**, and **database integration**.

---

## 🚀 Features

- User Login with PIN verification  
- New Account / User Signup  
- Balance Enquiry  
- Cash Deposit  
- Cash Withdrawal  
- Fast Cash option  
- PIN change functionality  
- JDBC-based MySQL database connectivity  
- GUI-based desktop application using Java Swing  
- Modular and structured Java classes  

---

## 🛠️ Technology Stack

| Component | Technology |
|---------|------------|
| Programming Language | Java |
| JDK Version | Java 17 (LTS) |
| GUI | Java Swing |
| Database | MySQL |
| Database Tool | MySQL Workbench |
| Connectivity | JDBC |
| IDE | IntelliJ IDEA / VS Code |
| Version Control | Git & GitHub |

---
```text 
## 📁 Project Structure

Bharat-Trust-Bank-Management-System/
├── src/
│   └── bank/management/system/
│       ├── Login.java
│       ├── Main.java
│       ├── Deposit.java
│       ├── Withdrawal.java
│       ├── BalanceEnqiry.java
│       ├── FastCash.java
│       ├── Pin.java
│       ├── Signup.java
│       ├── Signup2.java
│       ├── Signup3.java
│       └── DBConnection.java
        └── MiniStatement.java
├── icon/
│   ├── atm2.png
│   ├── backbg.jpg
│   └── bank.png
├── Libraries/
│   ├── mysql-connector-java-8.0.28.jar
│   ├── jcalendar-tz-1.3.3-4.jar
│   └── README.md
├── Database for Workbench/
│   ├── MySQL database.sql
│   └── README.md
├── .gitignore
└── README.md
```
---


## 📦 External Libraries

The project depends on external libraries stored in the **Libraries/** folder.

- **MySQL JDBC Connector**  
  Used to establish JDBC connection between the Java application and MySQL database.

- **JCalendar Library**  
  Used for date-related UI components during user signup.

All JAR files must be added to the project build path before running the application.

---

## 🗄️ Database Configuration

The application uses **MySQL Workbench** as the database layer.

### Steps to set up the database:
1. Open **MySQL Workbench**
2. Create a new database/schema
3. Execute the SQL file located at:
4. Ensure JDBC connection details in Java code match your database credentials

---

## 🗃️ Database Schema Documentation

The application uses a MySQL database to store user account details and
transaction records. The schema is designed to support basic banking
operations such as account creation, balance enquiry, deposits, withdrawals,
and PIN management.

---

### 📌 Table: users

Stores bank account holder details.

| Column Name | Data Type | Description |
|------------|----------|-------------|
| id | INT (Primary Key) | Unique user/account ID |
| name | VARCHAR(100) | Account holder name |
| email | VARCHAR(100) | User email address |
| pin | VARCHAR(10) | Account PIN for authentication |
| balance | DECIMAL(10,2) | Current account balance |

---

### 📌 Table: transactions

Stores all deposit and withdrawal records.

| Column Name | Data Type | Description |
|------------|----------|-------------|
| id | INT (Primary Key) | Unique transaction ID |
| user_id | INT (Foreign Key) | References `users.id` |
| transaction_type | VARCHAR(20) | Deposit / Withdrawal / Fast Cash |
| amount | DECIMAL(10,2) | Transaction amount |
| transaction_date | TIMESTAMP | Date and time of transaction |

---

### 🔗 Table Relationships

- Each **user** can have multiple **transactions**
- `transactions.user_id` is linked to `users.id`
- All transactions are processed through JDBC queries

---

### 🔐 Security Note

- PIN values are used for authentication
- Database credentials should be configured locally and **must not be exposed**
  in public repositories

---

## ▶️ How to Run the Project

### Prerequisites
- Java 17 installed and configured
- MySQL Workbench installed
- Database created using provided SQL file
- External JAR libraries added to classpath

### Run Steps
1. Open the project in **IntelliJ IDEA** or **VS Code**
2. Configure **Java 17** as the project SDK
3. Add JAR files from the `Libraries/` folder to the build path
4. Run `Login.java` to start the application

---

## 🎯 Project Purpose

This project was developed for academic and practical learning purposes to
demonstrate:

- Core Java programming skills
- Object-Oriented Programming concepts
- GUI development using Java Swing
- Database connectivity using JDBC
- Clean project structure and version control using Git

---

## 🔮 Future Enhancements

- Transaction history feature
- Admin dashboard
- Improved validation and exception handling
- Executable JAR packaging
- Refactoring to MVC architecture

---

## 👤 Author

**Sunil Prajapat**  
B.E. Computer Science Engineering  
Chandigarh University

---

## 📜 Disclaimer

This project is developed strictly for educational purposes and is not intended
for production or real banking use.

