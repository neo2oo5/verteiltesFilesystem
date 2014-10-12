/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystemException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public static synchronized void InsertIpInList(String IPtoInsert)
    {
        BufferedWriter fw = null;
        try
        {
            ipListPath = GetIPListPath();
            File file = new File(ipListPath);
            
            
            
              
                
            
            
                if(!SearchIP(IPtoInsert))
                {
                   fw = new BufferedWriter(new FileWriter(file, true));
                    synchronized (fw) {
                        fw.write(IPtoInsert);
                        fw.newLine();
                        fw.flush();
                        //writer.write(System.getProperty("line.separator"));
                    }
                    
                }
            
        } catch (IOException ex)
        {
            out.print("(InsertIpInList) " + ex.toString(), 3);
        }
        finally
        {
            
            try {
                if(fw != null)
                {
                    fw.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(IPList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void clearList()
    {
        ipListPath = GetIPListPath();
        File file = new File(ipListPath);
        FileWriter writer;
        try {
            writer = new FileWriter(file, false);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(IPList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static boolean SearchIP(String searchedIP)
    {
        boolean ipFound = false;
        
        List<String> IPListe = getIPList();

        for(String ip: IPListe)
        {
            if(ip.equals(searchedIP))
            {
                ipFound = true;
            }
        }
        
        return ipFound;
    }

    public static synchronized void removeIP(String searchedIP)
    {
        BufferedWriter fw   = null;
        try {
            
            List<String> IPList = getIPList();
            
            
            IPList.remove(searchedIP);
            
            ipListPath = GetIPListPath();
            File file = new File(ipListPath);
            fw = new BufferedWriter(new FileWriter(file, false));

            for(String ip: IPList)
            { 
                                
                fw.write(ip);
                fw.newLine();
                
                    
                
            }   
 
        } catch (IOException ex) {
            out.print(ex.toString());
            
        } finally {
            try {
                fw.flush();
                fw.close();
                
            } catch (IOException ex) {
                out.print(ex.toString());
            }
        }
    }
    
    
    public static synchronized void replaceIP(String searchedIP, String toReplaceIP)
    {
        FileWriter writer   = null;
        try {
            
            List<String> IPList = getIPList();
            
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

    public static List<String> getIPList()
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
        return Collections.synchronizedList(IPList);
    }

}
