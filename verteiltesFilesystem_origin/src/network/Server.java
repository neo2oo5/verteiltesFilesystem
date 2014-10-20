/**
 * Package
 */
package network;

/**
 * Imports
 */
import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import substructure.GUIOutput;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * Klasse Server
 * 
 * Klasse zur Kommunikation mit anderen Usern
 * - Diese Klasse dient zum empfangen der gewünschten Aktionen
 * - gibt diese dem Handler weiter, der die gewünschte Aktion dann ausführt
 * - wird beim Programmstart gestartet und bleibt bis zum Beenden des Programms
 * 
 * @author David Lampa
 * @version 1.0
 * 
 */
public class Server
{

    /**
     * Variablen Initialisieren
     */
    static GUIOutput outMsg = GUIOutput.getInstance();

    /**
     * Funktion server
     * 
     * Diese Funktion dient zur Kommunikation mit anderen Usern
     * - Diese Klasse dient zum erfolgreichen empfangen von Informationen
     *   das mitgegebene String an den Handler weiter zu geben
     *
     */
    public static void server()
    {
        /**
         * ThreadPool, Socket Initialisieren
         */
        ExecutorService executor = Executors.newFixedThreadPool(80);
        ServerSocket server;
        
        try
        {
            /**
             * Serversocket Verbindung erstellen, damit die Client's sich mit dem
             * Server verbinden können
             */
            server = new ServerSocket(1717);
            
            /**
             * Gebe aus, dass der Server und das Programm gestartet sind
             */
            outMsg.print("Programm gestartet!", 1);
            outMsg.print("Server Gestartet!", 1);

            /**
             * Wenn ein Client sich verbinden will
             * - Verbindung Aktzeptieren
             * - Daten an den Handler weitergeben
             */
            while (true)
            {
                Socket client = server.accept();
                executor.execute(new Handler(client));

            }
        } catch (IOException ex)
        {
            /**
             * Fehler abfangen und ausgeben
             */
            outMsg.print("(Server - server) : " + ex.toString(), 3);
        }

    }
}
