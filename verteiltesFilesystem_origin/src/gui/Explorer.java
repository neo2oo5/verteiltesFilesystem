/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;



import java.util.*;
import java.nio.file.*;
import fileSystem.fileSystem;
import java.io.*;
import substructure.GUIOutput;



/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class Explorer
{
    private static GUIOutput out =  GUIOutput.getInstance();
    private fileSystem c = fileSystem.getInstance();
    private static DynamicTree treePanel;
    /**
     *
     * @param Pane
     * @return
     */
    public Explorer(javax.swing.JTabbedPane Pane)
    {     
        
       
        
            treePanel = new DynamicTree(Pane);
            
        
    }
    
    /**
     *
     * @param Pane
     * @param index
     */
    public void addTab(javax.swing.JTabbedPane Pane, int index)
    {
        treePanel.addTab(Pane, index);
    }
    
    private void startRefreshTimer()
    {
        
        
        
       // JTreeCreator c = new JTreeCreator();
        //c.run();
    }
    
 public static void initExplorerTree(List<Path> fs,  String IP) throws IOException
        {
            List<String> tmp = new ArrayList<>();
            for (int i = 0; i < fs.size(); i++) {
                if(fs.get(i).toString().substring(0, 1).equals("/"))
                {
                    tmp.add(IP + fs.get(i).toString());
                }
                else
                {
                    tmp.add(IP + File.separator + fs.get(i).toString());
                }
            }
            
            try {
                //Created the Tree Structure in Explorer
                for (int i = 0; i < tmp.size(); i++) {
                   
                   treePanel.buildTreeFromString(tmp.get(i).toString());
                   
                }
                

           } catch (DirectoryIteratorException ex) {
               // I/O error encounted during the iteration, the cause is an IOException
               throw ex.getCause();
           }
        }
    

    
  

   
}
