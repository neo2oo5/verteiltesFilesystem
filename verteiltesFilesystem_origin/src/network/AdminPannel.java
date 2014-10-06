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
            out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
        } catch (IOException | fileSystemException ex)
        {
            out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
        } finally
        {
            try
            {
                in.close();

            } catch (IOException ex)
            {
                out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
            }
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
        String iplist = null;
        try
        {
            iplist = substructure.PathHelper.getFile("IPs.txt");
        } catch (fileSystemException ex)
        {
            out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
        }
        int anzahl = 0;
        String anServer = null;
        String ownIP = network.getIPv4Address.getIPv4Address();
        // check ob schon einer eingeloggt
        BufferedReader in = null;
        try
        {
            in = new BufferedReader(new FileReader(iplist));
        } catch (FileNotFoundException ex)
        {
            out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
        }
        String ip = null;
        FileWriter writer;
        try
        {
            while ((ip = in.readLine()) != null)
            {
                String doWhat = "AdminMessage";
                String[] args = new String[3];
                args[0] = ip;
                args[1] = msg;
                args[2] = doWhat;
                StartClientServer.startClient(args);

            }
        } catch (IOException ex)
        {
            out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
        }
    }

    public static void adminKickUser(String ipToKick) throws UnknownHostException
    {
        String ownIP = null;
        ownIP = network.getIPv4Address.getIPv4Address();
        if (ipToKick.equals(ownIP))
        {
            out.print("Sie können sich nicht selbst Kicken!", 3);
        } else
        {
            String iplist = null;
            try
            {
                iplist = substructure.PathHelper.getFile("IPs.txt");
            } catch (fileSystemException ex)
            {
                out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
            }
            int anzahl = 0;
            String anServer = null;
            // check ob schon einer eingeloggt
            BufferedReader in = null;
            try
            {
                in = new BufferedReader(new FileReader(iplist));
            } catch (FileNotFoundException ex)
            {
                out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
            }
            String ip = null;
            FileWriter writer;
            try
            {
                while ((ip = in.readLine()) != null)
                {
                    String doWhat = "AdminKickUser";
                    String[] args = new String[3];
                    args[0] = ip;
                    args[1] = ipToKick;
                    args[2] = doWhat;
                    StartClientServer.startClient(args);

                }
            } catch (IOException ex)
            {
                out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
            }
        }
    }

    public static boolean IAmAdmin()
    {
        File file = null;
        try
        {
            file = new File(substructure.PathHelper.getFile("admin.loggedin"));
        } catch (fileSystemException ex)
        {
            out.print("(AdminPannel - adminCheckLogin) : " + ex.toString(), 2);
        }
        boolean exists = file.exists();
        return exists;
    }
}
