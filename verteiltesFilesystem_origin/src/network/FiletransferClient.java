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
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static network.Interfaces.out;

import substructure.GUIOutput;
import substructure.PathHelper;

/**
 *
 * @author Lamparari
 */
public class FiletransferClient
{

    static GUIOutput out = GUIOutput.getInstance();

    public static boolean FileTransferClient(String[] args)
    {
        try
        {
            out.print("_____" + args[0] + "_____" + args[1]  +"_____" + args[2] , 2);
            String targetPath = PathHelper.getFile("Downloads");
            targetPath += File.separator;
            int fs = Integer.parseInt(args[1]);
            Socket sock = new Socket(args[2], 1718);
            byte[] mybytearray = new byte[fs];
            InputStream is = sock.getInputStream();
            String path = targetPath + args[0];
            String outputdatei = path;
            FileOutputStream fos = new FileOutputStream(path);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            bos.write(mybytearray, 0, bytesRead);
            bos.close();
            sock.close();

        } catch (IOException ex)
        {
            return false;
        } catch (fileSystemException ex)
        {
            Logger.getLogger(FiletransferClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
