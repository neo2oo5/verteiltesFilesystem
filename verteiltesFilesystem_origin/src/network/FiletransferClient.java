/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystemException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import substructure.GUIOutput;
import substructure.PathHelper;

/**
 *
 * @author Lamparari
 */
public class FiletransferClient
{

    static GUIOutput out = GUIOutput.getInstance();
    static DynamicPorts dp = DynamicPorts.getInstance();
    static boolean transferReady = false;

    /**
     *
     * @return
     */
    public static boolean isTransferReady()
    {
        return transferReady;
    }

    /**
     *
     * @param transferReady
     */
    public static void setTransferReady(boolean transferReady)
    {
        FiletransferClient.transferReady = transferReady;
    }

    /**
     *
     * @param args
     * @return
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
            out.print("FileTransferClient Startet", 1);
            
            try
            {
                serverSocket = new ServerSocket(Integer.valueOf(args[3]));
            } catch (IOException ex)
            {
                System.out.println("Can't setup server on this port number. ");
            }

            
            
            int bufferSize = 0;

            
            
            try
            {
                socket = serverSocket.accept();
            } catch (IOException ex)
            {
                System.out.println("Can't accept client connection. ");
            }

            try
            {
                is = socket.getInputStream();

                bufferSize = socket.getReceiveBufferSize();
                System.out.println("Buffer size: " + bufferSize);
            } catch (IOException ex)
            {
                System.out.println("Can't get socket input stream. ");
            }

            try
            {
                String targetPath = PathHelper.getFile("Downloads");
                targetPath += File.separator;
                fos = new FileOutputStream(targetPath + args[0]);
                bos = new BufferedOutputStream(fos);

            } catch (FileNotFoundException ex)
            {
                System.out.println("File not found. ");
            } catch (fileSystemException ex)
            {
                System.out.println("File not found. ");
            }

            byte[] bytes = new byte[bufferSize];

            int count;

            while ((count = is.read(bytes)) > 0)
            {
                bos.write(bytes, 0, count);
            }

            bos.flush();
           
            return true;
        } catch (IOException ex)
        {
            out.print("--- Fehler Fileclient --- " + ex.toString(), 3);
        }
        finally{
            try {
                if(bos != null)
                {
                    bos.close();
                }
                else if (is != null)
                {
                    is.close();
                }
                else if (socket != null)
                {
                    socket.close();
                }
                else if (serverSocket != null)
                {
                    serverSocket.close();
                }
                
                
                
               
                setTransferReady(true);
            } catch (IOException ex) {
                Logger.getLogger(FiletransferClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
