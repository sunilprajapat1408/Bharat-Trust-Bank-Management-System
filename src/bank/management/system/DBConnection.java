package bank.management.system;

import java.sql.*;

public class DBConnection {
    Connection connection;
    Statement statement;
    public DBConnection(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_management","root","Password");
            statement = connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
