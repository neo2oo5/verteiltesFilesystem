/**
 * Package
 */
package network;

/**
 * Imports
 */
import java.io.*;
import java.net.*;
import gui.Config;
import substructure.GUIOutput;

/**
 * Klasse SSDPNetworkClient
 * 
 * Diese Klasse dient UPNP/SSDP client to demonstrate the usage of UDP multicast sockets.
 * 
 * @author David Lampa, Kevin Bonner
 * @version 1.0
 * 
 
 */
public class SSDPNetworkClient implements Runnable
{
    /**
     * Variablen Initialisieren
     */
    MulticastSocket socket;
    final int port = 1900;
    static GUIOutput outMsg = GUIOutput.getInstance();

    /**
     * UPNP/SSDP client to demonstrate the usage of UDP multicast sockets.
     *
     * @throws IOException
     */
    public void multicast() throws IOException
    {
        try
        {
            InetAddress multicastAddress = InetAddress.getByName("239.255.255.250");
            // standard port for SSDP
            socket = new MulticastSocket(port);
            socket.setReuseAddress(true);
            socket.setSoTimeout(25000);
            socket.joinGroup(multicastAddress);

            //send NOTIFY
            byte[] txbuf = LOGIN_MESSAGE_ROOTDEVICE.getBytes("UTF-8");
            DatagramPacket hi = new DatagramPacket(txbuf, txbuf.length,
                    multicastAddress, port);
            socket.send(hi);
            outMsg.print("(SSDPNetwork) SSDP alive sent");

            // send discover
            txbuf = DISCOVER_MESSAGE_ROOTDEVICE.getBytes("UTF-8");
            hi = new DatagramPacket(txbuf, txbuf.length,
                    multicastAddress, port);
            socket.send(hi);
            outMsg.print("(SSDPNetwork) SSDP discover sent");

            //empfange
            do
            {
                byte[] rxbuf = new byte[8192];
                DatagramPacket packet = new DatagramPacket(rxbuf, rxbuf.length);
                socket.receive(packet);
                dumpPacket(packet);
            } while (true); // should leave loop by SocketTimeoutException
        } catch (SocketTimeoutException e)
        {
            outMsg.print("(SSDPNetwork) Timeout");
            if (socket != null)
            {
                socket.close();
            }
        }
    }

    private void dumpPacket(DatagramPacket packet) throws IOException
    {
        String addr = packet.getAddress().getHostAddress();
        
        //adresse in liste eintrage
        ByteArrayInputStream in = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
        if (checkifFileSystem(in) && !addr.equals(Config.getCurrentIp()) && !IPList.SearchIP(addr))
        {

            String Cnetmask = Config.getCurrentIp();
            Cnetmask = Cnetmask.substring(0, Cnetmask.lastIndexOf("."));

            String netmask = addr;
            netmask = netmask.substring(0, netmask.lastIndexOf("."));

            //in ip liste eintragen
            if (Cnetmask.equals(netmask))
            {
                IPList.InsertIpInList(addr);
                PingServer.PingServer(addr);
                String ownIP = gui.Config.getCurrentIp();
                String doWhat = "CheckAdminLoggedin";
                String[] args = new String[3];
                args[0] = addr;
                args[1] = ownIP;
                args[2] = doWhat;
                StartClientServer.startClient(args);
                outMsg.print("(SSDPNetwork) Folgende IP in Liste eingetragen: " + addr + "\n");
            }
        }
    }

    private boolean checkifFileSystem(InputStream in) throws IOException
    {

        String line;
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(inputStreamReader);

        while ((line = br.readLine()) != null)
        {
            if (line.contains("FileSystem"))
            {
                return true;
            }
        }

        return false;
    }

    private final static String DISCOVER_MESSAGE_ROOTDEVICE
            = "M-SEARCH * HTTP/1.1\r\n"
            + "ST: upnp:rootdevice\r\n"
            + "MX: 3\r\n"
            + "MAN: \"ssdp:discover\"\r\n"
            + "HOST: 239.255.255.250:1900\r\n\r\n";

    private final static String LOGIN_MESSAGE_ROOTDEVICE
            = "NOTIFY * HTTP/1.1\r\n"
            + "NT: \"ssdp:alive\"\r\n"
            + "SERVER: FileSystem\r\n"
            + "HOST: 239.255.255.250:1900\r\n\r\n";

    private final static String LOGOUT_MESSAGE_ROOTDEVICE
            = "NOTIFY * HTTP/1.1\r\n"
            + "NT: \"ssdp:byebye\"\r\n"
            + "SERVER: FileSystem\r\n"
            + "HOST: 239.255.255.250:1900\r\n\r\n";

    
    @Override
    public void run()
    {
        try
        {
            multicast();
        } catch (IOException ex)
        {
            outMsg.print("(SSDPNetwork) " + ex.toString(), 3);
        }
    }
}
