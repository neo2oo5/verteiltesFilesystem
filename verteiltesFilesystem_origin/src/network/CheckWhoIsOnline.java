/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import gui.Config;
import java.util.regex.Pattern;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class CheckWhoIsOnline extends  Thread {
    
    private Thread multicast = null;
    private static GUIOutput out = GUIOutput.getInstance();
    
    public CheckWhoIsOnline()
    {
        restartMulticast();
    }
    
    private void restartMulticast()
    {
        if(multicast == null)
        {
            multicast = new Thread(new SSDPNetworkClient());
            multicast.setName("Multicast");
            multicast.start();
        }
        else
        {
        
            if(!multicast.isAlive())
            {
                multicast.start();
            }
        }
    }
    
   
    
    public static String getBroadcastAdress()
    {
        String broadcastAdress = Config.getCurrentIp();
       // out.print();
        broadcastAdress = broadcastAdress.substring(0, broadcastAdress.lastIndexOf("."));
        return broadcastAdress += ".255";
        
    }
    
    
    
    
            
}
