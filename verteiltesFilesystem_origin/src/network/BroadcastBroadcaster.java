/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import gui.Config;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class BroadcastBroadcaster implements Runnable
{

    private static final int ECHO_PORT = 5555;
    static GUIOutput out = GUIOutput.getInstance();
    private volatile boolean closeClient = true;

    @Override
    public void run()
    {
        out.print("WhoIs Client gestartet");
        DatagramSocket udpSocket = null;
        out.print("(BroadcastBroadcaster) startet", 1);
        try
        {
            //sendet broadcast
            udpSocket = new DatagramSocket(ECHO_PORT);
            udpSocket.setBroadcast(true);
            byte[] buffer = new String(Config.getCurrentIp()).getBytes();
            String broadcastAdress = CheckWhoIsOnline.getBroadcastAdress();
            out.print("Sende Broadcast an: " + broadcastAdress);
            DatagramPacket packetsend = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(broadcastAdress), ECHO_PORT);
            out.print("Sende Nachricht.");
            udpSocket.send(packetsend);

            //empfängt ip der anderen
            buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.setSoTimeout(5000);
            udpSocket.receive(packet);

            int i = 0;
             while (closeClient)
            {
                if(i >= 2)
                {
                    closeClient = false;
                    break;
                }
                else if(Thread.currentThread().isInterrupted())
                {
                    closeClient = false;
                    break;
                }
                
                
                try
                {
                    udpSocket.receive(packet);
                    out.print("Antwort von " + packet.getAddress().getHostAddress() + ":");
                    out.print(new String(packet.getData(), 0, packet.getLength()));
                    IPList.InsertIpInList(new String(packet.getData(), 0, packet.getLength()));
                    break;
                } catch (SocketTimeoutException e)
                {

                    out.print("(BroadcastBroadcaster Versuch " + i + ") " + e.toString());

                    try
                    {
                        sleep(500);
                    } catch (InterruptedException ex)
                    {
                        out.print("(Interrupted) WhoIs Client wird beendet");
                        udpSocket.close();
                    }
                    i++;

                }
            };

        } catch (SocketException ex)
        {
            out.print("(BroadcastBroadcaster) " + ex.toString(), 3);

        } catch (UnknownHostException ex)
        {
            out.print("(BroadcastBroadcaster) " + ex.toString(), 3);

        } catch (IOException ex)
        {
            out.print("(BroadcastBroadcaster) " + ex.toString(), 3);
        } finally
        {
            if (udpSocket != null)
            {
                udpSocket.close();
            }

            
        }
        out.print("Whois Client standart gemäß beendet");
    }
}
