/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.Explorer;



import java.util.*;
import java.nio.file.*;
import fileSystem.fileSystem;
import java.io.*;
import java.util.regex.Pattern;
import javax.swing.tree.DefaultMutableTreeNode;
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
    private static javax.swing.JTabbedPane pane;
    /**
     *
     * @param Pane
     * @return
     */
    public Explorer(javax.swing.JTabbedPane Pane)
    {   
        
            this.pane = Pane;
            initExplorerTree();
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
    
    private static List<String> changePathSeperator(String IP)
    {
        
       List<Path> fs = c.get(IP);
        
       List<String> tmp = new ArrayList<>();
       for (int i = 0; i < fs.size(); i++) {
           
           String path = fs.get(i).toString();
           //System.out.print(fs.get(i).toString().substring(0, 1));
           String tmps="";
           
           if(path.length() > 0)
           {
                if(path.substring(0, 1).equals("/"))
                {
                    //linux pfade anpassen zu
                    if(PathHelper.getOSName() == "Windows")
                    {
                        path = path.substring(1, path.length());
                        tmps = IP + path.replace("/", "\\");
                    }
                    else
                    {
                        tmps = path.substring(1, path.length());
                    }

                }
                else
                {
                    //windows pfade anpassen zu
                    if(PathHelper.getOSName() == "Linux")
                    {
                        tmps = path.replace("\\", "/");
                    }
                    else
                    {
                        tmps = path;
                    }
                }
                
                
                tmp.add(tmps);
           }
       }
       
       return tmp;
    }
    
    public static synchronized void initExplorerTree() 
   {
       
       treePanel = new DynamicTree(pane);
        
       
            
            
            List<String> tmp;



            String ips[] = c.getAllIps();



             for(int z = 0; z < c.getClientCount(); z++)
             {


                tmp = changePathSeperator(ips[z]);

                int index = treePanel.childIndex(treePanel.getRootNode(), ips[z]);
                DefaultMutableTreeNode parent;
                
                //neue ip
               // System.out.print(index);
                if (index < 0) {
                
                    parent = treePanel.addObject(treePanel.getRootNode(), ips[z]);

                }
                //ip existiert bereits
                else
                {
                     parent = treePanel.getObjectAtIndex(treePanel.getRootNode(), index);
                     //System.out.print(parent.getUserObject());
                    //parent = treePanel.addObject(entry, ips[z]);
                    
                }
                
                //Created the Tree Structure in Explorer
                for (int i = 0; i < tmp.size(); i++) {

                   treePanel.buildTreeFromString(parent, tmp.get(i).toString());

                }
                
                treePanel.removeOldFsEntrys(tmp, ips[z]);

             }

          
        
   }
    

    
  

   
}
