package Databse;

import java.sql.*;
/**
 *
 * @author Daniel Gauditz
 */
public class CreateTable
{
   /**
    * erstellt die Tabelle
    * @param args 
    */
  public static void main( String args[] )
  {
    Connection c;
    Statement stmt;
    try 
    {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
      System.out.println("Opened database successfully");

      stmt = c.createStatement();
      String sql;
        sql = "CREATE TABLE FILE " +
                "(ID INT PRIMARY KEY           NOT NULL," +
                " NAME           TEXT          NOT NULL," +
                " Extension      TEXT          NOT NULL," +
                " PATH           TEXT          NOT NULL," +
                " TIMESTAMP      TEXT          NOT NULL," +
                " SIZE           TEXT          NOT NULL," +
                " IP_ADRESS      TEXT          NOT NULL)";
      stmt.executeUpdate(sql);
      stmt.close();
      c.close();
    } 
    catch ( ClassNotFoundException | SQLException e ) 
    {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Table created successfully");
  }
}
