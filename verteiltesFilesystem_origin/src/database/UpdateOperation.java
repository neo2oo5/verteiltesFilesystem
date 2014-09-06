package database;
import java.sql.*;
/**
 *
 * @author Daniel Gauditz
 */
public class UpdateOperation
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
      String sql = "UPDATE File set ID = 2 where ID=1;";
      stmt.executeUpdate(sql);
      c.commit();

      try (ResultSet rs = stmt.executeQuery( "SELECT * FROM FILE;" ))
      {
            while ( rs.next() ) 
            {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                String  path  = rs.getString("path");
                String timeStamp = rs.getString("timeStamp");
                String size = rs.getString("size");
                String ipAdress = rs.getString("IP_ADRESS");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
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

    private static Object getExtension(String name)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
