package Databse;
import java.sql.*;
/**
 *
 * @author Daniel Gauditz
 */
public class DeleteOperation
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
      String sql = "DELETE from File where IP_ADRESS = '192.168.178.23';";
      stmt.executeUpdate(sql);
      c.commit();

      try (ResultSet rs = stmt.executeQuery( "SELECT * FROM FILE;" ))
      {
            while ( rs.next() )
            {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                String path  = rs.getString("path");
                String timeStamp = rs.getString("timeStamp");
                String size = rs.getString("size");
                String ipAdress = rs.getString("IP_ADRESS");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "PATH = " + path );
                System.out.println( "TIMESTAMP = " + timeStamp);
                System.out.println( "SIZE = " + size);
                System.out.println( "IP_Adress = " + ipAdress);
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
