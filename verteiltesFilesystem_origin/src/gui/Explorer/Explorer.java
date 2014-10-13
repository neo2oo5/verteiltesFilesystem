/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.Explorer;



import java.util.*;
import java.nio.file.*;
import fileSystem.fileSystem;
import static gui.Explorer.DynamicTree.rootNode;
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
    
    public static void initExplorerTree() 
   {

        treePanel.beginReload();

        List<String> tmp;

        
       
        String ips[] = c.getAllIps();
        
        

         for(int z = 0; z < c.getClientCount(); z++)
         {
             

            tmp = changePathSeperator(ips[z]);

            

            DefaultMutableTreeNode parent = treePanel.addObject(rootNode, ips[z]);


             //Created the Tree Structure in Explorer
             for (int i = 0; i < tmp.size(); i++) {

                treePanel.buildTreeFromString(parent, tmp.get(i).toString());

             }
         }

       
       
       treePanel.endReload();
   }
    

    
  

   
}
