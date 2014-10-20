/**
 * Package
 */
package network;

/**
 * Imports
 */
import java.io.File;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import substructure.GUIOutput;
import java.net.BindException;
import substructure.PathHelper;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.io.BufferedOutputStream;
import fileSystem.fileSystemException;

/**
 * Klasse FiletransferClient
 * 
 * Klasse die sich mit dem FiletransferServer verbindet
 * und die Datei Empfängt
 * 
 * @author David Lampa
 * @version 1.0
 */
public class FiletransferClient
{
    /**
     * Variablen Initialisieren
     */
    static GUIOutput outMsg = GUIOutput.getInstance();
    static DynamicPorts dp = DynamicPorts.getInstance();
    static boolean transferReady = false;

    /**
     * Diese Funktion dient zum auslesen ob der Transfer beendet ist oder nicht
     *
     * @return boolean // true = Transfer Fertig
     */
    public static boolean isTransferReady()
    {
        return transferReady;
    }

    /**
     * Diese Funktion dient zum setzen, ob der Transfer beendet ist oder nicht
     *
     * @param transferReady // true = Transfer Fertig
     */
    public static void setTransferReady(boolean transferReady)
    {
        FiletransferClient.transferReady = transferReady;
    }

    /**
     *
     * Klasse die sich mit dem FiletransferServer verbindet
     * und die Datei Empfängt
     * 
     * @param args String Array // Alle Informationen für den Dateitransfer enthalten
     * @return boolean // true = Transfer erfolgreich
     */
    public static boolean FileTransferClient(String[] args)
    {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try
        {
            setTransferReady(false);
            outMsg.print("FileTransferClient Startet", 1);
            
            boolean trigger = true;
            do
            {
                try
                {
                    serverSocket = new ServerSocket(Integer.valueOf(args[3]));
                    trigger = false;
                }
                catch(BindException ex)
                {
                    trigger = true;
                }
                catch(ConnectException ex)
                {
                    trigger = true;
                }
            }while(trigger);
            

            int bufferSize = 0;
            socket = serverSocket.accept();
            is = socket.getInputStream();

            bufferSize = socket.getReceiveBufferSize();
            String targetPath = PathHelper.getFile("Downloads");
            targetPath += File.separator;
            fos = new FileOutputStream(targetPath + args[0]);
            bos = new BufferedOutputStream(fos);

            byte[] bytes = new byte[bufferSize];

            int count;

            while ((count = is.read(bytes)) > 0)
            {
                bos.write(bytes, 0, count);
            }

            bos.flush();

        } catch (IOException ex)
        {
            outMsg.print("(Filetransfer - Client) " + ex.toString(), 3);
        } catch (fileSystemException ex)
        {
            outMsg.print("(Filetransfer - Client) " + ex.toString(), 3);
        } finally
        {
            try
            {
                if (bos != null)
                {
                    bos.close();
                } else if (is != null)
                {
                    is.close();
                } else if (socket != null)
                {
                    socket.close();
                } else if (serverSocket != null)
                {
                    serverSocket.close();
                }

                setTransferReady(true);
                dp.releasePort(Integer.valueOf(args[3]));
                return true;
            } catch (IOException ex)
            {
            outMsg.print("(Filetransfer - Client) " + ex.toString(), 3);
            }
        }
        return false;
    }
}
