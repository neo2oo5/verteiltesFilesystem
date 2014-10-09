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
import java.net.SocketException;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class BroadcastAnswering implements Runnable
{

    private static final int ECHO_PORT = 1919;
    static GUIOutput out = GUIOutput.getInstance();

    @Override
    public void run()
    {
        DatagramSocket udpSocket = null;

        try
        {
            udpSocket = new DatagramSocket(ECHO_PORT);
            udpSocket.setBroadcast(true);
            while (true)
            {
                out.print("(BroadcastAnswering) startet", 1);
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                udpSocket.receive(packet);

                InetAddress sendeAdresse = packet.getAddress();
                System.out.print("Nachricht von " + Config.getCurrentIp() + ":");
                System.out.println(new String(packet.getData(), 0, packet.getLength()));

            }
        } catch (SocketException ex)
        {
            out.print("(BroadcastAnswering) Zeile 38 " + ex.toString(), 3);
        } catch (IOException ex)
        {
            out.print("(BroadcastAnswering) Zeile 57 " + ex.toString(), 3);
        }

    }
}
