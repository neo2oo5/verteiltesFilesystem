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
import java.net.UnknownHostException;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class BroadcastBroadcaster implements Runnable
{

    private static final int ECHO_PORT = 1919;
    static GUIOutput out = GUIOutput.getInstance();

    @Override
    public void run()
    {
        DatagramSocket udpSocket = null;
        out.print("(BroadcastBroadcaster) startet", 1);
        try
        {
            //sendet broadcast
            udpSocket = new DatagramSocket(ECHO_PORT);
            udpSocket.setBroadcast(true);
            byte[] buffer = new String(Config.getCurrentIp()).getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("192.168.0.255"), ECHO_PORT);
            out.print("Sende Nachricht.");
            udpSocket.send(packet);
            
            
            //empfängt ip der anderen
            buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.setSoTimeout(5000);
            udpSocket.receive(packet);
 
            out.print("Antwort von " + packet.getAddress().getHostAddress() + ":");
            out.print(new String(packet.getData(), 0, packet.getLength()));
            IPFile.setIPtoFile(new String(packet.getData(), 0, packet.getLength()));
        } catch (SocketException ex)
        {
            out.print("(BroadcastBroadcaster) Zeile 52" + ex.toString(), 3);

        } catch (UnknownHostException ex)
        {
            out.print("(BroadcastBroadcaster) Zeile 56 " + ex.toString(), 3);

        } catch (IOException ex)
        {
            out.print("(BroadcastBroadcaster) Zeile 65 " + ex.toString(), 3);
        }finally
        {
           if(udpSocket != null)
           {
               udpSocket.close();
           }
        }

    }
}
