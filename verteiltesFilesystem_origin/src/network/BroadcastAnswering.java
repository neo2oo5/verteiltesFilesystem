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

/**
 *
 * @author Lamparari
 */
public class BroadcastAnswering
{

    private static final int ECHO_PORT = 1337;

    public static void BroadcastAnswering()
    {
        DatagramSocket udpSocket = null;
        try
        {
            udpSocket = new DatagramSocket(ECHO_PORT);
            udpSocket.setBroadcast(true);
            while (true)
            {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                udpSocket.receive(packet);
                InetAddress sendeAdresse = packet.getAddress();
                System.out.print("Nachricht von " + Config.getCurrentIp() + ":");
                System.out.println(new String(packet.getData(), 0, packet.getLength()));

            }
        } catch (SocketException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            udpSocket.close();
        }
    }
}
