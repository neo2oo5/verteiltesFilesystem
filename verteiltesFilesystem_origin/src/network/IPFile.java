/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystemException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class IPFile {
    private static String ipFile = null;
    private static GUIOutput out = GUIOutput.getInstance();
    
    private static void setIPFile()
    {
        try {
            ipFile = substructure.PathHelper.getFile("IPs.txt");
        } catch (fileSystemException ex) {
            Logger.getLogger(CheckWhoIsOnline.class.getName()).log(Level.SEVERE, null, ex);
        };
    }
    
    
    public static void setIPtoFile(String IP)
    {
        setIPFile();
        
        //read IP.txt File
        Deque<String> IPS = new ArrayDeque<>(); 
        boolean writeFile = true;
        
        
        try {
		Reader r = new InputStreamReader(new FileInputStream(ipFile), "UTF8");
                BufferedReader in = new BufferedReader(r);
		String zeile = null;
		while ((zeile = in.readLine()) != null) {
			IPS.add(zeile);
                        
		}
                
                in.close();
                r.close();
                
	} catch (IOException e) {
		out.print("(IPFile-Fehler): "+e.toString());
	}
        
        
        
      try {
        PrintWriter p = new PrintWriter (new FileWriter (ipFile));
        
        
            
            //write and check ip to File
            for (String itr : IPS) {
                
              if(itr.equals(IP))
              {
                  out.print("ip bereits in txt");
                  writeFile = false;
              }

            }
            

        if(writeFile)
        {
            out.print(IP + " in txt geschrieben");
            IPS.add(IP);
            
        }
        
        for (String itr : IPS) 
        {
                p.write(itr);
                p.write(System.getProperty("line.separator"));
                p.flush();
        }
        
        
        p.close();
      }
      catch (IOException e) {
        out.print("(IPFile-Fehler): "+e.toString());
      }
    }
    
    public static void removeIPfromFile(String IP)
    {
        setIPFile();
        
        //read IP.txt File
        Deque<String> IPS = new ArrayDeque<>(); 
       
        
        
        try {
		Reader r = new InputStreamReader(new FileInputStream(ipFile), "UTF8");
                BufferedReader in = new BufferedReader(r);
		String zeile = null;
		while ((zeile = in.readLine()) != null) {
			IPS.add(zeile);
                        
		}
                in.close();
                r.close();
                
	} catch (IOException e) {
		out.print("(IPFile-Fehler): "+e.toString());
	}
        
        
        
      try {
          
        PrintWriter p = new PrintWriter (new FileWriter (ipFile));
        IPS.remove(IP);
            
        
        
        for (String itr : IPS) 
        {
                p.write(itr);
                p.write(System.getProperty("line.separator"));
                p.flush();
               
        }
        
        
        p.close();
      }
      catch (IOException e) {
        out.print("(IPFile-Fehler): "+e.toString());
      }
    }
}
