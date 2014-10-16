/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import gui.Config;
import java.net.UnknownHostException;

/**
 *
 * @author Lamparari
 */
public class CheckKicked
{

    /**
     *
     * @return @throws UnknownHostException
     */
    public static boolean checkKicked() throws UnknownHostException
    {
        return !IPList.SearchIP(Config.getCurrentIp());
    }
}
