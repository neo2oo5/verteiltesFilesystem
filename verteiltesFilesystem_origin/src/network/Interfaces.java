/**
 * Package
 */
package network;

/**
 * Imports
 */
import gui.Config;
import java.util.List;
import java.io.IOException;
import fileSystem.fileSystem;
import substructure.GUIOutput;
import java.net.UnknownHostException;
import static network.IPList.getIPList;

/**
 * Klasse Interfaces
 * 
 * Diese Klasse dient als Schnittstelle für alle Package's
 * die gewünschte Aktion auszuführen 
 * oder sich mit einem User zu verbinden um dort die gewünschte Aktion auszuführen
 * 
 * 
 * @author David Lampa
 * @version 1.0
 * 
 */
public class Interfaces
{
    /**
     * Variablen Initialisieren
     */
    static GUIOutput outMsg = GUIOutput.getInstance();
    static fileSystem c = fileSystem.getInstance();

    /**
     *
     * @param IPv4
     * @param path //  null wenn im System ordner über Pathhelper pfad geholt
     * wird
     * @param filename
     * @param clientFilename
     * @return
     * @throws UnknownHostException
     */
    public static boolean interfaceFileTransfer(String IPv4, String path, String filename, String clientFilename) throws UnknownHostException
    {
        boolean succesful = false;
        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            outMsg.print("Network Offline or You get Kicked from Network", 3);
        } else if (PingServer.PingServer(IPv4) == false)
        {
        } else
        {
            String IPv4target = null;
            IPv4target = Config.getCurrentIp();
            if (IPv4target.isEmpty())
            {
            } else
            {
                try
                {
                    String doWhat = "FileTransfer";
                    String[] args = new String[7];
                    args[0] = IPv4;
                    args[1] = path; // path , null => file in System ordner
                    args[2] = filename; // name
                    args[3] = gui.Config.getCurrentIp();
                    args[4] = clientFilename;
                    args[5] = IPv4;
                    args[6] = doWhat;
                    Thread client = StartClientServer.startClient(args);

                    do
                    {
                        Thread.sleep(500);
                    } while (client.isAlive());

                    return true;

                } catch (Exception ex)
                {
                    outMsg.print("(Interfaces) FileTransfer " + ex, 3);
                }

            }
        }
        return succesful;
    }

    /**
     *
     * @param IPv4
     * @param sourcePath
     * @param oldFilename
     * @param newFilename
     * @return
     * @throws UnknownHostException
     */
    public static int interfaceFileRename(String IPv4, String sourcePath, String oldFilename, String newFilename) throws UnknownHostException
    {
        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            outMsg.print("(Interface - FileRename) : " + "Network Offline or You get Kicked from Network", 3);
        } else if (PingServer.PingServer(IPv4) == false)
        {
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

    /**
     *
     * @param IPv4
     * @param sourcePath
     * @param filename
     * @return
     * @throws UnknownHostException
     */
    public static int interfaceFileDelete(String IPv4, String sourcePath, String filename) throws UnknownHostException
    {

        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            outMsg.print("(Interface - FileDelete) : " + "Network Offline or You get Kicked from Network", 3);
        } else if (PingServer.PingServer(IPv4) == false)
        {
        } else
        {
            /**
             * interface to delete a file/directory
             */
            String doWhat = "FileDelete";
            String[] args = new String[4];
            args[0] = IPv4;
            args[1] = sourcePath;
            args[2] = filename;
            args[3] = doWhat;
            StartClientServer.startClient(args);
        }
        return 1;
    }

    /**
     *
     * @param IPv4
     * @param sourcePath
     * @param filename
     * @return
     * @throws UnknownHostException
     */
    public static int interfaceFileCreate(String IPv4, String sourcePath, String filename) throws UnknownHostException
    {

        boolean kicked = CheckKicked.checkKicked();
        if (kicked)
        {
            outMsg.print("(Interface - FileCreate) : " + "Network Offline or You get Kicked from Network", 3);
        } else if (PingServer.PingServer(IPv4) == false)
        {
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

    /**
     *
     * @param IPv4
     * @return
     */
    public static boolean interfaceCheckServerOnline(String IPv4)
    {
        /**
         * interface to get every client IP in the network
         */
        return PingServer.PingServer(IPv4);
    }

    /**
     * method to start the interfaces
     *
     * @return
     * @throws java.net.UnknownHostException
     */
    public static boolean interfaceStartProgram() throws UnknownHostException
    {
        boolean succes = false;
        getIPv4Address.setIPv4Address();
        StartClientServer.startServer();
        String ip = Config.getCurrentIp();
        IPList.InsertIpInList(ip);
        outMsg.print("(Interface) - StartProgram -> Ihre IP: " + ip);
        CheckWhoIsOnline.doMulticast();
        return true;
    }

    /**
     * method to start the interfaces
     *
     * @return
     */
    public static boolean interfaceNetworkOnline()
    {
        boolean online = false;

        try
        {
            boolean kicked = CheckKicked.checkKicked();
            if (kicked)
            {
                outMsg.print("(Interface - NetworkOnline) : " + "Network Offline or You get Kicked from Network", 3);
            } else
            {

                if (getIPList().size() > 1)
                {
                    online = true;
                } else
                {
                    online = false;
                }
            }
        } catch (UnknownHostException ex)
        {
            outMsg.print("(Interface - NetworkOnline) : " + ex.toString(), 3);
        }
        return online;
    }

    /**
     *
     * @return
     */
    public static boolean interfaceAdminLogin()
    {
        return AdminPannel.adminLogin();
    }

    /**
     *
     * @return @throws UnknownHostException
     */
    public static boolean interfaceAdminLogout() throws UnknownHostException
    {
        try
        {
            return AdminPannel.adminLogout();
        } catch (IOException ex)
        {
            outMsg.print("(Interface - AdminLogout) : " + ex.toString(), 2);
        }
        return false;
    }

    /**
     *
     * @param ipToKick
     */
    public static void interfaceAdminKickUser(String ipToKick)
    {
        try
        {
            AdminPannel.adminKickUser(ipToKick);
            c.remove(ipToKick);
        } catch (IOException ex)
        {
            outMsg.print("(Interface - AdminKickUser) : " + ex.toString(), 2);
        }
    }

    /**
     *
     * @return
     */
    public static boolean interfaceIAmAdmin()
    {
        return AdminPannel.IAmAdmin();
    }

    /**
     *
     * @param oldIP
     * @param newIP
     * @throws UnknownHostException
     */
    public static void interfaceChangeOwnIP(String oldIP, String newIP) throws UnknownHostException
    {
        gui.Config.setCurrentIp(newIP);

        List<String> IPList = getIPList();

        for (String ip : IPList)
        {
            String doWhat = "ChangeOwnIP";
            String[] args = new String[4];
            args[0] = ip;
            args[1] = oldIP;
            args[2] = newIP;
            args[3] = doWhat;
            StartClientServer.startClient(args);

        }

        CheckWhoIsOnline.doMulticast();

    }

    /**
     *
     * @throws UnknownHostException
     */
    public static void interfaceExitProg() throws UnknownHostException
    {

        for (String ip : getIPList())
        {
            if (!ip.equals(gui.Config.getCurrentIp()))
            {
                String doWhat = "Exit";
                String[] args = new String[3];
                args[0] = ip;
                args[1] = gui.Config.getCurrentIp();
                args[2] = doWhat;
                StartClientServer.startClient(args);
            }

        }
        IPList.clearList();
        boolean admin = interfaceIAmAdmin();
        if (admin)
        {
            interfaceAdminLogout();
        }
    }

    /**
     *
     * @param clientIP
     * @param ownIP
     */
    public static void interfaceNewClient(String clientIP, String ownIP)
    {
        outMsg.print("(interfaceNewClient) start");
        String doWhat = "newClient";
        String[] args = new String[3];
        args[0] = clientIP;
        args[1] = ownIP;
        args[2] = doWhat;
        StartClientServer.startClient(args);
    }

    /**
     *
     */
    public static void interfaceMergeList()
    {
        Thread fs = null;

        boolean trigger = true;

        while (trigger)
        {
            if (fs == null)
            {
                trigger = false;
                fs = new Thread(new syncFilesystems());
            } else if (!fs.isAlive())
            {
                trigger = false;
                fs = new Thread(new syncFilesystems());
            }
        }

        fs.setName("(syncFilesystems)");
        fs.start();
    }

    /**
     *
     */
    public static void interfaceRestartMulticast()
    {
        CheckWhoIsOnline.doMulticast();
    }

    /**
     *
     */
    public static void interfecDoPingTest()
    {
        CheckWhoIsOnline.doPingTest();
    }
}
