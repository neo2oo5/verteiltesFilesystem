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
    
    
    private static GUIOutput out = GUIOutput.getInstance();
    
    public CheckWhoIsOnline()
    {
        Thread ba = new Thread(new SSDPNetworkClient());
        ba.setName("Multicast");
        ba.start();
    }
    
    
    
   
    
    public static String getBroadcastAdress()
    {
        String broadcastAdress = Config.getCurrentIp();
       // out.print();
        broadcastAdress = broadcastAdress.substring(0, broadcastAdress.lastIndexOf("."));
        return broadcastAdress += ".255";
        
    }
    
    
    
    
            
}
