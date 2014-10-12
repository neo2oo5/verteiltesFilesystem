/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystemException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class FiletransferServer
{

    static GUIOutput out = GUIOutput.getInstance();

    public static void FileTransferServer(String[] args) throws IOException
    {
        out.print("FileTransferServer startet", 1);
        ServerSocket servsock = new ServerSocket(1718);
        String file = null;
        try
        {
            file = substructure.PathHelper.getFile(args[0]);
        } catch (fileSystemException ex)
        {
            out.print("(FileTransferServer) " + ex, 2);
        }
        File myFile = new File(file);
        while (true)
        {
            Socket sock = servsock.accept();
            byte[] mybytearray = new byte[(int) myFile.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
            bis.read(mybytearray, 0, mybytearray.length);
            OutputStream os = sock.getOutputStream();
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
            sock.close();
        }
    }

    public static int fileSize(String[] args)
    {
        int fileSize = 0;
        try
        {
            String file = null;
            file = substructure.PathHelper.getFile(args[0]);
            File myFile = new File(file);
            fileSize = (int) myFile.length();
        } catch (fileSystemException ex)
        {
            out.print("(FileTransferServer - fileSize) " + ex, 2);
        }
        return fileSize;
        
    }
}
