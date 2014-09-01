package Databse;
import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 *
 * @author Bobo
 */
public class InsertOperation
{

    /**
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main( String args[]) throws IOException
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
      AusLagerungIntern interneFunktionen = new AusLagerungIntern();
      EingehendNetzwerk eingehendNetzwerk = new EingehendNetzwerk();
      
      String sql = "INSERT INTO FILE (ID,NAME,EXTENSION,PATH,TIMESTAMP,SIZE,IP_ADRESS) " +
                   "VALUES (1,'"+interneFunktionen.getFileNameWithoutExtension()+"','"+interneFunktionen.getFileExtension()+"',"
              + "           '"+interneFunktionen.getAbsolutePfad()+"','"+interneFunktionen.lastModified()+"',"
              + "           '"+interneFunktionen.getFileSize()+"',"
              + "           '"+eingehendNetzwerk.getIp()+"');" ; 
      
      stmt.executeUpdate(sql);
      stmt.close();
      c.commit();
      c.close();
    } 
    catch ( ClassNotFoundException | SQLException e )
    {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Records created successfully");
  }   

   private static File File(String eworkspaceProject_Versuch_SQL_14esttxt)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
