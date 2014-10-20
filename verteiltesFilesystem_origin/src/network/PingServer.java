/**
 * Package
 */
package network;

/**
 * Imports
 */
import java.net.Socket;
import java.io.IOException;
import fileSystem.fileSystem;
import java.net.SocketAddress;
import substructure.GUIOutput;
import java.net.InetSocketAddress;
import static java.lang.Thread.sleep;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasse PingServer
 * 
 * Diese Klasse dient zum Test ob der gewünschte User noch erreichbar ist
 * 
 * @author David Lampa
 * @version 1.0
 * 
 */
public class PingServer
{
    /**
     * Variablen Initialisieren
     */
    static GUIOutput outMsg = GUIOutput.getInstance();
    static fileSystem c = fileSystem.getInstance();

    /**
     * Funktion PingServer
     * 
     * Diese Funktion Pingt den gewünschten User an um zu Prüfen ob er noch da ist
     *
     * @param checkIP String // die zu Überprüfende IP
     * @return boolean // true = user erreichbar
     */
    public static boolean PingServer(String checkIP)
    {
        /**
         * Variablen Initialisieren
         * Socket Initialisieren
         */
        int counter = 0;
        boolean chk = true;
        Socket socket = new Socket();
        
        /**
         * Solange chk ungleich true bleibe in der Schleife
         */
        do
        {
            try
            {
                /**
                 * Starte den Socket mit Verbindung zum Gewünschten User 
                 * mit einem Max. Timeout von einer Sekunde
                 * Setze chk auf true
                 */
                SocketAddress sockaddr = new InetSocketAddress(checkIP, 1717);
                socket.connect(sockaddr, 1000);
                chk = true;
            } catch (SocketTimeoutException ex)
            {
                try
                {
                    /**
                     * Warte eine Halbe Sekunde
                     */
                    sleep(800);
                } catch (InterruptedException ex1)
                {
                    /**
                     * Fehler abfangen, ausgeben - falls Sleep Fehlschlägt
                     */
                    outMsg.print("(PingServer) " + ex1.toString(), 1);
                }
                
                /**
                 * Setze chk als false - nicht geklappt
                 * Zähle den Counter hoch
                 */
                chk = false;
                counter++;
                ex.printStackTrace();
                
                /**
                 * Soald die Verbindung zum gewünschten User 4 mal nicht erfolgreich war
                 * Lösche die IP aus der Liste, Melde dass die IP gelöscht wurde
                 * Gebe flase zurück // nicht Erfolgreich
                 */
                if (counter >= 8)
                {
                    outMsg.print("IP: " + checkIP + " wurde aus dem Netzwerk entfernt, da nicht erreichbar!", 1);
                    return false;
                }
            } catch (IOException ex)
            {
                outMsg.print("(CheckWhoIsOnline - PingServer) : " + ex.toString(), 2);
            }
        } while (!chk);
        try
        {
            /**
             * Schließe die Socket Verbindung
             */
            socket.close();
        } catch (IOException ex)
        {
            /**
             * Fehler abfangen, ausgeben und fehlgeschlagen zurückgeben
             */
            outMsg.print("(CheckWhoIsOnline - PingServer) : " + ex.toString(), 2);
            return false;
        }
        /**
         * Gebe Erfolgreich zurück
         */
        return true;
    }
}
