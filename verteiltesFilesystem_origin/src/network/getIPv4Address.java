/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package network;

/**
 *
 * @author Michael Marchand, David Lampa
 */



/** Used Libraries **/
import java.net.InetAddress;          
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class getIPv4Address
{
    /**
     *
     * @throws java.net.SocketException
     * @throws java.net.UnknownHostException
     */
    public static String getIPv4Address() throws SocketException, UnknownHostException
    {   
        /** Search all network interfaces */
        Enumeration<NetworkInterface> netInter = NetworkInterface.getNetworkInterfaces();
        int n = 0;
        String IPv4 = null;
        /** Search for internet-adresses in the network table */
        while ( netInter.hasMoreElements() )
        {
            NetworkInterface ni = netInter.nextElement();
            int finder = 0;
            /** get the IP from every client who is listed in the network table */
            for ( InetAddress iaddress : Collections.list(ni.getInetAddresses()) )
            {   
                /** Search and format the router IP in the local network */
                if(iaddress.isLoopbackAddress() == false && iaddress.isSiteLocalAddress() == true) 
                {
                    String ip = iaddress.getHostAddress();
                    String[] segs = ip.split( Pattern.quote( "." ) );
                    if(segs[3].length() > 1)
                    {
                        finder = 1;
                        IPv4 = iaddress.getHostAddress();
                    }
                }
            }
        }
        
        return IPv4;
    }
}