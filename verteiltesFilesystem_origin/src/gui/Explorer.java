/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;



import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import fileSystem.fileSystem;
import static fileSystem.fileSystem.find;
import fileSystem.fileSystemException;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import substructure.GUIOutput;



/**
 *
 * @author xoxoxo
 */
public class Explorer
{
    public  GUIOutput out =  GUIOutput.getInstance();
    private fileSystem  c;
    DynamicTree treePanel;
    /**
     *
     * @param Explorer
     * @return
     */
    public void init(javax.swing.JTabbedPane Pane)
    {      
        treePanel = new DynamicTree(Pane);
        startRefreshTimer();
    }
    
    private void startRefreshTimer()
    {
        
        
        Timer timer = new Timer();

        // Start in einer Sekunde dann Ablauf alle 5 Sekunden
        timer.schedule(new JTreeCreator(), 1000, 5000 );
       // JTreeCreator c = new JTreeCreator();
        //c.run();
    }
    
    class JTreeCreator extends TimerTask{
      
        @Override public void run()
        {
            treePanel.clear();
            c = fileSystem.getInstance();
           // GUIOutput out =  GUIOutput.getInstance();

            String ips[] = c.getAllIps();
            
            System.out.print("Es werden " + c.getClientCount() + " Clienten indexiert. \n");

            for(int i = 0; i < c.getClientCount(); i++)
            {
                try {

                    initExplorerTree(c.get(ips[i]), ips[i]);
                } catch (IOException ex) {
                   // out.print(ex, 3);
                }
            }

        }
    
        private void initExplorerTree(List<Path> fs,  String IP) throws IOException
        {
            try {
                DefaultMutableTreeNode rootNode = treePanel.addObject(IP);
               for (int i = 0; i < fs.size(); i++) {
                   Path tmp = fs.get(i);
                   //System.out.print(tmp.getFileName());
                    treePanel.addObject(rootNode, tmp.getFileName());
                }

           } catch (DirectoryIteratorException ex) {
               // I/O error encounted during the iteration, the cause is an IOException
               throw ex.getCause();
           }
        }
   }
    

    
  

   
}
