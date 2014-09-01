package Databse;

import java.sql.*;

/**
 * 
 * @author Matthäus Piechowiak
 */

public class Join {
public static void main(String args[]) {
    
    Statement stmt;
    

    try {
    Class.forName("org.sqlite.JDBC");
        
        //Ich hab keine Ahnung, wie man es elegant hinbekommt mehrere Connections zu machen
        //Von daher muss man die DB Connection hier erstmal per Hand ändern.
        //Ansonsten hätte ich halt den Codeblock nochmal für ne andere Connection
        //mit anderen Variablen kopieren/duplizieren müssen
        //Das sieht halt irgendwie kacke aus.
    
        Connection sourceConnection = DriverManager.getConnection("jdbc:sqlite:test2.db");
        
        //Für merged.db muss halt vorher mit der createTable Klasse das FILE Table erstellen.
        //Könnte man hier eigentlich auch reinmachen, das würde dann aber bei einer
        //veränderten Connection dann aber nich mehr funktionieren, da das Table ja nach dem
        //ersten mal ausführen schon da ist.
        
        Connection targetConnection = DriverManager.getConnection("jdbc:sqlite:merged.db");
        System.out.println("Opened databases successfully");
        
            String insertSQL = "INSERT INTO FILE"
            + "(id, name, extension, path, timestamp) VALUES"
            + "(?,?,?,?,?)";
            
            //Prepared Statement insert Statement füllt die Platzhalter ("?") 
            //mit den Daten aus dem normalen select Statement der Source DB.
            
            PreparedStatement insertStatement = targetConnection.prepareStatement(insertSQL);

            Statement selectStatement = sourceConnection.createStatement();

        try (ResultSet rs = selectStatement.executeQuery("SELECT * FROM FILE;")) {
        
            while (rs.next()) {

                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String extension = rs.getString("extension");
                    String path = rs.getString("path");
                    String timeStamp = rs.getString("timeStamp");

                    insertStatement.setInt(1, id);
                    insertStatement.setString(2, name);
                    insertStatement.setString(3, extension);
                    insertStatement.setString(4, path);
                    insertStatement.setString(5, timeStamp);
                    insertStatement.executeUpdate();

        }
    }
        
    //Beim Versuch zwei DBs zu mergen, wobei bei beiden jeweils einmal die
    //gleiche ID drin ist, krieg ich ne Exception, da die ID bisher ja primary key ist.
    //d.h. er lässt einfach den doppelten primary key raus und kopiert trotzdem den rest rein.
        
    insertStatement.close();
    selectStatement.close();
    targetConnection.close();
    sourceConnection.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
            System.out.println("Operation done successfully");
    }
}