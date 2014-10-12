/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystemException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
public class AdminPannel
{

    private static boolean loggedin;
    static GUIOutput out = GUIOutput.getInstance();

    public static boolean isLoggedin()
    {
        return loggedin;
    }

    public static void setLoggedin(boolean loggedin)
    {
        AdminPannel.loggedin = loggedin;
    }

    public static void adminCheckLogin()
    {

        
        String ownIP = gui.Config.getCurrentIp();
        List<String> IPListe = IPList.getIPList();
            
            for(String ip: IPListe)
        {
            String doWhat = "CheckAdminLoggedin";
            String[] args = new String[3];
            args[0] = ip;
            args[1] = ownIP;
            args[2] = doWhat;
            StartClientServer.startClient(args);
        }
    }

    public static boolean adminLogin()
    {
        adminCheckLogin();
        try
        {
            sleep(100);
        } catch (InterruptedException ex)
        {
            out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
        }
        if (loggedin == false)
        {
            FileWriter writer = null;
            try
            {
                String path = substructure.PathHelper.getFile("");
                File file = new File(path + "admin.loggedin");
                writer = new FileWriter(file, false);
                message("Admin Logged in!");
            } catch (IOException | fileSystemException ex)
            {
                out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
            } finally
            {
                try
                {
                    writer.close();
                } catch (IOException ex)
                {
                    out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
                }
            }
        } else
        {
            out.print("Es ist bereits ein Admin eingeloggt!", 3);
            return false;
        }
        return true;
    }

    public static boolean adminLogout() throws UnknownHostException
    {
        message("Admin Logged out!");

        try
        {
            Delete.deleteFile(substructure.PathHelper.getFile(""), "admin.loggedin");
        } catch (fileSystemException ex)
        {
            out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
        }

        return true;
    }

    public static void message(String msg) throws UnknownHostException
    {
        
        String ownIP = gui.Config.getCurrentIp();
        List<String> IPListe = IPList.getIPList();
            
            for(String ip: IPListe)
        {

            String doWhat = "AdminMessage";
            String[] args = new String[3];
            args[0] = ip;
            args[1] = msg;
            args[2] = doWhat;
            StartClientServer.startClient(args);
        }
    }

    public static void adminKickUser(String ipToKick) throws UnknownHostException
    {
        String ownIP = gui.Config.getCurrentIp();
        if (ipToKick.equals(ownIP))
        {
            out.print("Sie können sich nicht selbst Kicken!", 3);
        } else
        {

            List<String> IPListe = IPList.getIPList();
            
            for(String ip: IPListe)
            {
                String doWhat = "AdminKickUser";
                String[] args = new String[3];
                args[0] = ip;
                args[1] = ipToKick;
                args[2] = doWhat;
                StartClientServer.startClient(args);
            }
        }
    }

    public static boolean IAmAdmin()
    {
        File file = null;
        boolean check = false;
        try
        {
            file = new File(substructure.PathHelper.getFile("admin.loggedin"));
            check = file.exists();
        } catch (fileSystemException ex)
        {
            check = false;
        }
        return check;
    }
}
