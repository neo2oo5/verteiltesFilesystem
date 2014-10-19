/**
 * Package
 */
package network;

/**
 * Imports
 */
import java.io.File;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import substructure.GUIOutput;
import java.net.UnknownHostException;
import fileSystem.fileSystemException;

/**
 * Klasse AdminPannel
 * 
 * Klasse für den Admin Bereich
 * - Überprüfungen ob schon Admin eingeloggt ist
 * - Überprüfungen ob schon man selbst als Admin eingeloggt ist
 * - User Kick Funktion, entfernt gewünschten User aus dem Netzwerk ( Sicherrung, man kann sich nicht selbst kicken)
 * - Admin Login
 * - Admin Logout
 * - Admin Message (Nachricht an Alle User, dass z.B. Admin eingeloggt ist)
 * - setLoggedIn - funktion um die variable loggedin zu setzen, dass ein admin eingeloggt ist oder sich ausgeloggt hab
 * 
 * @author David Lampa
 * @version 1.0
 */
public class AdminPannel
{

    /**
     * Variablen Initialisieren
     */
    private static boolean loggedin;
    static GUIOutput outMsg = GUIOutput.getInstance();

    /**
     * Funktion isLoggedin
     * 
     * Diese Funktion gibt einen boolean Wert zurück, 
     * je nach dem ob jmd. eingeloggt ist oder nicht
     *
     * @return boolean loggedin  // true = ein Admin ist bereits eingeloggt
     */
    public static boolean isLoggedin()
    {
        return loggedin;
    }

    /**
     * Funktion setLoggedin
     * 
     * Diese Funktion setzt einen boolean Wert, 
     * je nach dem ob jmd. eingeloggt ist oder nicht
     * 
     * @param loggedin  boolean  // true = ein Admin ist bereits eingeloggt
     */
    public static void setLoggedin(boolean loggedin)
    {
        AdminPannel.loggedin = loggedin;
    }

    /**
     * Funktion adminCheckLogin
     * 
     * Diese Funktion überprüft ob im Netzwerk schon ein Admin eingeloggt ist
     * Falls ja gibt führt der Server wo der Admin eingeloggt ist
     * automatisch setLoggedin aus und setzt loggedin auf true
     */
    public static void adminCheckLogin()
    {
        /**
         * Hole die komplette IPListe als String Liste
         */
        String ownIP = gui.Config.getCurrentIp();
        List<String> IPListe = IPList.getIPList();

        /**
         * gehe die IP-Liste durch und frag jeden ob bei Ihm der Admin eingeloggt ist
         */
        for (String ip : IPListe)
        {
            String doWhat = "CheckAdminLoggedin";
            String[] args = new String[3];
            args[0] = ip;
            args[1] = ownIP;
            args[2] = doWhat;
            StartClientServer.startClient(args);
        }
    }

