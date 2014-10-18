/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import gui.Config;
import java.io.File;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class syncFilesystems implements Runnable
{

    private String fullPath = "", filename = "myFileList.ser";
    private static GUIOutput out = GUIOutput.getInstance();

    public syncFilesystems()
    {
        try
        {
            fullPath = substructure.PathHelper.getFile(filename);
        } catch (fileSystemException ex)
        {
            out.print(ex.toString());
        }

    }

    @Override
    public void run()
    {
        out.print("(syncFileSystems) start");

        List<String> ips = IPList.getIPList();

        for (String ip : ips)
        {
            if (PingServer.PingServer(ip) && !ip.equals(Config.getCurrentIp()))
            {
                if (downloadFSbyIP(ip))
                {
                    fileSystem fs = fileSystem.getInstance();
                    fs.mergeList();
                } else
                {
                    out.print("Ein sync Fehler mit folgender IP trat auf: " + ip);
                }
            }
        }

        out.print("(syncFileSystems) standart gemäß beendet");

    }

    private boolean downloadFSbyIP(String IP)
    {
        try
        {
            boolean trigger = true;
            do
            {
                if (Interfaces.interfaceFileTransfer(IP, null, filename, "inComingList.ser"))
                {
                    do
                    {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(syncFilesystems.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }while( FiletransferClient.isTransferReady() != true);
                    trigger = false;
                }

            } while (trigger);

            return true;

        } catch (UnknownHostException ex)
        {
            out.print(ex.toString());
            return false;
        }

    }
}
