# Database for Workbench

This folder contains database-related files used for the Bharat Trust Bank
Management System.

## Purpose

The project uses **MySQL Workbench** as the database layer. This folder
stores database scripts and data files that were used to:

- Create database tables
- Execute SQL queries
- Manage connections using JDBC
- Test data flow between Java application and database

## Contents

- SQL files containing table creation and query execution
- Database configuration and connection-related data
- Query scripts executed in MySQL Workbench

## Database Connectivity

- The Java application connects to MySQL Workbench using **JDBC**
- Connection details are handled inside the project using JDBC
- Database operations such as login validation and transaction handling
  depend on this setup

## Important

- This folder is required for understanding and reproducing the database
  setup of the project.
- Ensure MySQL Workbench is properly configured before running the project.
