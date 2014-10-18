/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.Explorer;



import java.util.*;
import java.nio.file.*;
import fileSystem.fileSystem;
import gui.Config;
import java.io.*;
import java.util.regex.Pattern;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import network.PingServer;
import substructure.GUIOutput;
import substructure.PathHelper;



/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class Explorer
{
    private static GUIOutput out =  GUIOutput.getInstance();
    private static fileSystem c = fileSystem.getInstance();
    private static DynamicTree treePanel;
    /**
     *
     * @param Pane
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
    
    private static void changePathSeperator(String IP, List<String> tmp)
    {
        
       List<Path> fs = c.get(IP);
        
       
       for (int i = 0; i < fs.size(); i++) {
           
           String path = fs.get(i).toString();
          
           String tmps="";
           
           tmps = ExplorerHelper.convertPath(path);
         //  System.out.print("IP: "+IP + " Path: " + path+"\n");
           tmp.add(tmps);
       }
       
       
    }
    
    /**
     *
     */
    public static synchronized void initExplorerTree() 
   {
       
        
        if (!Config.isRootDir())
        {
            Config.filechooser();
        } 
        else if(c.isAccessDenied(Config.getRootDir()))
        {
            Config.filechooser();
        }
        else
        {
            c.setNewFileSystem(Config.getCurrentIp(), Config.getRootDir());
        }
       
            
            
            



            List<String> ips = c.getAllIps();
            List<String> tmp = new ArrayList<>();

             for(int z = 0; z < c.getClientCount(); z++)
             {
                 
                 String currentIP = ips.get(z);
                 tmp.clear();
                 changePathSeperator(currentIP, tmp);

                
                   

               

                
               // System.out.print(parent.getUserObject()+"\n");
                //Created the Tree Structure in Explorer
                
                    int index = treePanel.childIndex(treePanel.getRootNode(), currentIP);
                    DefaultMutableTreeNode parent;

                    //neue ip
                    
                    if (index < 0) {

                        parent = treePanel.addObject(treePanel.getRootNode(), currentIP);

                    }
                    //ip existiert bereits
                    else
                    {
                         parent = treePanel.getObjectAtIndex(treePanel.getRootNode(), index);
                         
                           
                        
                         //System.out.print(parent.getUserObject());
                        //parent = treePanel.addObject(entry, ips[z]);

                    }

                    for (int i = 0; i < tmp.size(); i++) {
                      //  System.out.print("index: "+index+" parent: "+ parent.getUserObject()+" string: "+tmp.get(i).toString()+"\n");
                        treePanel.buildTreeFromString(parent, tmp.get(i).toString());
                    }

                



               
                
                    treePanel.removeOldFsEntrys(tmp, currentIP);
                

             }

          
             
   }
    

    
  

   
}
