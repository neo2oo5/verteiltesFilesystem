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
import substructure.GUIOutput;
import java.net.BindException;
import java.io.FileInputStream;
import java.net.ConnectException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import fileSystem.fileSystemException;

/**
 * Klasse FiletransferServer
 * 
 * Klasse die den FiletransferClient mit den benötigten Informationen startet
 * sich mit dem FiletransferClient verbindet
 * und die gewünschte Datei Sendet
 * 
 * @author David Lampa
 * @version 1.0
 */
public class FiletransferServer
{
    /**
     * Variablen Initialisieren
     */
    static GUIOutput outMsg = GUIOutput.getInstance();
    static DynamicPorts dp = DynamicPorts.getInstance();

    /**
     *
     * @param args
     */
    public static void FileTransferServer(String[] args)
    {

        /**
         * Variablen Initialisieren
         */
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        BufferedOutputStream out = null;
        Socket sock = null;
        int dynamicPort = 0;
        
        try
        {
            IPList.InsertIpInList(args[2]);
            outMsg.print("FileTransferServer startet", 1);

            dp.getPort(args[2]);
            
            do
            {
                dynamicPort = dp.findIdent(dp.getIdentbyString(args[2]));
                
            } while (dynamicPort == -1);
            
            String file = null;
            try
            {
                if (args[0].equals("null"))
                {
                    file = substructure.PathHelper.getFile(args[1]);
                } else
                {
                    file = args[0] + File.separator + args[1];
                }
            } catch (fileSystemException ex)
            {
                outMsg.print("(FileTransferServer) -" + ex.toString(), 2);
            }

            //starte Client und gib file grÃ¶ÃŸe mit
            File myFile = new File(file);
            int fsi = (int) myFile.length();
            StringBuilder sb = new StringBuilder();
            sb.append(fsi);
            String strI = sb.toString();
            String[] argsClient = new String[6];
            argsClient[0] = args[2]; // IP Client
            argsClient[1] = args[3]; // neuerName
            argsClient[2] = strI; // grÃ¶ÃŸe datei
            argsClient[3] = args[4]; // ip Server
            argsClient[4] = String.valueOf(dynamicPort);
            argsClient[5] = "FileTransferClient";

            StartClientServer.startClient(argsClient);

            // Get the size of the file
            boolean trigger = true;
            do
            {
                try
                {
                    sock = new Socket(args[2], dynamicPort);
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
            
            int bufferSize = sock.getReceiveBufferSize();

//                  // Get the size of the file
            long length = myFile.length();
            if (length > Integer.MAX_VALUE)
            {
                outMsg.print("File is too large.", 3);
            }
            byte[] bytes = new byte[(int) length];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            out = new BufferedOutputStream(sock.getOutputStream());

            int count;

            while ((count = bis.read(bytes)) > 0)
            {
                out.write(bytes, 0, count);
            }

            out.flush();
        } catch (IOException ex)
        {
            outMsg.print("(FileTransferServer) " + ex.toString(), 3);
        } finally
        {
            try
            {
                if(out != null) out.close();
                if(fis != null) fis.close();
                if(bis != null) bis.close();
                if(sock != null) sock.close();
                if(dynamicPort != 0) dp.releasePort(dynamicPort);
            } catch (IOException ex)
            {
                outMsg.print("(FileTransferServer) " + ex.toString(), 3);
            }
        }
    }
}
