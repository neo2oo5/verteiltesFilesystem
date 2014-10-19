/**
 * Package
 */
package network;

/**
 * Imports
 */
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.BufferedReader;
import substructure.GUIOutput;
import java.io.InputStreamReader;

/**
 * Klasse Client
 * 
 * Klasse zur Kommunikation mit anderen Usern
 * - Diese Klasse schreibt zum erfolgreichen übertragen von Informationen
 *   das mitgegebene String Array in einen String um
 * - Überträgt den String an den gewünschten User
 * 
 * @author David Lampa
 * @version 1.0
 */
public class Client
{
    /**
     * Variablen Initialisieren
     */
    static GUIOutput outMsg = GUIOutput.getInstance();

    /**
     * Funktion client
     * 
     * Diese Funktion dient zur Kommunikation mit anderen Usern
     * - Diese Klasse schreibt zum erfolgreichen übertragen von Informationen
     *   das mitgegebene String Array in einen String um
     * - Überträgt den String an den gewünschten User
     *
     * @param args String[] // das zu umwandelnde String Array mit den gewünschten Informationen
     */
    public static void client(String[] args)
    {
        try
        {
            /**
             * Variablen Initialisieren
             * Socket mit Verbindung zum gewünschten User öffnen
             */
            String anServer = "";
            Socket client = new Socket(args[0], 1717);
            
            /**
             * Wandelt das mitgegebene String Array in einen String um
             */
            anServer += args[1];
            for (int i = 2; i < args.length; i++)
            {
                anServer += "#entf#" + args[i];
            }

            
            /**
             * Stream Variablen Initialisieren
             */
            OutputStream out = client.getOutputStream();
            BufferedReader reader;
            
            try (PrintWriter writer = new PrintWriter(out))
            {
                /**
                 * Stream Variablen Initialisieren
                 */
                InputStream in = client.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                
                /**
                 * String in Stream schreiben und wegsenden
                 */
                writer.write(anServer);
                writer.flush();
                
                /**
                 * Streams & Client Schließen
                 */
                writer.close();
                reader.close();
                client.close();
            }
        } catch (IOException ex)
        {
            /**
             * Fehler abfangen und ausgeben
             */
            outMsg.print("(Client - client) : " + ex.toString(), 2);
        }
    }
}
