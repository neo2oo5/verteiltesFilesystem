package Database;
/**
 * import SQLite libaries
 */
import java.sql.*;

/**
 * 
 * @author Daniel Gauditz
 */
public class ConnectToDatabase
{
   /**
    * legt die Datenbank an
    * @param args 
    */
  public static void main( String args[] )
  {
    Connection c;
    try 
    {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
    }
    catch ( ClassNotFoundException | SQLException e )
    {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Opened database successfully");
  }
}