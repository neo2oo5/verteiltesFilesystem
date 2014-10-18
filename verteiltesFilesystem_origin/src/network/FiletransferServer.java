/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystemException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
    static DynamicPorts dp = DynamicPorts.getInstance();

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
            
            dp.getPort(args[2]);
            
            int index = -1;
            do
            {
                index = dp.findIdent(dp.getIdentbyString(args[2]));
            }while(index == -1);
            
            int dynamicPort = Integer.valueOf(dp.getPortbyIndex(index));
            out.print(dynamicPort);
            
           // ServerSocket servsock = new ServerSocket(1718);
            Socket sock = new Socket(args[2], dynamicPort);
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
            
            //starte Client und gib file größe mit
            // sdfsfd
            File myFile = new File(file);
            int fsi = (int) myFile.length();
            StringBuilder sb = new StringBuilder();
            sb.append(fsi);
            String strI = sb.toString();
            String[] argsClient = new String[6];
            argsClient[0] = args[2]; // IP Client
            argsClient[1] = args[3]; // neuerName
            argsClient[2] = strI; // größe datei
            argsClient[3] = args[4]; // ip Server
            argsClient[4] = String.valueOf(dynamicPort);
            argsClient[5] = "FileTransferClient";
            
            StartClientServer.startClient(argsClient);
            
            
            // Get the size of the file
            int bufferSize = sock.getReceiveBufferSize();
            
            
            
//                  // Get the size of the file
            long length = myFile.length();
            if (length > Integer.MAX_VALUE) {
                System.out.println("File is too large.");
            }
            byte[] bytes = new byte[(int) length];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            BufferedOutputStream out = new BufferedOutputStream(sock.getOutputStream());

            int count;

            while ((count = bis.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }

            out.flush();
        } catch (IOException ex)
        {
            out.print("(FileTransferServer) " + ex.toString(), 3);
        }
    }
}
