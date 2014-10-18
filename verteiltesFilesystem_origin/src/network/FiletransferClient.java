/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystemException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
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
            System.out.println("Buffer size: " + bufferSize);
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
            out.print("(Filetransfer - Client) " + ex.toString(), 3);
            ex.printStackTrace();
        } catch (fileSystemException ex)
        {
            out.print("(Filetransfer - Client) " + ex.toString(), 3);
            ex.printStackTrace();
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
            out.print("(Filetransfer - Client) " + ex.toString(), 3);
            ex.printStackTrace();
            }
        }
        return false;
    }
}
