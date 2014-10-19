/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import static network.IPList.getIPList;

/**
 *
 * @author Lamparari
 */
public class NetworkOnline
{

    private static int counter = 0;
    private static boolean msg = false;

    public static boolean isMsg()
    {
        return msg;
    }

    public static void setMsg(boolean msg)
    {
        NetworkOnline.msg = msg;
    }
    
    public static int getCounter()
    {
        return counter;
    }

    public static void setCounter()
    {
        NetworkOnline.counter = getCounter() + 1;
    }
    public static void setCounter(int counter)
    {
        NetworkOnline.counter = counter;
    }

    public static boolean isNetworkOnline()
    {
        boolean online = false;
        if (getIPList().size() > 1)
        {
            setCounter();
            if(getCounter() > 0) setMsg(false);
            return true;
        } else
        {
            if(getCounter() > 0) setMsg(true);
            setCounter(0);
            setMsg(false);
            return false;
        }
    }
}
