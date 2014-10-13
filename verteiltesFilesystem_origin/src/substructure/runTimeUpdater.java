/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package substructure;

import fileSystem.fileSystem;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import network.Interfaces;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class runTimeUpdater {
    private fileSystem c = fileSystem.getInstance();
    private static GUIOutput out =  GUIOutput.getInstance();
    
    public runTimeUpdater()
    {
        Timer timer = new Timer();

        // Start in one second then drain every 5 seconds
        // Update Explorer Folder structure
        timer.schedule(new JTreeCreator(), 1000, 5000 );
        
        timer.schedule(new GUIScheduler(), 1000, 2000 );
        
        timer.schedule(new GUILog(), 1000, 5000 );
        
        timer.schedule(new MulticastUpdater(), 20000, 50000 );
        
        timer.schedule(new syncFileSytems(), 1000, 30000 );
        
        out.print("runTimeUpdater gestarted");
    }
    
    class JTreeCreator extends TimerTask{
        
        @Override public void run()
        {
            String ips[] = c.getAllIps();

            for(int i = 0; i < c.getClientCount(); i++)
            {
                try {

                    gui.Explorer.Explorer.initExplorerTree(c.get(ips[i]), ips[i]);
                } catch (IOException ex) {
                   // out.print(ex, 3);
                }
            }
        }    
    }
    
    class GUIScheduler extends TimerTask
    {
        @Override public void run()
        {
           substructure.fileSystem_Start.gUI.setOnOffState();
           
        }
    }
    
    class GUILog extends TimerTask
    {
        @Override public void run()
        {
           if(out != null)
           {
            out.refreshGuiLog();
           }
        }
    }
    
    class syncFileSytems extends TimerTask
    {
        @Override public void run()
        {
           Interfaces.interfaceMergeList();
        }
    }

    class MulticastUpdater extends TimerTask
    {
        @Override public void run()
        {
           Interfaces.interfaceRestartMulticast();
        }
    }
}
