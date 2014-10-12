package network;

import gui.Config;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SSDPNetworkClient implements Runnable {
    MulticastSocket socket ;
  /**
   * UPNP/SSDP client to demonstrate the usage of UDP multicast sockets.
   * @throws IOException
   */
  public void multicast() throws IOException {
    try {
      InetAddress multicastAddress = InetAddress.getByName("239.255.255.250"); 
      // multicast address for SSDP
      final int port = 1900; // standard port for SSDP
      socket = new MulticastSocket(port); 
      socket.setReuseAddress(true);
      socket.setSoTimeout(25000);
      socket.joinGroup(multicastAddress);
      
      

      //send NOTIFY
      byte[] txbuf = LOGIN_MESSAGE_ROOTDEVICE.getBytes("UTF-8");
      DatagramPacket hi = new DatagramPacket(txbuf, txbuf.length,
          multicastAddress, port);
      socket.send(hi);
      System.out.println("SSDP alive sent");
      
      // send discover
      txbuf = DISCOVER_MESSAGE_ROOTDEVICE.getBytes("UTF-8");
      hi = new DatagramPacket(txbuf, txbuf.length,
          multicastAddress, port);
      socket.send(hi);
      System.out.println("SSDP discover sent");

      //empfange
    do {
        byte[] rxbuf = new byte[8192];
        DatagramPacket packet = new DatagramPacket(rxbuf, rxbuf.length);
        socket.receive(packet);
        dumpPacket(packet);
      } while (true); // should leave loop by SocketTimeoutException
    } catch (SocketTimeoutException e) {
      System.out.println("Timeout");
      if(socket != null)
      {
          socket.close();
      }
    }
  }
  
   private void dumpPacket(DatagramPacket packet) throws IOException {
    String addr = packet.getAddress().getHostAddress();
    //System.out.println("Response from: " + addr);
    //adresse in liste eintrage
    ByteArrayInputStream in = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
    if(checkifFileSystem(in) && !addr.equals(Config.getCurrentIp()) && !IPList.SearchIP(addr))
    {
        //in ip liste eintragen
        IPList.InsertIpInList(addr);
        PingServer.PingServer(addr);
        System.out.print("Folgende IP in Liste eingetragen: " + addr + "\n");
    }
  }

  private boolean checkifFileSystem(InputStream in) throws IOException {
    
      String line;
      InputStreamReader inputStreamReader = new InputStreamReader(in);
      BufferedReader br = new BufferedReader(inputStreamReader);
    
    while ((line = br.readLine()) != null) {
        if(line.contains("FileSystem"))
        {
            return true;
        }
    }
    
    return false;
  }

  private final static String DISCOVER_MESSAGE_ROOTDEVICE =
    "M-SEARCH * HTTP/1.1\r\n" +
    "ST: upnp:rootdevice\r\n" +
    "MX: 3\r\n" +
    "MAN: \"ssdp:discover\"\r\n" + 
    "HOST: 239.255.255.250:1900\r\n\r\n";
  
  private final static String LOGIN_MESSAGE_ROOTDEVICE =
    "NOTIFY * HTTP/1.1\r\n" +
    "NT: \"ssdp:alive\"\r\n" + 
    "SERVER: FileSystem\r\n" +
    "HOST: 239.255.255.250:1900\r\n\r\n";
    
  private final static String LOGOUT_MESSAGE_ROOTDEVICE =
    "NOTIFY * HTTP/1.1\r\n" +
    "NT: \"ssdp:byebye\"\r\n" + 
    "SERVER: FileSystem\r\n" +
    "HOST: 239.255.255.250:1900\r\n\r\n";

  /**
   * MAIN
   */
 /* public static void main(String[] args) throws Exception {
    SSDPNetworkClient client = new SSDPNetworkClient();
    client.multicast();
  }*/
  
  @Override public void run() {
        try {
            multicast();
        } catch (IOException ex) {
            Logger.getLogger(SSDPNetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
}