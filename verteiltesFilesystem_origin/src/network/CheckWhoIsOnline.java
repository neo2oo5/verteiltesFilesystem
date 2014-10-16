/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystem;
import gui.Config;
import java.util.ArrayList;
import java.util.List;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class CheckWhoIsOnline extends Thread
{

    private static Thread multicast = null;
    private static GUIOutput out = GUIOutput.getInstance();
    private static fileSystem c = fileSystem.getInstance();
    private static List<String[]> ipList = new ArrayList<>();
    private static boolean init = false;

    private CheckWhoIsOnline()
    {
    }

    /**
     *
     */
    public static void doMulticast()
    {
        multicast = new Thread(new SSDPNetworkClient());
        multicast.setName("Multicast");
        
        if (multicast == null)
        {
            
            
            multicast.start();
        } else
        {

            if (!multicast.isAlive())
            {
                multicast.start();
            }
        }
    }

    /**
     *
     */
    public static void doPingTest()
    {
        initPingTest();

        List<String> ips = c.getAllIps();
        for (int z = 0; z < c.getClientCount(); z++)
        {
            String currentIP = ips.get(z);
            int index = -1;
            String[] ipSet = new String[2];

            //fetch index from ip array
            for (int i = 0; i < ipList.size(); i++)
            {
                ipSet = ipList.get(i);
                if (currentIP.equals(ipSet[1]))
                {
                    index = ipList.indexOf(i);
                }
            }
            //count +1
            if (index >= 0)
            {
                if (!PingServer.PingServer(currentIP) && !currentIP.equals(Config.getCurrentIp()))
                {

                    ipSet[0] = String.valueOf(Integer.getInteger(ipSet[0]) + 1);
                }

                ipList.set(index, ipSet);

                //remove off ip (Explorer, )
                for (int i = 0; i < ipList.size(); i++)
                {
                    String[] ipSetnew = new String[2];
                    ipSet = ipList.get(i);
                    System.out.print(ipSetnew[1]);
                    if (Integer.getInteger(ipSetnew[0]) >= 3)
                    {
                        //remove from fs
                        c.remove(ipSetnew[1]);
                        //remove from IPList
                        IPList.removeIP(ipSetnew[0]);

                    }
                }
            }

        }
    }

    private static void initPingTest()

    {
        if (init == false)
        {
            List<String> ips = c.getAllIps();
            for (int z = 0; z < c.getClientCount(); z++)
            {
                String currentIP = ips.get(z);
                String[] ipSet = new String[2];
                ipSet[0] = "1";
                ipSet[1] = currentIP;

                if (!PingServer.PingServer(currentIP))
                {
                    ipSet[0] = String.valueOf(Integer.getInteger(ipSet[0]) + 1);
                }

                ipList.add(ipSet);

            }

            init = true;
        }
    }

}
