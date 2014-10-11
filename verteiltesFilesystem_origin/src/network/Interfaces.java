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
import gui.Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class Interfaces
{

    // attr.
    static GUIOutput out = GUIOutput.getInstance();

    public static int interfaceFileTransfer(String IPv4, String targetPath, String filename) throws UnknownHostException
    {

        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("Network Offline or You get Kicked from Network", 3);
        } else
        {
            String IPv4target = null;
            IPv4target = Config.getCurrentIp();
            if (IPv4target.isEmpty())
            {
            } else
            {
                // do ...
                String doWhat = "FileTransfer";
                String[] args = new String[3];
                args[0] = IPv4;
                args[1] = filename; // name
                args[2] = doWhat;

                out.print("start Server", 3);
                StartClientServer.startClient(args);

                out.print("start client", 3);
                String[] args2 = new String[3];
                args2[0] = IPv4;
                args2[1] = filename; // name
                args2[2] = targetPath; // zielordner
                try
                {
                    FiletransferClient.FileTransferClient(args2);
                } catch (Exception ex)
                {
                    out.print("(Interfaces) newClient " + ex, 3);
                }

            }
        }
        return 1;
    }

    public static int interfaceFileRename(String IPv4, String sourcePath, String oldFilename, String newFilename) throws UnknownHostException
    {
        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("(Interface - FileRename) : " + "Network Offline or You get Kicked from Network", 3);
        } else if (CheckWhoIsOnline_old.PingServer(IPv4) == false)
        {
            out.print("(Interface - FileDelete) : " + "Client nicht verfügbar", 3);
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

        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("(Interface - FileDelete) : " + "Network Offline or You get Kicked from Network", 3);
        } else if (CheckWhoIsOnline_old.PingServer(IPv4) == false)
        {
            out.print("(Interface - FileDelete) : " + "Client nicht verfügbar", 3);
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

        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("(Interface - FileCreate) : " + "Network Offline or You get Kicked from Network", 3);
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
        return CheckWhoIsOnline_old.PingServer(IPv4);
    }

    /**
     * method to start the interfaces
     */
    public static boolean inerfaceStartProgram() throws UnknownHostException
    {
        boolean succes = false;
        getIPv4Address.setIPv4Address();
        StartClientServer.startServer();
        String ip = Config.getCurrentIp();
        IPList.InsertIpInList(ip);
        out.print("(Interface) - StartProgram -> Ihre IP: " + ip, 1);
        
        //////// Kevin deine CheckWhoIsOnline Zeile :)
        
        return true;
    }

    /**
     * method to start the interfaces
     */
    public static boolean inerfaceNetworkOnline() throws UnknownHostException
    {

        boolean online = false;
        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            out.print("(Interface - NetworkOnline) : " + "Network Offline or You get Kicked from Network", 3);
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

    public static void InterfaceChangeOwnIP(String oldIP, String newIP) throws UnknownHostException
    {
        String iplist = null;
        try
        {
            iplist = substructure.PathHelper.getFile("IPs.txt");
        } catch (fileSystemException ex)
        {
            out.print("(Interfaces - InterfaceChangeOwnIP) : " + ex.toString(), 2);
        }
        int anzahl = 0;
        String anServer = null;
        // check ob schon einer eingeloggt
        BufferedReader in = null;
        try
        {
            in = new BufferedReader(new FileReader(iplist));
        } catch (FileNotFoundException ex)
        {
            out.print("(Interfaces - InterfaceChangeOwnIP) : " + ex.toString(), 2);
        }
        String ip = null;
        FileWriter writer;
        try
        {
            while ((ip = in.readLine()) != null)
            {
                String doWhat = "ChangeOwnIP";
                String[] args = new String[4];
                args[0] = ip;
                args[1] = oldIP;
                args[2] = newIP;
                args[3] = doWhat;
                StartClientServer.startClient(args);

            }
        } catch (IOException ex)
        {
            out.print("(Interfaces - InterfaceChangeOwnIP) : " + ex.toString(), 2);
        }
    }

    public static void InterfaceExitProg() throws UnknownHostException
    {
        String ownIP = Config.getCurrentIp();
        String iplist = null;
        try
        {
            iplist = substructure.PathHelper.getFile("IPs.txt");
        } catch (fileSystemException ex)
        {
            out.print("(Interfaces - Exit) : " + ex.toString(), 2);
        }
        int anzahl = 0;
        String anServer = null;
        // check ob schon einer eingeloggt
        BufferedReader in = null;
        try
        {
            in = new BufferedReader(new FileReader(iplist));
        } catch (FileNotFoundException ex)
        {
            out.print("(Interfaces - Exit) : " + ex.toString(), 2);
        }
        String ip = null;
        FileWriter writer;
        try
        {
            while ((ip = in.readLine()) != null)
            {
                if (!ip.equals(ownIP))
                {
                    String doWhat = "Exit";
                    String[] args = new String[3];
                    args[0] = ip;
                    args[1] = ownIP;
                    args[2] = doWhat;
                    StartClientServer.startClient(args);
                }

            }
        } catch (IOException ex)
        {
            out.print("(Interfaces - Exit) : " + ex.toString(), 2);
        }
        String pathExit = null;
        try
        {
            pathExit = substructure.PathHelper.getFile("IPs.txt");
        } catch (fileSystemException ex)
        {
            out.print("(CheckWhoIsOnline) - Pfad nicht gefunden", 3);
        }
        File fileExit = new File(pathExit);
        FileWriter writerExit;
        try
        {
            writerExit = new FileWriter(fileExit, false);
        } catch (IOException ex)
        {
            out.print("(CheckWhoIsOnline)" + ex.toString(), 3);
        }
        CheckWhoIsOnline.serverClose();
    }

    public static void interfaceNewClient(String clientIP, String ownIP)
    {
        out.print("(interfaceNewClient) start");
        String doWhat = "newClient";
        String[] args = new String[3];
        args[0] = clientIP;
        args[1] = ownIP;
        args[2] = doWhat;
        StartClientServer.startClient(args);
    }
}
