package gui;

import fileSystem.fileSystemException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import network.Interfaces;
import substructure.GUIOutput;

/**
 * Klasse ueberprueft Login und wechselt Login Panel mit Control Panel
 * 
 * @author Kevin Bonner  - kevin.bonner@gmx.de
 * 
 */
public class Admin
{

    private static GUIOutput out = GUIOutput.getInstance();
    private JLabel welcome = new JLabel("Willkomen");
    private AdminControlPanel apanel;

  /**
     * Erstellt ControlPanel und added es an Admin Tab  
     *
     * @param configpanel
     * @param loginpanel
     */
    public Admin(JPanel configpanel, JPanel loginpanel)
    {

        apanel = new AdminControlPanel(loginpanel);

        configpanel.add(apanel);

    }

 /**
     * Aktualisiert die Netzwerk Liste
     */
    public void refresh()
    {
        apanel.refreshUserlist();
    }

    /**
     * Prüft ob schon ein Admin Eingeloggt ist und ob Passwort und Benutzernamen korrekt sind
     *
     * @param username
     * @param password
     * @return true or false
     */
    public static boolean Login(String username, String password)
    {
        String propusername = "";
        String proppassword = "";

        if (username == null && username.isEmpty() && password == null && password.isEmpty() && Interfaces.interfaceAdminLogin())
        {
            out.print("Keine Login Daten!");
            return false;
        }

        Properties prop = new Properties();
        InputStream input = null;

        try
        {

            // load a properties file
            prop.load(new FileReader(substructure.PathHelper.getFile("user.properties")));

            // get the property value and print it out
            propusername = prop.getProperty("ADMINUSERNAME");
            proppassword = prop.getProperty("ADMINPASSWORD");
            out.print("Conf Username: " + propusername);
            out.print("Conf Password: " + proppassword);
            out.print("Param Username: " + username);
            out.print("Param Password: " + password);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        } catch (fileSystemException ex)
        {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (username.equals(propusername) && password.equals(proppassword))
        {
            boolean loggedin = Interfaces.interfaceAdminLogin();
            if (loggedin)
            {
                out.print("Login erfolgreich");
                return true;
            } else
            {
                new GuiPromptHelper(GuiPromptHelper.showError, "Ein Admin ist bereits angemeldet!");
                return false;
            }
        } else
        {
            out.print("Falsche login Daten!");
            return false;
        }

    }
}
