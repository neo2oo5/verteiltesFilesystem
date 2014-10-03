/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        BufferedReader in = null;
        try
        {
            String iplist = substructure.PathHelper.getFile("IPs.txt");
            int anzahl = 0;
            String anServer = null;
            String ownIP = network.getIPv4Address.getIPv4Address();
            // check ob schon einer eingeloggt
            in = new BufferedReader(new FileReader(iplist));
            String ip = null;
            FileWriter writer;
            while ((ip = in.readLine()) != null)
            {

                String doWhat = "CheckAdminLoggedin";
                String[] args = new String[3];
                args[0] = ip;
                args[1] = ownIP;
                args[2] = doWhat;
                StartClientServer.startClient(args);

            }
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(AdminPannel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(AdminPannel.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try
            {
                in.close();

            } catch (IOException ex)
            {
                Logger.getLogger(AdminPannel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void adminLogin() throws InterruptedException
    {
        adminCheckLogin();
        sleep(100);
        if (loggedin == false)
        {
            FileWriter writer = null;
            try
            {
                System.out.println("Variable: " + loggedin);
                System.out.println("japp ");
                String path = substructure.PathHelper.getFile("");
                File file = new File(path + "admin.loggedin");
                writer = new FileWriter(file, false);
                // message an alle
            } catch (IOException ex)
            {
                Logger.getLogger(AdminPannel.class.getName()).log(Level.SEVERE, null, ex);
            } finally
            {
                try
                {
                    writer.close();
                } catch (IOException ex)
                {
                    Logger.getLogger(AdminPannel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else
        {
            System.out.println("Admin Bereits eingeloggt!");
            //out.print("Admin Bereits eingeloggt!", 2);
        }
    }

    public static void adminLogout()
    {
        // nachricht an alle
        
        Delete.deleteFile(substructure.PathHelper.getFile(""), "admin.loggedin");
    }

    public void message(String message)
    {
    }
}
