/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 * Used Libraries
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class Interfaces
{

    static GUIOutput out = GUIOutput.getInstance();

    public static int interfaceFileTransfer(String IPv4, String sourcePath, String targetPath, String filename) throws InterruptedException
    {

        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("You get Kicked from Network", 3);
        } else
        {

            String IPv4target = null;
            try
            {
                IPv4target = getIPv4Address.getIPv4Address();
            } catch (SocketException ex)
            {
                Logger.getLogger(Interfaces.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex)
            {
                Logger.getLogger(Interfaces.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (IPv4target.isEmpty())
            {
            } else
            {
                // do ...
                String doWhat = "FileTransfer";
                String[] args = new String[6];
                args[0] = IPv4;
                args[1] = sourcePath; // quelle
                args[2] = targetPath; // ziel
                args[3] = filename;
                args[4] = IPv4target;
                args[5] = doWhat;

                File target = new File(args[2]);
                target.mkdirs();
                StartClientServer.startClient(args);
                sleep(100);
                String dateiCheck = args[0] + args[2];
                File fileCheck = new File(dateiCheck);
                if (fileCheck.exists())
                {
                    System.out.println("File transfer complete");
                } else
                {
                    System.out.println("File transfer not complete");
                }
            }
        }
        return 1;
    }

    public static int interfaceFileRename(String IPv4, String sourcePath, String oldFilename, String newFilename)
    {

        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("You get Kicked from Network", 3);
        } else
        {
            /* interface to rename a file with the necessary values */
            String doWhat = "FileRename";
            /**
             * arguments to rename the file
             */
            String[] args = new String[5];
            args[0] = IPv4;
            args[1] = sourcePath;
            args[2] = oldFilename;
            args[3] = newFilename;
            args[4] = doWhat;
            StartClientServer.startClient(args);
            /**
             * if successfully
             */
        }
        return 1;
    }

    public static int interfaceFileDelete(String IPv4, String sourcePath, String filename)
    {

        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("You get Kicked from Network", 3);
        } else
        {
            /**
             * interface to delete a file/directory
             */
            String doWhat = "FileDelete";
            /**
             * arguments needed
             */
            String[] args = new String[4];
            args[0] = IPv4;
            args[1] = sourcePath;
            args[2] = filename;
            args[3] = doWhat;
            StartClientServer.startClient(args);
            /**
             * if successfully
             */
        }
        return 1;
    }

    public static int interfaceFileCreate(String IPv4, String sourcePath, String filename)
    {

        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("You get Kicked from Network", 3);
        } else
        {
            /**
             * interface to create a file
             */
            String doWhat = "FileCreate";
            /**
             * arguments needed
             */
            String[] args = new String[4];
            args[0] = IPv4;
            args[1] = sourcePath;
            args[2] = filename;
            args[3] = doWhat;
            StartClientServer.startClient(args);
            /**
             * if successfully
             */
        }
        return 1;
    }

    public static boolean interfaceCheckServerOnline(String IPv4)
    {
        /**
         * interface to get every client IP in the network
         */
        return CheckWhoIsOnline.PingServer(IPv4);
    }

    /**
     * method to start the interfaces
     */
    public static boolean inerfaceStartProgram()
    {
        boolean succes = false;
        /**
         * start the server
         */
        StartClientServer.startServer();
        String ip = null;
        /**
         * get the IP address and catch exceptions
         */
        try
        {
            ip = getIPv4Address.getIPv4Address();
        } catch (SocketException | UnknownHostException ex)
        {
            /**
             * no succes for an unknown host
             */
            succes = false;
        }
        /**
         * success if an IP was found with CheckWhoIsOnline
         */
        if (ip != null)
        {
            System.out.println("Ihre IP: " + ip);
            CheckWhoIsOnline.CheckWhoIsOnline(ip);
            succes = true;
        }
        /**
         * tell the client that the connection to the server was successful
         */
        return succes;
    }

    /**
     * method to start the interfaces
     */
    public static boolean inerfaceNetworkOnline()
    {

        boolean online = false;
        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("You get Kicked from Network", 3);
        } else
        {

            String iplist = "/System/IPs.txt";
            int anzahl = 0;
            try
            {
                BufferedReader in = new BufferedReader(new FileReader(iplist));
                String ip = null;
                FileWriter writer;
                while ((ip = in.readLine()) != null)
                {
                    anzahl++;
                }

            } catch (IOException e)
            {
                e.printStackTrace();
            }

            if (anzahl > 1)
            {
                online = true;
            } else
            {
                online = false;
            }

        }
        return online;
    }

    public static void inerfaceAdminLogin()
    {
        try
        {
            AdminPannel.adminLogin();
        } catch (InterruptedException ex)
        {
            Logger.getLogger(Interfaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void inerfaceAdminLogout()
    {
        AdminPannel.adminLogout();
        
    }
}
