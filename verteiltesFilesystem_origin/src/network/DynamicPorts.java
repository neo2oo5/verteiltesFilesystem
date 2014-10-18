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
    
    
    private static List<String[]> ports         = new ArrayList<>();
    private static int portRangeMax            = 1750;
    private static int portRangeMin            = 1720;
    private static int index                   = -1;
    static GUIOutput out                       = GUIOutput.getInstance();
   
    
    private DynamicPorts() {
    }
    
    /**
     *
     * @return
     */
    public static DynamicPorts getInstance() {
        return DynamicPortsHolder.INSTANCE;
    }
    
    private static class DynamicPortsHolder {

        private static final DynamicPorts INSTANCE = new DynamicPorts();
    }
    
    private static int generatingPort()
    {
        
        int rand=0;
        do
        {
            rand = (int) (Math.random() * (portRangeMax - portRangeMin) + portRangeMin);
        }while(ports.indexOf(rand) != -1);

         
         

        
        return rand;
        
    }
    
    /**
     *
     * @param ident
     * @param port
     * @return
     */
    public static int setPort(String ident, int port)
    {
        boolean trigger = true;
        String[] tmp = new String[2];
        tmp[0] = ident;
        index = -1;
        
        for(String[] p: ports)
        {
            if(p[1].equals(String.valueOf(port)))
            {
                trigger = false;
            }
        }
        
        if(trigger == true)
        {
            tmp[1] = String.valueOf(port);
            ports.add(tmp);
            
        }
        
        
        return findPort(port);
    }
    
    /**
     *
     * @param port
     * @return
     */
    public static int findPort(int port)
    {
        int index = -1;
        
        for(String[] p: ports)
        {
            if(p[1].equals(String.valueOf(port)))
            {
                index = ports.indexOf(p);
            }
            
        }
        
        return index;
        
    }
    
    /**
     *
     * @param Ident
     * @return
     */
    public static int findIdent(String Ident)
    {
        int index = -1;
        
        for(String[] p: ports)
        {
            if(p[0].equals(String.valueOf(Ident)))
            {
                index = ports.indexOf(p);
            }
            
        }
        
        return index;
        
    }

    /**
     *
     * @param index
     * @return
     */
    public static String getPortbyIndex(int index)
    {
        String[] tmp = ports.get(index);
        return tmp[1];
    }
    
    /**
     *
     * @param IPto
     * @return
     */
    public static int getPort(String IPto)
    {
        arrangePort(IPto, String.valueOf(generatingPort())+".1");
        return findIdent(getIdentbyString(IPto));
        
    }
    
    /**
     *
     * @param IP
     * @return
     */
    public static String getIdentbyString(String IP)
    {
        return IP.substring(IP.lastIndexOf(".") + 1, IP.length());
    }
    
    /**
     *
     * @param port
     */
    public static void releasePort(int port)
    {
        ports.remove(port);
    }
    
    /**
     *
     * @param IPv4to
     * @param Port
     */
    public static void arrangePort(String IPv4to, String Port)
    {
            arrangePort(IPv4to, Port, false);
    
    }
    
    /**
     *
     * @param IPv4to
     * @param Port
     * @param back
     */
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
                
                if(!back)
                {
                    args[0] = IPv4to;
                    args[1] = Config.getCurrentIp();
                }
                else
                {
                    args[0] = Config.getCurrentIp();
                    args[1] = IPv4to;
                    
                }
                args[2] = getIdentbyString(args[1]) + "." + Port;
                args[3] = doWhat;
               
                StartClientServer.startClient(args);
                
                
                
                
            }   } catch (UnknownHostException ex) {
            Logger.getLogger(DynamicPorts.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    
}
