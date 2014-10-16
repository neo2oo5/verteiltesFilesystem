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
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class FiletransferServer
{

    static GUIOutput out = GUIOutput.getInstance();

    /**
     *
     * @param args
     */
    public static void FileTransferServer(String[] args)
    {
        try
        {
            IPList.InsertIpInList(args[2]);
            out.print("FileTransferServer startet", 1);
            ServerSocket servsock = new ServerSocket(1718);
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
                out.print("(FileTransferServer) " + ex, 2);
            }
            File myFile = new File(file);
            int fsi = (int) myFile.length();
            StringBuilder sb = new StringBuilder();
            sb.append(fsi);
            String strI = sb.toString();
            String[] argsClient = new String[5];
            argsClient[0] = args[2]; // IP Client
            argsClient[1] = args[3]; // neuerName
            argsClient[2] = strI; // größe datei
            argsClient[3] = args[4]; // ip Server
            argsClient[4] = "FileTransferClient";
            StartClientServer.startClient(argsClient);
            boolean transfer = true;
            while (transfer)
            {
                Socket sock = servsock.accept();
                byte[] mybytearray = new byte[(int) myFile.length()];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));

                bis.read(mybytearray, 0, mybytearray.length);
                OutputStream os = sock.getOutputStream();
                os.write(mybytearray, 0, mybytearray.length);
                os.flush();

                sock.close();
                servsock.close();
                transfer = false;
            }
        } catch (IOException ex)
        {
            out.print("(FileTransferServer) " + ex.toString(), 3);
        }
    }
}
