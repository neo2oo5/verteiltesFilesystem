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

    public static boolean isTransferReady()
    {
        return transferReady;
    }

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
        setTransferReady(false);
        out.print("FileTransferClient Startet", 1);
        BufferedOutputStream bos = null;
        Socket sock = null;
        
        for(String arg: args)
            {
                
                out.print(arg, 2);
            }
        try
        {   
        
            String targetPath = PathHelper.getFile("Downloads");
            targetPath += File.separator;
            int fs = Integer.parseInt(args[1]);
            //sock = new Socket(args[2], 1718);
            sock = new Socket(args[2], Integer.valueOf(args[3]));
            byte[] mybytearray = new byte[fs];
            InputStream is = sock.getInputStream();
            File path = new File(targetPath + args[0]);
            FileOutputStream fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            bos.write(mybytearray, 0, bytesRead);

        } catch (IOException ex)
        {
            return false;
        } catch (fileSystemException ex)
        {
            out.print("(FileTransferClient) " + ex.toString(), 3);
        } finally
        {

            try
            {
                bos.close();
                sock.close();
            } catch (IOException ex)
            {
                out.print("(FileTransferClient) " + ex.toString(), 3);
            }
        }
        setTransferReady(true);
        return true;
    }
}
