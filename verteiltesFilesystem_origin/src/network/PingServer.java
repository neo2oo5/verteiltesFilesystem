/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystem;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class PingServer
{

    static GUIOutput out = GUIOutput.getInstance();
    static fileSystem c = fileSystem.getInstance();

    /**
     *
     * @param checkIP
     * @return
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
        int counter = 0;
        boolean chk = true;
        do
        {
            try
            {
                SocketAddress sockaddr = new InetSocketAddress(checkIP, 1717);
                socket.connect(sockaddr, 1000);
                chk = true;
            } catch (IOException ex)
            {
                chk = false;
                counter++;
                if (counter == 4)
                {
                    out.print("IP: " + checkIP + " wurde aus dem Netzwerk entfernt, da nicht erreichbar!", 1);
                    IPList.removeIP(checkIP);
                    counter = 0;
                    return false;
                }
            }
        } while (!chk);
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
