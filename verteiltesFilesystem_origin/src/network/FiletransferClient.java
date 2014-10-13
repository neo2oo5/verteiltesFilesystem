/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import substructure.GUIOutput;

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
            out.print("FileTransferClient startet", 1);
            Socket sock = new Socket(args[0], 1718);
            byte[] mybytearray = new byte[1000000000];
            InputStream is = sock.getInputStream();
            String path = args[2] + args[1];
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
        }
        return true;
    }
}
