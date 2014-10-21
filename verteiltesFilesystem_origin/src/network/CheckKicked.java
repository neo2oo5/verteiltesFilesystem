/**
 * Package
 */
package network;

/**
 * Imports
 */
import gui.Config;
import java.net.UnknownHostException;

/**
 * Klasse CheckKicked
 * 
 * Klasse zum Testen ob man aus dem Netzwerk entfernt wurde
 * - IPListe nach der eigenen IP durchsuchen
 * - Falls eigene IP nicht gefunden wurde, return true
 * - ansonsten return false // alles in Ordnung, man ist noch im Netzwerk
 * 
 * @author David Lampa
 * @version 1.0
 */
public class CheckKicked
{


    /**
     * Funktion checkKicked
     * 
     * Diese Funktion prüft ob man aus dem Netzwerk entfernt wurde
     * - IPListe nach der eigenen IP durchsuchen
     * - Falls eigene IP nicht gefunden wurde, return true
     *
     * @return boolean // false  alles in Ordnung, man ist noch im Netzwerk
     * @throws java.net.UnknownHostException // wird in der GUI abgefangen und ausgegeben
     */
    public static boolean checkKicked() throws UnknownHostException
    {
        return !IPList.SearchIP(Config.getCurrentIp());
    }
}
