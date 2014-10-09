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

    private static final int ECHO_PORT = 1818;
    static GUIOutput out = GUIOutput.getInstance();

    @Override
    public void run()
    {
        DatagramSocket udpSocket = null;
        out.print("(BroadcastBroadcaster) startet", 1);
        try
        {
            udpSocket = new DatagramSocket(ECHO_PORT);
        } catch (SocketException ex)
        {
        out.print("(BroadcastBroadcaster) Zeile 40" + ex.toString(), 3);
        }
        try
        {
            udpSocket.setBroadcast(true);
        } catch (SocketException ex)
        {
        out.print("(BroadcastBroadcaster) Zeile 47 " + ex.toString(), 3);
        }
        byte[] buffer = new String("Ist da jemand ?").getBytes();
        InetAddress byName = null;
        try
        {
            byName = InetAddress.getByName(Config.getCurrentIp());
        } catch (UnknownHostException ex)
        {
        out.print("(BroadcastBroadcaster) Zeile 56 " + ex.toString(), 3);
        }
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, byName, ECHO_PORT);
        System.out.println("Sende Nachricht.");
        try
        {
            udpSocket.send(packet);
        } catch (IOException ex)
        {
        out.print("(BroadcastBroadcaster) Zeile 65 " + ex.toString(), 3);
        }

    }
}
