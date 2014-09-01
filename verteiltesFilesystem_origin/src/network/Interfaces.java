/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;
/** Used Libraries */
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Lamparari
 */
public class Interfaces
{

    public static int interfaceFileTransfer(String IPv4, String sourcePath, String targetPath)
    {
        /* Interface to handle the filetransfer */
        return 1;
    }

    public static int interfaceFileRename(String IPv4, String sourcePath, String oldFilename, String newFilename)
    {
        /* interface to rename a file with the necessary values */
        String doWhat = "FileRename";
        /** arguments to rename the file */
        String[] args = new String[5];
        args[0] = IPv4;
        args[1] = sourcePath;
        args[2] = oldFilename;
        args[3] = newFilename;
        args[4] = doWhat;
        StartClientServer.startClient(args);
        /** if successfully */
        return 1; 
    }

    public static int interfaceFileDelete(String IPv4, String sourcePath, String filename)
    {
        /** interface to delete a file/directory */
        String doWhat = "FileDelete";
        /** arguments needed */
        String[] args = new String[4];
        args[0] = IPv4;
        args[1] = sourcePath;
        args[2] = filename;
        args[3] = doWhat;
        StartClientServer.startClient(args);
        /** if successfully */
        return 1;
    }

    public static int interfaceFileCreate(String IPv4, String sourcePath, String filename)
    {
        /** interface to create a file */
        String doWhat = "FileCreate";
        /** arguments needed */
        String[] args = new String[4];
        args[0] = IPv4;
        args[1] = sourcePath;
        args[2] = filename;
        args[3] = doWhat;
        StartClientServer.startClient(args);
        /** if successfully */
        return 1;
    }
    
    public static boolean interfaceCheckServerOnline(String IPv4)
    {
        /** interface to get every client IP in the network */
        return CheckWhoIsOnline.PingServer(IPv4);
    }
    
    /** method to start the interfaces */
    public  static boolean inerfaceStartProgram()
    {
        boolean succes = false;
        /** start the server */
        StartClientServer.startServer();
        String ip = null;
        /** get the IP address and catch exceptions */
        try
        {
            ip = getIPv4Address.getIPv4Address();
        } catch (SocketException | UnknownHostException ex)
        {
            /** no succes for an unknown host */
            succes = false;
        }
        /** success if an IP was found with CheckWhoIsOnline */
        if (ip != null)
        {
            CheckWhoIsOnline.CheckWhoIsOnline(ip);
            succes = true;
        }
        /** tell the client that the connection to the server was successful */
        return succes;
    }
}
