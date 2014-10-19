/**
 * Package
 */
package network;

/**
 * Imports
 */
import gui.Config;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import substructure.GUIOutput;
import java.net.UnknownHostException;

/**
 * Klasse DynamicPorts
 * 
 * Klasse die einen Port mit dem anderen User aushandelt
 * 
 * @author David Lampa, Kevin Bonner
 * @version 1.0
 */
public class DynamicPorts {
    
    /**
     * Variablen Initialisieren
     */
    private static final List<String[]> ports  = Collections.synchronizedList(new ArrayList<String[]>());
    private static int portRangeMax            = 1750;
    private static int portRangeMin            = 1720;
    private static int index                   = -1;
    static GUIOutput outMsg                    = GUIOutput.getInstance();
   
    
    private DynamicPorts() {
    }
    
    /**
     *
     * @return
     */
    public synchronized static DynamicPorts getInstance() {
        return DynamicPortsHolder.INSTANCE;
    }
    
    private static class DynamicPortsHolder {

        private static final DynamicPorts INSTANCE = new DynamicPorts();
    }
    
    private synchronized static int generatingPort()
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
    public synchronized static int setPort(String ident, int port)
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
    public synchronized static int findPort(int port)
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
    public synchronized static int findIdent(String Ident)
    {
        
        String[] returnS =null;
        
        for(String[] p: ports)
        {
            if(p[0].equals(String.valueOf(Ident)))
            {
                returnS = p;
            }
        }
        
        if(returnS != null)
        {
            return Integer.valueOf(returnS[1]);
        }
        else
        {
            return -1;
        }
    }

    /**
     *
     * @param index
     * @return
     */
    public synchronized static String getPortbyIndex(int index)
    {
        if(ports.size() > 0)
        {
            String[] tmp = ports.get(index);
            return tmp[1];
        }
        
        return null;        
    }
    
    /**
     *
     * @param IPto
     * @return
     */
    public synchronized static int getPort(String IPto)
    {
        arrangePort(IPto, String.valueOf(generatingPort())+".1");
        return Integer.valueOf(findIdent(getIdentbyString(IPto)));        
    }
    
    /**
     *
     * @param IP
     * @return
     */
    public synchronized static String getIdentbyString(String IP)
    {
        return IP.substring(IP.lastIndexOf(".") + 1, IP.length());
    }
    
    /**
     *
     * @param port
     */
    public synchronized static void releasePort(int port)
    {        
        for(int i = 0; i < ports.size(); i++)
        {
            String[] tmp = ports.get(i);
            if(tmp[1].equals(String.valueOf(port)))
            {
                ports.remove(tmp);
            }
        }        
    }
    
    /**
     *
     * @param IPv4to
     * @param Port
     */
    public synchronized static void arrangePort(String IPv4to, String Port)
    {
            arrangePort(IPv4to, Port, false);    
    }
    
    /**
     *
     * @param IPv4to
     * @param Port
     * @param back
     */
    public synchronized static void arrangePort(String IPv4to, String Port, boolean back)
    {
        try {
            boolean kicked = CheckKicked.checkKicked();
            if (kicked)
            {
                outMsg.print("(Interface - FileCreate) : " + "Network Offline or You get Kicked from Network", 3);
            } else if (PingServer.PingServer(IPv4to) == false)
            {
                outMsg.print("(Dynamic Ports) IP " + IPv4to + "zur Zeit nicht erreichbar!", 3);
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
            }   
        } catch (UnknownHostException ex) {
            outMsg.print("(Dynamic Ports) " + ex.toString(), 3);
        }    
    }
}
