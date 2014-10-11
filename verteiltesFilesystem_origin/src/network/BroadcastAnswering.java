/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import gui.Config;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static network.BroadcastBroadcaster.out;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class BroadcastAnswering extends Thread
{

    private static final int ECHO_PORT = 5555;
    private volatile boolean serverClose = true;

    static GUIOutput out = GUIOutput.getInstance();
    Thread client = null;
    DatagramSocket udpSocket = null;
    DatagramPacket packet;

    public BroadcastAnswering(Thread client)
    {
        this.client = client;
    }

    @Override
    public void run()
    {
        
        try
        {
            while (serverClose)
            {
                
                    if (client.isInterrupted() || !client.isAlive())
                    {
                        out.print("WhoIs Server gestartet");
                        byte[] buffer = new byte[1024];
                        packet = new DatagramPacket(buffer, buffer.length);

                        udpSocket = new DatagramSocket(ECHO_PORT);
                        udpSocket.setBroadcast(true);
                        udpSocket.receive(packet);

                        //empfängt broadcast
                        InetAddress sendeAdresse = packet.getAddress();
                        out.print("Nachricht von " + sendeAdresse.getHostAddress() + ":");
                        out.print(new String(packet.getData(), 0, packet.getLength()));
                        IPList.InsertIpInList(sendeAdresse.getHostAddress());

                        //
                        out.print("Sende Antwort.. ");
                        String antwort = Config.getCurrentIp();
                        packet = new DatagramPacket(antwort.getBytes(), antwort.length(), sendeAdresse, ECHO_PORT);
                        udpSocket.send(packet);
                        out.print("Antwort gesendet!");
                    }
                
            }

        } catch (IOException ex)
        {
            out.print("(BroadcastAnswering) " + ex.toString(), 2);

        } finally
        {
            if (udpSocket != null)
            {
                udpSocket.close();
            }
            
        }
        out.print("Whois Server standart gemäß beendet");
    }

    public void closeConnection()
    {
        serverClose = false;
        if (udpSocket != null)
        {
            
            udpSocket.close();
            
            
        }
        client.interrupt();
        this.interrupt();
        
    }

}
