/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;



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
    private  GUIOutput out =  GUIOutput.getInstance();
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
    }
    
    public void add(String IP)
    {
         c = fileSystem.getInstance();
                
        try {
            
            initExplorerTree(c.get(IP), IP);
        } catch (IOException ex) {
            Logger.getLogger(Explorer.class.getName()).log(Level.SEVERE, null, ex);
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
