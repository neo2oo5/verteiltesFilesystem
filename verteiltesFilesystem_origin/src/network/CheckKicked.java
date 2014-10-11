/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystemException;
import gui.Config;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import static network.AdminPannel.out;

/**
 *
 * @author Lamparari
 */
public class CheckKicked
{

    public static boolean checkKicked() throws UnknownHostException
    {
        
        return !IPList.SearchIP(Config.getCurrentIp());
    }
}
