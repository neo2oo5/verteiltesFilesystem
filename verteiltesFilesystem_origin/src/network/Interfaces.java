/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 * Used Libraries
 */
import fileSystem.fileSystemException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.UnknownHostException;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class Interfaces
{

    static GUIOutput out = GUIOutput.getInstance();

    public static int interfaceFileTransfer(String IPv4, String sourcePath, String targetPath, String filename) throws UnknownHostException
    {

        boolean kicked = false;
        kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("You get Kicked from Network", 3);
        } else
        {

            String IPv4target = null;
            IPv4target = getIPv4Address.getIPv4Address();
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
                try
                {
                    sleep(100);
                } catch (InterruptedException ex)
                {
                    out.print("(Interface - FileTransfer) : " + ex.toString(), 2);
                }
                String dateiCheck = args[0] + args[2];
                File fileCheck = new File(dateiCheck);
                if (fileCheck.exists())
                {
                    out.print("(Interface - FileTransfer) : " + "File transfer complete", 1);
                } else
                {
                    out.print("(Interface - FileTransfer) : " + "File transfer not complete", 1);
                }
            }
        }
        return 1;
    }

    public static int interfaceFileRename(String IPv4, String sourcePath, String oldFilename, String newFilename) throws UnknownHostException
    {

        boolean kicked = false;
        kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("(Interface - FileRename) : " + "You get Kicked from Network", 3);
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

    public static int interfaceFileDelete(String IPv4, String sourcePath, String filename) throws UnknownHostException
    {

        boolean kicked = false;
        kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("(Interface - FileDelete) : " + "You get Kicked from Network", 3);
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

    public static int interfaceFileCreate(String IPv4, String sourcePath, String filename) throws UnknownHostException
    {

        boolean kicked = false;
        kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("(Interface - FileCreate) : " + "You get Kicked from Network", 3);
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
    public static boolean inerfaceStartProgram() throws UnknownHostException
    {
        boolean succes = false;

        /**
         * start the server
         */
        StartClientServer.startServer();
        String ip = null;
        ip = getIPv4Address.getIPv4Address();
        /**
         * success if an IP was found with CheckWhoIsOnline
         */
        if (ip != null)
        {
            System.out.println("Ihre IP: " + ip);
            Thread cwio = new Thread(new CheckWhoIsOnline(ip));
            cwio.start();
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
    public static boolean inerfaceNetworkOnline() throws UnknownHostException
    {

        boolean online = false;
        boolean kicked = false;
        kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("(Interface - NetworkOnline) : " + "You get Kicked from Network", 3);
        } else
        {

            String iplist = null;
            try
            {
                iplist = substructure.PathHelper.getFile("IPs.txt");
            } catch (fileSystemException ex)
            {
                out.print("(Interface - NetworkOnline) : " + ex.toString(), 2);
            }
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
                out.print("(Interface - NetworkOnline) : " + e.toString(), 2);
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

    public static boolean inerfaceAdminLogin()
    {
        return AdminPannel.adminLogin();
    }

    public static boolean inerfaceAdminLogout() throws UnknownHostException
    {
        try
        {
            return AdminPannel.adminLogout();
        } catch (IOException ex)
        {
            out.print("(Interface - AdminLogout) : " + ex.toString(), 2);
        }
        return false;
    }

    public static void inerfaceAdminKickUser(String ipToKick)
    {
        try
        {
            AdminPannel.adminKickUser(ipToKick);
        } catch (IOException ex)
        {
            out.print("(Interface - AdminKickUser) : " + ex.toString(), 2);
        }
    }

    public static boolean inerfaceIAmAdmin()
    {
        return AdminPannel.IAmAdmin();
    }

}
