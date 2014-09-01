package Databse;
import java.sql.*;
/**
 *
 * @author Daniel Gauditz
 */
public class SelectOperation
{
  public static void main( String args[] )
  {
    Connection c;
    Statement stmt;
    try 
    {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
      c.setAutoCommit(false);
      System.out.println("Opened database successfully");

      stmt = c.createStatement();
      try (ResultSet rs = stmt.executeQuery( "SELECT * FROM FILE;" ))
      {
            while ( rs.next() ) 
            {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                String extension = rs.getString("extension");
                String  path  = rs.getString("path");
                String timeStamp = rs.getString("timeStamp");
                String size = rs.getString("size");
                String ipAdress = rs.getString("ip_Adress");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "EXTENSION = " + extension );
                System.out.println( "PATH = " + path );
                System.out.println( "TIMESTAMP = " + timeStamp);
                System.out.println( "SIZE = " + size);
                System.out.println( "IP_ADRESS = " + ipAdress);
                System.out.println();
            } 
      }
      stmt.close();
      c.close();
    } 
    catch ( ClassNotFoundException | SQLException e )
    {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Operation done successfully");
  }
}
