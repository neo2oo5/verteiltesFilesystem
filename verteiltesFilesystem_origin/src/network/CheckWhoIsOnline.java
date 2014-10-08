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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */

/* class to detect all clients who are online */
public class CheckWhoIsOnline implements Runnable
{

    private String ipv4;

    static GUIOutput out = GUIOutput.getInstance();

    /**
     *
     * @param ipv4
     */
    public CheckWhoIsOnline(String ipv4)
    {
        this.ipv4 = ipv4;
    }

    @Override
    public void run()
    {
        out.print("Starte Initialisierung der Rechner im Lokalen Netzwerk", 1);
        int anzahl = 0;
        int endung = 0;
        boolean reachable = false;
        /**
         * initiate the IP format
         */
        String path = null;
        try
        {
            path = substructure.PathHelper.getFile("IPs.txt");
        } catch (fileSystemException ex)
        {
            out.print("(CheckWhoIsOnline) - Pfad nicht gefunden", 3);
        }
        File file = new File(path);

        /**
         * write the IP in the address table
         */
        FileWriter writer;
        try
        {
            if (++anzahl == 1)
            {
                writer = new FileWriter(file, false);
            } else
            {
                writer = new FileWriter(file, true);
            }

            writer.write(ipv4);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
            writer.close();
            /**
             * catch exceptions
             */
        } catch (IOException e)
        {
            out.print("(CheckWhoIsOnline - run) : " + e.toString(), 2);
        }
        String[] sip = ipv4.split(Pattern.quote("."));
        String uIP = sip[0] + "." + sip[1] + "." + sip[2] + ".";
        /**
         * check wich ip is online in the network
         */
        while (endung < 256)
        {
            String uebIP = uIP + endung;
            reachable = PingServer(uebIP);
            /**
             * write the found IP's in our address table
             */
            if (reachable)
            {
                try
                {
//                     if(!uebIP.equals(ipv4)){
//                         String pathDBneuerOrdner = substructure.PathHelper.getFile("");
//                         try
//                         {
//                             out.print("asdasdasdadsadasd", 3);
//                             Interfaces.interfaceFileTransfer(uebIP, "", pathDBneuerOrdner, "myFileList.ser");
//                         } catch (UnknownHostException ex)
//                         {
//                             out.print("fehler--------------CheckWIO", 2);
//                             Logger.getLogger(CheckWhoIsOnline.class.getName()).log(Level.SEVERE, null, ex);
//                         }
//                     }
                    /**
                     * path and name for the found IP
                     */
                    if (!uebIP.equals(ipv4))
                    {
                        path = substructure.PathHelper.getFile("IPs.txt");
                        file = new File(path);

                        try
                        {
                            writer = new FileWriter(file, true);

                            writer.write(uebIP);
                            writer.write(System.getProperty("line.separator"));
                            writer.flush();
                            writer.close();
                            /**
                             * catch exceptions
                             */
                        } catch (IOException e)
                        {
                            out.print("(CheckWhoIsOnline - run) : " + e.toString(), 2);
                        }
                    }
                } catch (fileSystemException ex)
                {
                    out.print("(CheckWhoIsOnline - run) : " + ex.toString(), 2);

                }
            }
            /**
             * jump to the next address in the network
             */
            endung++;

        }
        out.print("Initialisierung der Rechner im Lokalen Netzwerk abgeschlossen", 1);
    }

    /**
     *
     * @param checkIP
     * @return boolean
     */
    public static boolean PingServer(String checkIP)
    {
        /**
         * create a new socket (Port 1717, name = checkIP)
         */
        Socket socket = new Socket();
        /**
         * try to connect to and address, timeout at 50 seconds
         */
        try
        {
            SocketAddress sockaddr = new InetSocketAddress(checkIP, 1717);
            socket.connect(sockaddr, 50);

        } catch (IOException ex)
        {
            //   out.print("(CheckWhoIsOnline - PingServer) : " + ex.toString(), 2);
            return false;
        }
        /**
         * close the socket, catch exceptions and return the value
         */
        try
        {
            socket.close();
        } catch (IOException ex)
        {
            out.print("(CheckWhoIsOnline - PingServer) : " + ex.toString(), 2);
            return false;
        }
        return true;
    }

}
