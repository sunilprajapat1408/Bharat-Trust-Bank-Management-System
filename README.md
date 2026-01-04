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
│       ├── main_Class.java
│       ├── Deposit.java
│       ├── Withdrawal.java
│       ├── BalanceEnquriy.java
│       ├── FastCash.java
│       ├── Pin.java
│       ├── Signup.java
│       ├── Signup2.java
│       ├── Signup3.java
│       └── Connn.java
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

