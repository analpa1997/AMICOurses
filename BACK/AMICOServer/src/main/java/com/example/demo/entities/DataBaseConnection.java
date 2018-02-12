package com.example.demo.entities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
	
	protected Connection con;
    protected Statement stmt;
    private String serverName= "localhost";
    private String portNumber = "3306";
    private String databaseName= "";
    private String url = "jdbc:mysql://localhost:3306/" + databaseName;
    private String userName = "root";
    private String password = ""; 
    private String errString;
    
    // Constructor public Connect(){}

    public DataBaseConnection() {

    }
    
    private String getConnectionUrl() {
    	return url;
    }

   public Connection Conect() {
	   con=null;
       try{
    	   Class.forName("org.gjt.mm.mysql.Driver");
           con = DriverManager.getConnection(getConnectionUrl(),userName,password);
           stmt=con.createStatement();
           System.out.println("Connected");
       }catch(Exception e) {
             errString= "Error While connecting to the Data Base";
             System.out.println(errString);
             return null;
        }
        return con;
   }

 
   public void Disconnect() {
	   
	   try {
		   stmt.close();
           con.close();
       }catch(SQLException e){
    	   errString= "Error While closing the Data base";
         }
   }
   
   public Statement getStmt() {
	   
	   return this.stmt;
   }
}