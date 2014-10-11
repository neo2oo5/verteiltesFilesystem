/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class syncFilesystems extends Thread {
    
    private String tmpFolder ="", filename = "inComingList.ser";
    private static GUIOutput out = GUIOutput.getInstance();
    
    public syncFilesystems()
    {
        try {
            tmpFolder = substructure.PathHelper.getFolder("");
        } catch (fileSystemException ex) {
            out.print(ex.toString());
        }
    }
    
    @Override
    public void run()
    {
        out.print("(syncFileSystems) start");
        
        ArrayList<String> ips = IPList.getIPList();
        
        for(String ip : ips)
        {
            if(downloadFSbyIP(ip))
            {
                fileSystem fs = fileSystem.getInstance();
                fs.mergeList();
            }
            else
            {
                out.print("Ein sync Fehler mit folgender IP trat auf: "+ip);
            }
        }
        
        out.print("(syncFileSystems) standart gemäß beendet");
        
    }
    
    private boolean downloadFSbyIP(String IP)
    {
        try {
            Interfaces.interfaceFileTransfer(IP, tmpFolder, filename);
        } catch (UnknownHostException ex) {
            out.print(ex.toString());
            return false;
        }
        
        return true;
    }
}
