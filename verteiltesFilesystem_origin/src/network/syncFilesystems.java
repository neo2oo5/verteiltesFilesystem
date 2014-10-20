/**
 * Package
 */
package network;

/**
 * Imports
 */
import gui.Config;
import java.util.List;
import fileSystem.fileSystem;
import substructure.GUIOutput;
import java.net.UnknownHostException;
import fileSystem.fileSystemException;

/**
 * Klasse syncFilesystems
 * 
 * Klasse dient dazu, das FileSystem Synchron zu halten
 * 
 * @author David Lampa
 * @version 1.0
 * 
 * @Runnable
 */
public class syncFilesystems implements Runnable
{
    /**
     * Variablen Initialisieren
     */
    private String fullPath = "", filename = "myFileList.ser";
    private static GUIOutput outMsg = GUIOutput.getInstance();

    /**
     * Funktion syncFilesystems
     * 
     * Diese Funktion dient dazu, das FileSystem über Thread zu Synchronisieren
     *
     */
    public syncFilesystems()
    {
        try
        {
            /**
             * Variablen Initialisieren
             */
            fullPath = substructure.PathHelper.getFile(filename);
        } catch (fileSystemException ex)
        {
            /**
             * Fehler abfangen und ausgeben
             */
            outMsg.print("(syncFilesystems) " + ex.toString(), 3);
        }

    }

    @Override
    public void run()
    {
        outMsg.print("(syncFileSystems) start", 1);

        /**
         * IPListe holen
         */
        List<String> ips = IPList.getIPList();

        /**
         * Solange IP's in der IPListe sind
         */
        for (String ip : ips)
        {
            /**
             * Solange User anpingbar ist und nicht der eigenen IP entspricht
             */
            if (PingServer.PingServer(ip) && !ip.equals(Config.getCurrentIp()))
            {
                if (downloadFSbyIP(ip))
                {
                    /**
                     * Füge die FileSysteme zusammen
                     */
                    fileSystem fs = fileSystem.getInstance();
                    fs.mergeList();
                } else
                {
                    /**
                     * Fehler abfangen und ausgeben
                     */
                    outMsg.print("Ein sync Fehler mit folgender IP trat auf: " + ip);
                }
            }
        }

        outMsg.print("(syncFileSystems) standart gemäß beendet");
    }

    /**
     * Funktion downloadFSbyIP
     * 
     * Diese Funktion dient dazu, das FileSystem zu Synchronisieren
     *
     * @param IP String // gewünschte IP von der das FileSystem geholt werden soll
     * @return boolean // true = Synchronisieren Erfolgreich
     */
    private boolean downloadFSbyIP(String IP)
    {
        try
        {
            /**
             * Variablen Initialisieren
             */
            boolean trigger = true;
            do
            {
                /**
                 * Falls der Dateidownload Fertig ist
                 */
                if (Interfaces.interfaceFileTransfer(IP, null, filename, "inComingList.ser"))
                {
                    do
                    {
                        try {
                            /**
                             * Warte eine Sekunde solange der DOwnload noch nicht abgeschlossen ist
                             */
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            /**
                             * Fehler abfangen und ausgeben
                             */
                            outMsg.print("(downloadFSbyIP) " + ex.toString(), 3);
                        }
                        
                    }while( FiletransferClient.isTransferReady() != true);
                    trigger = false;
                }

            } while (trigger);
            
            /**
             * Gebe Erfolgreich zurück
             */
            return true;

        } catch (UnknownHostException ex)
        {
            
            /**
             * Fehler abfangen, ausgeben und Fehlgeschlagen zurückgeben
             */
            outMsg.print("(downloadFSbyIP) " + ex.toString(), 3);
            return false;
        }
    }
}
