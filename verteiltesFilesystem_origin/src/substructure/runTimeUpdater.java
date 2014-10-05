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

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class runTimeUpdater {
    private fileSystem c = fileSystem.getInstance();
    
    public runTimeUpdater()
    {
        Timer timer = new Timer();

        // Start in one second then drain every 5 seconds
        timer.schedule(new JTreeCreator(), 1000, 5000 );
    }
    
    class JTreeCreator extends TimerTask{
        
        @Override public void run()
        {
            
            
           

            String ips[] = c.getAllIps();
            //out.print(" explorer clientscount"+c.getClientCount());
            //out.print("Es werden " + c.getClientCount() + " Clienten indexiert. \n");

            for(int i = 0; i < c.getClientCount(); i++)
            {
                try {

                    gui.Explorer.initExplorerTree(c.get(ips[i]), ips[i]);
                } catch (IOException ex) {
                   // out.print(ex, 3);
                }
            }

        }
        
    }
}
