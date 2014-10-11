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
    private static BroadcastAnswering server = null;
    
    public CheckWhoIsOnline()
    {
        server = server(client());
    }
    
    
    
    public Thread client()
    { //sent Broadcast
        
        Thread ba = new Thread(new BroadcastBroadcaster());
        ba.setName("BroadcastBroadcaster");
        ba.start();
        
        return ba;
    }
    
    public BroadcastAnswering server(Thread client)
    {   
        //receives broadcast
        BroadcastAnswering ba = new BroadcastAnswering(client);
        ba.setName("BroadcastAnswering");
        ba.start();
        
        return ba;
    }
    
    public static String getBroadcastAdress()
    {
        String broadcastAdress = Config.getCurrentIp();
       // out.print();
        broadcastAdress = broadcastAdress.substring(0, broadcastAdress.lastIndexOf("."));
        return broadcastAdress += ".255";
        
    }
    
    
    
    public static Thread serverClose()
    {
        if(server != null)
        {
            server.closeConnection();
            return server;
        }
        
        return null;
    }
            
}
