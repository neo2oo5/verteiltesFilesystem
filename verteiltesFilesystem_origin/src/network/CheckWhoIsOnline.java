/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

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
    
    
    
    public static void serverClose()
    {
        if(server != null)
        {
            server.closeConnection();
        }
    }
            
}
