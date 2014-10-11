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
import java.util.logging.Level;
import java.util.logging.Logger;
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
            ipListPath = GetIPListPath();
            File file = new File(ipListPath);
            
            
            if(IPtoInsert == null)
            {
                FileWriter writer = new FileWriter(file, false);
                writer.close();
                
            }
            else
            {
                if(!SearchIP(IPtoInsert))
                {
                    FileWriter writer = new FileWriter(file, true);
                    writer.write(IPtoInsert);
                    writer.write(System.getProperty("line.separator"));
                    writer.flush();
                    writer.close();
                }
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

    public static void replaceIP(String searchedIP, String toReplaceIP)
    {
        FileWriter writer = null;
        try {
            
            ArrayList<String> IPList = getIPList();
            
            IPList.add(toReplaceIP);
            IPList.remove(searchedIP);
            
            ipListPath = GetIPListPath();
            File file = new File(ipListPath);
            writer = new FileWriter(file, false);

            for(String ip: IPList)
            { 
                writer.write(ip);  
            }   
 
        } catch (IOException ex) {
            out.print(ex.toString());
            
        } finally {
            try {
                writer.close();
                
            } catch (IOException ex) {
                out.print(ex.toString());
            }
        }
        
        
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
