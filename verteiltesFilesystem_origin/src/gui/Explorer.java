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
import substructure.Output;
import fileSystem.fileSystem;
import fileSystem.fileSystemException;


/**
 *
 * @author xoxoxo
 */
public class Explorer
{
    Output out = new Output();
    private fileSystem  c;
    /**
     *
     * @param Explorer
     * @return
     */
    public void init(javax.swing.JTabbedPane Pane)
    {
      
        DynamicTree treePanel = new DynamicTree(Pane);
        
        javax.swing.JTree tree = treePanel.getTree();
        
        
        
       /* tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                   tree.getLastSelectedPathComponent();

           
                if (node == null) return;

        
                Object nodeInfo = node.getUserObject();
                out.print(nodeInfo);
            }
        });*/
        
        
        
        String out="";
        try{
                

        
       
        
                c = fileSystem.getInstance();
                String s="";
                c.setnewFileSystem("127.0.0.1", "/home/xoxoxo");
               for (Path file_or_folder: c.get("127.0.0.1"))
               {
                   
                  DefaultMutableTreeNode p = treePanel.addObject(null , file_or_folder.getFileName());
                  if(c.getChild(file_or_folder) != null)
                  {
                    for (Path file_or_folder_child: c.getChild(file_or_folder))
                    {
                        if(Files.isDirectory(file_or_folder))
                        {
                          treePanel.addObject(p , file_or_folder.getFileName());
                        }
                    }
                  }
                  
               
               }
                } catch (fileSystemException ex) {
                    
        } 
        catch (DirectoryIteratorException ex) {
           // I/O error encounted during the iteration, the cause is an IOException
           //throw ex.getCause();
       }
        
        
        
        
        
        
        
        
        
        String p1Name = new String("Parent 1");
        String p2Name = new String("Parent 2");
        String c1Name = new String("Child 1");
        String c2Name = new String("Child 2");

        DefaultMutableTreeNode p1, p2;

        p1 = treePanel.addObject(null , p1Name);
        p2 = treePanel.addObject(null , p2Name);

       treePanel.addObject(p1, c1Name);
        treePanel.addObject(p1, c2Name);

        treePanel.addObject(p2, c1Name);
        treePanel.addObject(p2, c2Name);
         
        
       // Pane.addTab("Explorer", treePanel );
        
        
       
    }
    
    /**
     *
     * @param e
     */

   
}
