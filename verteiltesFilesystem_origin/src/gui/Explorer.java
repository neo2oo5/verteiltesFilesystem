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
import static fileSystem.fileSystem.find;
import fileSystem.fileSystemException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author xoxoxo
 */
public class Explorer
{
    Output out = new Output();
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
        
       // tree = treePanel.getTree();
        
        
        
       /* tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                   tree.getLastSelectedPathComponent();

           
                if (node == null) return;

        
                Object nodeInfo = node.getUserObject();
                out.print(nodeInfo);
            }
        });*/
        
        
        
       
        

        
       
        
                c = fileSystem.getInstance();
                
        try {
            c.setnewFileSystem("127.0.0.1", "/home/xoxoxo/Musik/BM35flac");
            initExplorerTree(c.get("127.0.0.1"));
        } catch (fileSystemException ex) {
            Logger.getLogger(Explorer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Explorer.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void initExplorerTree(List<Path> fs) throws IOException
    {
        try {
           for (int i = 0; i < fs.size(); i++) {
               Path tmp = fs.get(i);
               //System.out.print(tmp.getFileName());
		treePanel.addObject(tmp.getFileName());
            }
           
       } catch (DirectoryIteratorException ex) {
           // I/O error encounted during the iteration, the cause is an IOException
           throw ex.getCause();
       }
    }
    

    
  

   
}
