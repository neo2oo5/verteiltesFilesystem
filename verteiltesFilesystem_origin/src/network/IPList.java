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
    private static String ipListPath;

    public static String GetIPListPath()
    {
        try
        {
            return PathHelper.getFile("IPs.txt");
        } catch (fileSystemException ex)
        {
            out.print("(GetIPListPath) " + ex.toString(), 3);
            return null;
        }
    }

    public static void InsertIpInList(String IPtoInsert)
    {
        try
        {
            if(!SearchIP(IPtoInsert))
            {
                ipListPath = GetIPListPath();
                File file = new File(ipListPath);
                FileWriter writer = new FileWriter(file, true);
                writer.write(IPtoInsert);
                writer.write(System.getProperty("line.separator"));
                writer.flush();
                writer.close();
            }
        } catch (IOException ex)
        {
            out.print("(InsertIpInList) " + ex.toString(), 3);
        }
    }

    public static boolean SearchIP(String searchedIP)
    {
        boolean ipFound = false;
        
        ArrayList<String> IPListe = getIPList();

        for(String ip: IPListe)
        {
            if(ip.equals(searchedIP))
            {
                ipFound = true;
            }
        }
        
        return ipFound;
    }

    public static boolean replaceIP(String searchedIP, String toReplaceIP)
    {
        boolean ipFound = false;
        ArrayList<String> IPListe = getIPList();
        ListIterator<String> li = IPListe.listIterator();
        while (li.hasNext())
        {
            if(li.toString().equals(searchedIP))InsertIpInList(toReplaceIP);
            else InsertIpInList(li.toString());
        }

        return ipFound;
    }

    public static ArrayList<String> getIPList()
    {
        ArrayList<String> IPList = new ArrayList<>();
        try
        {
            BufferedReader inFile = null;

            ipListPath = GetIPListPath();
            inFile = new BufferedReader(new FileReader(ipListPath));
            String ip = null;
            while ((ip = inFile.readLine()) != null)
            {
                IPList.add(ip);
            }
            inFile.close();
        } catch (FileNotFoundException ex)
        {
            out.print("(replaceIP) " + ex.toString(), 3);
        } catch (IOException ex)
        {
            out.print("(replaceIP) " + ex.toString(), 3);
        }
        return IPList;
    }

}