    /**
     * Funktion adminLogin
     * 
     * Diese Funktion prüft ob bereits ein Admin im Netzwerk eingeloggt ist
     * wenn dies nicht der Falls ist, wird der Benutzer eingeloggt und sendet
     * eine Nachricht an alle, dass nun ein Admin eingeloggt ist
     * Ansonsten gibt es eine Meldung an den Benutzer
     * 
     * @return boolean  // true = login erfolgreich
     */
    public static boolean adminLogin()
    {
        /**
         * Überprüft ob ein Admin eingeloggt ist
         */
        adminCheckLogin();
        
        /**
         * Falls kein Admin eingeloggt ist
         * 
         * - Als Admin einloggen
         * - Datei für Admin Login erstellen
         * - Nachricht an alle, dass nun Admin eingeloggt ist
         * - return true => erfolgreich eingeloggt
         * 
         * 
         * Falls ein Admin eingeloggt ist
         * - return false => bereits ein admin eingeloggt oder Fehler
         */
        if (isLoggedin() == false)
        {
            FileWriter writer = null;
            try
            {
                /**
                 * Admin Datei erstellen
                 */
                String path = substructure.PathHelper.getFile("");
                File file = new File(path + "admin.loggedin");
                writer = new FileWriter(file, false);
                
                /**
                 * Nachricht an alle senden
                 */
                message("Admin Logged in!");
            } catch (IOException | fileSystemException ex)
            {
                /**
                 * Fehler abfangen, ausgeben und fehlgeschlagen zurückgeben
                 */
                outMsg.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 3);
                return false;
            } finally
            {
                try
                {
                    writer.close();
                } catch (IOException ex)
                {
                    /**
                     * Fehler abfangen und ausgeben
                     */
                    outMsg.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 3);
                }
            }
        } else
        {
            /**
             * Fehler abfangen, ausgeben und fehlgeschlagen zurückgeben
             */
            outMsg.print("Es ist bereits ein Admin eingeloggt!", 3);
            return false;
        }
        return true;
    }

    /**
     * Funktion adminLogout
     * 
     * Diese Funktion loggt sich selber als admin aus
     * Sendet eine Nachricht an alle, dass nun der Admin ausgeloggt ist
     * Löscht die admin datei
     * 
     * @return boolean  // true = login erfolgreich
     * @throws java.net.UnknownHostException // wird in der GUI abgefangen und ausgegeben
     */
    public static boolean adminLogout() throws UnknownHostException
    {
        /**
         * Nachricht an alle senden
         */
        message("Admin Logged out!");

        try
        {
            /**
             * Admin Datei löschen
             */
            Delete.deleteFile(substructure.PathHelper.getFile(""), "admin.loggedin");
        } catch (fileSystemException ex)
        {
            /**
             * Fehler abfangen und ausgeben
             */
            outMsg.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 3);
            return false;
        }

        return true;
    }

    /**
     * Funktion message
     * 
     * Diese Funktion holt sich die IPListe, und sendet jedem User die gewünschte Nachricht
     *
     * @param msg String // nachricht die bei allen ausgegeben werden soll
     * @throws java.net.UnknownHostException // wird in der GUI abgefangen und ausgegeben
     */
    public static void message(String msg) throws UnknownHostException
    {
        /**
         * IPListe holen
         * Dann jedem die gewünschte Nachricht übertragen
         */
        List<String> IPListe = IPList.getIPList();

        for (String ip : IPListe)
        {

            String doWhat = "AdminMessage";
            String[] args = new String[3];
            args[0] = ip;
            args[1] = msg;
            args[2] = doWhat;
            StartClientServer.startClient(args);
        }
    }

    /**
     * Funktion adminKickUser
     * 
     * Diese Funktion soll eine bestimmte IP aus allen IPListen im Netzwerk entfernen
     * Holt zuerst die eigene IP und Prüft ob man sich selber Kicken will
     * Falls ja Fehlermeldung, man kann sich nicht selber kicken
     * Falls ungleich eigener IP
     * - Hole die IPListe 
     * - Sage jedem er soll die IP aus seiner Liste entfernen
     *
     *
     * @param ipToKick String // gewünschte IP die entfernt werden soll
     * @throws java.net.UnknownHostException // wird in der GUI abgefangen und ausgegeben
     */
    public static void adminKickUser(String ipToKick) throws UnknownHostException
    {
        /**
         * Hole eigene IP und prüfe ob man sich selber entfernen will
         */
        String ownIP = gui.Config.getCurrentIp();
        if (ipToKick.equals(ownIP))
        {
            /**
             * Falls man sich selber entfernen will, Fehlermeldung ausgeben
             */
            outMsg.print("Sie können sich nicht selbst Kicken!", 3);
        } else
        {

            /**
             * Ansonsten IPListe holen
             * Sage jedem er soll die IP aus seiner Liste entfernen
             */
            List<String> IPListe = IPList.getIPList();

            for (String ip : IPListe)
            {
                String doWhat = "AdminKickUser";
                String[] args = new String[3];
                args[0] = ip;
                args[1] = ipToKick;
                args[2] = doWhat;
                StartClientServer.startClient(args);
            }
        }
    }

    
    /**
     * Funktion IAmAdmin
     * 
     * Diese Funktion überprüft ob man selbst Admin ist,
     * indem geprüft wird ob die Admin Datei Lokal auf dem Rechner ist
     *
     * @return boolean // true = man selbst ist Admin
     */
    public static boolean IAmAdmin()
    {
        /**
         * Variablen Initialisieren
         */
        File file = null;
        boolean check = false;
        try
        {
            /**
             * Testen ob Admin Datei vorhanden ist
             */
            file = new File(substructure.PathHelper.getFile("admin.loggedin"));
            check = file.exists();
        } catch (fileSystemException ex)
        {
            /**
             * Fehler Datei konnte nicht gefunden werden abfangen und false ausgeben
             * -> Man selbst ist kein Admin
             */
            check = false;
        }
        return check;
    }
}
