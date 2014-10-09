/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 * Used Libraries
 */
import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
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
        try
        {
            sleep(20000);
        } catch (InterruptedException ex)
        {
        out.print("(CheckWhoIsOnline) Sleep Error", 2);
        }
        out.print("(CheckWhoIsOnline) Starte Initialisierung der Rechner im Lokalen Netzwerk", 1);
        out.print("(CheckWhoIsOnline) Ihre IP: " + ipv4, 1);
        int anzahl = 0;
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
        Thread bc = new Thread(new BroadcastBroadcaster());
        bc.start();
      
//        String[] sip = ipv4.split(Pattern.quote("."));
//        String uIP = sip[0] + "." + sip[1] + "." + sip[2] + ".";
//        /**
//         * check wich ip is online in the network
//         */
//        int endung = 0;
//        while (endung < 256)
//        {
//            String uebIP = uIP + endung;
//            reachable = PingServer(uebIP);
//            /**
//             * write the found IP's in our address table
//             */
//            if (reachable)
//            {
//                if (!uebIP.equals(ipv4))
//                {
//                    out.print("(CheckWhoIsOnline) Folgende IP im Lokalen Netzwerk gefunden: " + uebIP, 1);
//
//                    // check ob bereits in liste
//                    String iplist = null;
//                    try
//                    {
//                        iplist = substructure.PathHelper.getFile("IPs.txt");
//                    } catch (fileSystemException ex)
//                    {
//                        out.print("(CheckWhoIsOnline) : " + ex.toString(), 2);
//                    }
//                    int anz = 0;
//                    String anServer = null;
//                    // check ob schon einer eingeloggt
//                    BufferedReader in = null;
//                    try
//                    {
//                        in = new BufferedReader(new FileReader(iplist));
//                    } catch (FileNotFoundException ex)
//                    {
//                        out.print("(CheckWhoIsOnline) : " + ex.toString(), 2);
//                    }
//                    String ip = null;
//                    boolean chkVorhanden = false;
//                    try
//                    {
//                        while ((ip = in.readLine()) != null)
//                        {
//                            if (ip.equals(uebIP))
//                            {
//                                chkVorhanden = true;
//                            }
//                        }
//                    } catch (IOException ex)
//                    {
//                        out.print("(CheckWhoIsOnline) : " + ex.toString(), 2);
//                    }
//                    // falls noch nicht in eigener liste
//                    if (!chkVorhanden)
//                    {
//                        // solange noch datei inComingList.ser existiert warten
//                        boolean chk = false;
//                        while (!chk)
//                        {
//                            try
//                            {
//                                String fileCheck = substructure.PathHelper.getFile("inComingList.ser");
//                                try
//                                {
//                                    out.print("(CheckWhoIsOnline) inComingList.ser - Existiert noch, Warten bis andere Aktion Fertig ", 1);
//                                    sleep(100);
//                                } catch (InterruptedException ex)
//                                {
//                                    out.print("(CheckWhoIsOnline) Sleep fehlgeschlagen " + ex, 2);
//                                }
//
//                            } catch (fileSystemException ex)
//                            {
//                                chk = true;
//                            }
//                        }
//                        file = new File(path);
//
//                        try
//                        {
//                            writer = new FileWriter(file, true);
//
//                            writer.write(uebIP);
//                            writer.write(System.getProperty("line.separator"));
//                            writer.flush();
//                            writer.close();
//                            /**
//                             * catch exceptions
//                             */
//                        } catch (IOException e)
//                        {
//                            out.print("(CheckWhoIsOnline - run) : " + e.toString(), 2);
//                        }
//                        out.print("(CheckWhoIsOnline) IP in Liste eingetragen: " + uebIP, 1);
//                        Interfaces.interfaceNewClient(uebIP, ipv4);
//                        out.print("(CheckWhoIsOnline) eigene IP in Liste des gefundenen Rechners eingetragen: " + uebIP, 1);
//
//                        String pathDBneuerOrdner = null;
//                        try
//                        {
//                            pathDBneuerOrdner = substructure.PathHelper.getFolder("tmp");
//                        } catch (fileSystemException ex)
//                        {
//                            out.print("(CheckWhoIsOnline) " + ex.toString(), 2);
//                        }
//                        try
//                        {
//                            Interfaces.interfaceFileTransfer(uebIP, pathDBneuerOrdner, "myFileList.ser");
//                        } catch (UnknownHostException ex)
//                        {
//                            out.print("(CheckWhoIsOnline) " + ex.toString(), 2);
//                        }
                        // Datei umbenennen
//                            fileSystem fs = fileSystem.getInstance();
//                        try
//                        {
//                            fs.renameOutGoingObject();
//                        } catch (IOException ex)
//                        {
//                            out.print("(CheckWhoIsOnline) " + ex.toString(), 2);
//                        } catch (fileSystemException ex)
//                        {
//                            out.print("(CheckWhoIsOnline) " + ex.toString(), 2);
//                        }
//                        // mergen
//                        String inComingList = null;
//                        try {
//                            inComingList = substructure.PathHelper.getFile("inComingList.ser");
//                        } catch (fileSystemException ex) {
//                            out.print("(CheckWhoIsOnline) " + ex.toString(), 2);
//                        }
//                        try
//                        {
//                            fs.mergeList(inComingList);
//                        } catch (fileSystemException ex)
//                        {
//                            Logger.getLogger(CheckWhoIsOnline.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (IOException ex)
//                        {
//                            Logger.getLogger(CheckWhoIsOnline.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (ClassNotFoundException ex)
//                        {
//                            Logger.getLogger(CheckWhoIsOnline.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        try
//                        {
//                            // Datei Löschen
//                            fs.deleteInComingObject();
//                        } catch (IOException ex)
//                        {
//                            out.print("(CheckWhoIsOnline) " + ex.toString(), 2);
//                        } catch (fileSystemException ex)
//                        {
//                            out.print("(CheckWhoIsOnline) " + ex.toString(), 2);
//                        }
//                    }
//                }
//
//            }
//            /**
//             * jump to the next address in the network
//             */
//            endung++;
//
//        }
        out.print("(CheckWhoIsOnline) Initialisierung der Rechner im Lokalen Netzwerk abgeschlossen", 1);
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
            socket.connect(sockaddr, 200);

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
