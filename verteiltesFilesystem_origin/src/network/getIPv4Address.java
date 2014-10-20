/**
 * Package
 */
package network;

/**
 * Imports
 */
import gui.Config;
import java.util.ArrayList;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Enumeration;
import substructure.GUIOutput;
import java.net.SocketException;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

/**
 * Klasse getIPv4Address
 * 
 * Klasse dient dazu, alle Aktuellen und in verwendung stehenden (W)LAN Ports zu erkennen
 * - die IP und den Adapter Name zu holen und in die ArrayListe bzw in die IPs.txt einzutragen
 * 
 * @author David Lampa, Michael Marchand
 * @version 1.0
 */
public class getIPv4Address
{
    /**
     * Variablen Initialisieren
     */
    static GUIOutput outMsg = GUIOutput.getInstance();

    /**
     * Funktion getIPv4Address
     * 
     * Diese Funktion dient dazu, alle Aktuellen und in verwendung stehenden (W)LAN Ports zu erkennen
     * - die IP und den Adapter Name zu holen und in die ArrayListe bzw in die IPs.txt einzutragen
     *
     * @return ArrayList String Array IPListe 
     * @throws java.net.UnknownHostException
     */
    public static ArrayList<String> getIPv4Address() throws UnknownHostException
    {
        /**
         * Variablen, IPListe Initialisieren
         */
        int n = 0;
        Enumeration<NetworkInterface> netInter = null;
        ArrayList<String> IPListe = new ArrayList<String>();
        
        try
        {
            /**
             * Hole alle Netzwerkadapter und trage diese in netInter ein
             */
            netInter = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ex)
        {
            /**
             * Fehler abfangen und ausgeben
             */
            outMsg.print("(getIPv4Address - getIPv4Address) : " + ex.toString(), 2);
        }
        
        /**
         * Solange weitere Adapter in der netInter Liste eingetragen sind
         */
        while (netInter.hasMoreElements())
        {
            /**
             * Variablen Initialisieren
             * Nächste Element von netInter holen
             */
            int finder = 0;
            NetworkInterface ni = netInter.nextElement();
            
            /**
             * Hole die Netzwerk Adressen (Name + IP) solange noch welche in der Liste sind
             */
            for (InetAddress iaddress : Collections.list(ni.getInetAddresses()))
            {
                /**
                 * Überprüfe ob der Netzwerk Adapter verwendet werden kann
                 * - Loopback Adresse auf false prüfen
                 * - Local Adresse auf true prüfen
                 * - wenn dies der Fall ist, Adaptername und IP in die IPListe eintragen
                 */
                if (iaddress.isLoopbackAddress() == false && iaddress.isSiteLocalAddress() == true)
                {
                    IPListe.add(iaddress.getHostName() + "/" + iaddress.getHostAddress());
                }
            }
        }
        
        /**
         * gebe die IPListe zurück
         */
        return IPListe;

    }

    /**
     * Funktion getIPv4Address
     * 
     * Diese Funktion dient dazu, alle Aktuellen und in verwendung stehenden (W)LAN Ports zu erkennen
     * - diese als verwendete IP zu setzen
     * - setzt die IP für den Programmstart
     *
     * @throws java.net.UnknownHostException
     */
    public static void setIPv4Address() throws UnknownHostException
    {
        /**
         * Variablen Initialisieren
         * Nächste Element von netInter holen
         */
        int n = 0;
        String ip = null;
        Enumeration<NetworkInterface> netInter = null;
        
        try
        {
            /**
             * Hole alle Netzwerkadapter und trage diese in netInter ein
             */
            netInter = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ex)
        {
            /**
             * Fehler abfangen und ausgeben
             */
            outMsg.print("(getIPv4Address - setIPv4Address) : " + ex.toString(), 2);
        }
        
        /**
         * Solange weitere Adapter in der netInter Liste eingetragen sind
         */
        while (netInter.hasMoreElements())
        {
            /**
             * Variablen Initialisieren
             * Nächste Element von netInter holen
             */
            int finder = 0;
            NetworkInterface ni = netInter.nextElement();
            
            /**
             * Hole die Netzwerk Adressen (Name + IP) solange noch welche in der Liste sind
             */
            for (InetAddress iaddress : Collections.list(ni.getInetAddresses()))
            {
                /**
                 * Überprüfe ob der Netzwerk Adapter verwendet werden kann
                 * - Loopback Adresse auf false prüfen
                 * - Local Adresse auf true prüfen
                 * - wenn dies der Fall ist, Adaptername und IP in die IPListe eintragen
                 *   & als Aktuelle IP setzen
                 */
                if (iaddress.isLoopbackAddress() == false && iaddress.isSiteLocalAddress() == true)
                {
                    ip = iaddress.getHostAddress();
                    Config.setCurrentIp(ip);
                }
            }
        }
    }
}
