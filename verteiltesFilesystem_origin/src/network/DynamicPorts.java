/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import gui.Config;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class DynamicPorts {
    
    
    private static List<Integer> ports         = new ArrayList<>();
    private static int portRangeMax            = 1750;
    private static int portRangeMin            = 1720;
    private static int index                   = -1;
    static GUIOutput out                       = GUIOutput.getInstance();
   
    
    private DynamicPorts() {
    }
    
    public static DynamicPorts getInstance() {
        return DynamicPortsHolder.INSTANCE;
    }
    
    private static class DynamicPortsHolder {

        private static final DynamicPorts INSTANCE = new DynamicPorts();
    }
    
    private static int generatingPort()
    {
        
        int rand=0;
      

           rand = (int) (Math.random() * (portRangeMax - portRangeMin) + portRangeMin);
         

        System.out.print("port: " + rand);
        return rand;
        
    }
    
    public static int setPort(int port)
    {
        boolean trigger = true;
        index = -1;
        
        for(int p: ports)
        {
            if(p == port)
            {
                trigger = false;
            }
        }
        
        if(trigger == true)
        {
            ports.add(port);
            index = ports.indexOf(port);
        }
        
        
        return index;
    }
    
    
    
    public static int getPort()
    {
        return generatingPort();
    }
    
    
    
    public static void releasePort(int port)
    {
        ports.remove(port);
    }
    
    public static void arrangePort(String IPv4to, String Port)
    {
            arrangePort(IPv4to, Port, false);
    
    }
    
    public static void arrangePort(String IPv4to, String Port, boolean back)
    {
        try {
            boolean kicked = CheckKicked.checkKicked();
            if (kicked)
            {
                out.print("(Interface - FileCreate) : " + "Network Offline or You get Kicked from Network", 3);
            } else if (PingServer.PingServer(IPv4to) == false)
            {
                out.print("IP " + IPv4to + "zur Zeit nicht erreichbar!", 3);
            } else
            {
                /**
                 * interface to create a file
                 */
                String doWhat = "arrangePort";
                /**
                 * arguments needed
                 */
                String[] args = new String[4];
                
                if(back)
                {
                    args[0] = IPv4to;
                    args[1] = Config.getCurrentIp();
                }
                else
                {
                    args[0] = Config.getCurrentIp();
                    args[1] = IPv4to;
                    
                }
                args[2] = String.valueOf(Port);
                args[3] = doWhat;
               
                StartClientServer.startClient(args);
                
                
                
                
            }   } catch (UnknownHostException ex) {
            Logger.getLogger(DynamicPorts.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    
}
