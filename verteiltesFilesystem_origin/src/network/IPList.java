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
import java.util.ArrayList;
import java.util.ListIterator;
import substructure.GUIOutput;
import substructure.PathHelper;

/**
 *
 * @author Lamparari
 */
public class IPList
{

    static GUIOutput out = GUIOutput.getInstance();
    private static String ipListPath = null;
    private static FileWriter writer = null;

    public static void GetIPListPath()
    {
        try
        {
            ipListPath = PathHelper.getFile("IPs.txt");
            out.print("NUR FÜR TESTS - IP PFAD: " + ipListPath, 1);
        } catch (fileSystemException ex)
        {
            out.print("(GetIPListPath) " + ex.toString(), 3);
        }
    }

    public static void InsertIpInList(String IPtoInsert)
    {
        try
        {
            GetIPListPath();
            File file = new File(ipListPath);
            writer = new FileWriter(file, true);
            writer.write(IPtoInsert);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
            writer.close();
        } catch (IOException ex)
        {
            out.print("(InsertIpInList) " + ex.toString(), 3);
        }
    }

    public static boolean SearchIP(String searchedIP)
    {
        boolean ipFound = false;
        BufferedReader inFile = null;
        try
        {
            GetIPListPath();
            inFile = new BufferedReader(new FileReader(ipListPath));
            String ip = null;
            while ((ip = inFile.readLine()) != null)
            {
                if (ip.equals(searchedIP))
                {
                    ipFound = true;
                }
            }
        } catch (FileNotFoundException ex)
        {
            out.print("(SearchIP) " + ex.toString(), 3);
        } catch (IOException ex)
        {
            out.print("(SearchIP) " + ex.toString(), 3);
        } finally
        {
            try
            {
                inFile.close();
            } catch (IOException ex)
            {
                out.print("(SearchIP) " + ex.toString(), 3);
            }
        }
        return ipFound;
    }

    public static boolean replaceIP(String searchedIP, String toReplaceIP)
    {
        boolean ipFound = false;
        try
        {
            BufferedReader inFile = null;
            ArrayList<String> IPList = new ArrayList<>();

            GetIPListPath();
            inFile = new BufferedReader(new FileReader(ipListPath));
            String ip = null;
            while ((ip = inFile.readLine()) != null)
            {
                if (ip.equals(searchedIP))
                {
                    if(!toReplaceIP.equals(null)) IPList.add(toReplaceIP);
                    ipFound = true;
                } else
                {
                    IPList.add(ip);
                }
            }
            inFile.close();
            File file = new File(ipListPath);
            writer = new FileWriter(file, false);
            ListIterator<String> li = IPList.listIterator();
            while (li.hasNext())
            {
                InsertIpInList(li.toString());
            }
        } catch (FileNotFoundException ex)
        {
                out.print("(replaceIP) " + ex.toString(), 3);
        } catch (IOException ex)
        {
                out.print("(replaceIP) " + ex.toString(), 3);
        }
        return ipFound;
    }

}