/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;



import java.util.*;
import java.nio.file.*;
import javax.swing.tree.DefaultMutableTreeNode;
import fileSystem.fileSystem;
import java.io.*;
import substructure.GUIOutput;



/**
 *
 * @author xoxoxo
 */
public class Explorer
{
    private  GUIOutput out =  GUIOutput.getInstance();
    private fileSystem c = fileSystem.getInstance();
    DynamicTree treePanel;
    /**
     *
     * @param Explorer
     * @return
     */
    public Explorer(javax.swing.JTabbedPane Pane)
    {     
        
       
        
            treePanel = new DynamicTree(Pane);
            startRefreshTimer();
        
    }
    
    public void addTab(javax.swing.JTabbedPane Pane, int index)
    {
        treePanel.addTab(Pane, index);
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
            
            
           

            String ips[] = c.getAllIps();
            //out.print(" explorer clientscount"+c.getClientCount());
            //out.print("Es werden " + c.getClientCount() + " Clienten indexiert. \n");

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
            List<String> tmp = new ArrayList<>();
            for (int i = 0; i < fs.size(); i++) {
                if(fs.get(i).toString().substring(0, 1).equals("/"))
                {
                    tmp.add(IP + fs.get(i).toString());
                }
                else
                {
                    tmp.add(IP + "/" + fs.get(i).toString());
                }
            }
            
            try {
                
               // DefaultMutableTreeNode parent = treePanel.addObject(IP); 
                for (int i = 0; i < tmp.size(); i++) {
                    //System.out.print(tmp.get(i).toString() + "\n");
                   treePanel.buildTreeFromString(tmp.get(i).toString());
                   
                }
                

           } catch (DirectoryIteratorException ex) {
               // I/O error encounted during the iteration, the cause is an IOException
               throw ex.getCause();
           }
        }
        
        
   }
    

    
  

   
}
