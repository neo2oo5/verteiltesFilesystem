/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lamparari
 */
public class CheckKicked
{

    public static boolean checkKicked()
    {

        /**
         * falls gekickt wurden
         */
        boolean kicked = true;

        try
        {

            String searchedip = null;
            searchedip = getIPv4Address.getIPv4Address();

            String iplist = substructure.PathHelper.getFile("IPs.txt");

            int anzahl = 0;
            try
            {
                BufferedReader in = new BufferedReader(new FileReader(iplist));
                String ip = null;
                FileWriter writer;
                while ((ip = in.readLine()) != null)
                {
                    if (ip == searchedip)
                    {
                        kicked = false;
                    }
                }

            } catch (IOException e)
            {
                e.printStackTrace();
            }

        } catch (SocketException ex)
        {
            Logger.getLogger(CheckKicked.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex)
        {
            Logger.getLogger(CheckKicked.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kicked;
    }
}
