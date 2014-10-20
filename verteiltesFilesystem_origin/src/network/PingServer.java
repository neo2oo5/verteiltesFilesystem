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
import javax.net.SocketFactory;

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
//                     SocketAddress sockaddr = new InetSocketAddress(checkIP, 1717);
//                     socket.connect(sockaddr, 1000);
                    Socket s = SocketFactory.getDefault().createSocket(checkIP, 1717);
                    outMsg.print("AAAAAAAAAAAAAA " + s.isConnected(), 3);
                    if(s.isConnected() == true)
                    {
                        chk = true;
                    }
                } catch (IOException ex)
                {
                    outMsg.print("------------------------------------",3);
                        counter++;
                        try {
                            sleep(800);
                        } catch (InterruptedException ex1) {
                            outMsg.print("(PingServer) " + ex1.toString(), 1);
                        }
                        chk = false;
                    
                        if (counter >= 4)
                        {
                            outMsg.print("IP: " + checkIP + " wurde aus dem Netzwerk entfernt, da nicht erreichbar!", 1);
                            return false;
                        }
                }
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
