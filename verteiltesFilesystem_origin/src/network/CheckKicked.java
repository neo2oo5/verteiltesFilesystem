/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystemException;
import gui.Config;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import static network.AdminPannel.out;

/**
 *
 * @author Lamparari
 */
public class CheckKicked
{

    public static boolean checkKicked() throws UnknownHostException
    {

        /**
         * falls gekickt wurden
         */
        boolean kicked = true;

        try
        {

            String searchedip = null;
            searchedip = Config.getCurrentIp();

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
                out.print("(CheckKicked - checkKicked) : " + e.toString(), 2);
            }

        } catch (fileSystemException ex)
        {
            out.print("(CheckKicked - checkKicked) : " + ex.toString(), 2);
        }
        return kicked;
    }
}
