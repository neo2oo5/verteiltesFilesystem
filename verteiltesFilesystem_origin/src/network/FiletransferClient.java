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
import static network.Interfaces.out;

import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class FiletransferClient
{

    static GUIOutput out = GUIOutput.getInstance();
    static int fileSize = 0;

    public static int getFileSize()
    {
        return fileSize;
    }

    public static void setFileSize(int fileSize)
    {
        FiletransferClient.fileSize = fileSize;
    }

    public static boolean FileTransferClient(String[] args)
    {
        try
        {
            String doWhat3 = "fileSize";
            String[] args3 = new String[3];
            args3[0] = args[0];
            args3[1] = args[3]; // name
            args3[2] = doWhat3;
            out.print("start get FileSize", 1);
            StartClientServer.startClient(args3);

            out.print("FileTransferClient startet", 1);
            Socket sock = new Socket(args[0], 1718);
            byte[] mybytearray = new byte[(int) getFileSize()];
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
