/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.Explorer;

import fileSystem.fileSystemException;
import gui.Config;
import substructure.GUIOutput;
import java.awt.Toolkit;
import java.io.File;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class DynamicTree extends JPanel
{


    public static DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Root Node");
    public static DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
    public static JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static GUIOutput out = GUIOutput.getInstance();

    private static JLabel loadingl;
    private String lastOpenedNode = "";
    JScrollPane scrollPane;
    

        
    /**
         * Create JTree
         * @param Pane
         */
    public DynamicTree(javax.swing.JTabbedPane Pane) 
    {

       
        treeModel.addTreeModelListener(new JTreeModelListener());


        tree = new JTree(treeModel);
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);


        scrollPane = new JScrollPane(tree);
        tree.addMouseListener(new JTreeMouseListener());

        try {   
            out.print("online: " + network.Interfaces.interfaceNetworkOnline());
            if(Config.isRootDir() == false && network.Interfaces.interfaceNetworkOnline() == false)
            {
                try {
                    ImageIcon loading = new ImageIcon(substructure.PathHelper.getFile("ajax-loader.gif"));
                    loading.setImage(loading.getImage().getScaledInstance(100, 100, 2));
                    loadingl = new JLabel("", loading, JLabel.CENTER);
                    Pane.addTab("Explorer",loadingl);
                } catch (fileSystemException ex) {
                    out.print(ex.toString());
                }



            }
            else
            {
                Pane.addTab("Explorer", scrollPane);
            }
        } catch (UnknownHostException ex) {

           out.print(ex.toString());
        }

    }
        
    /**
     * Refresh Explorer Tab after selecting a path
     * @param Pane
     * @param index
     */
    public void addTab(javax.swing.JTabbedPane Pane, int index)
    {
        Pane.removeTabAt(index);
        Pane.addTab("Explorer", scrollPane);
        Pane.setSelectedIndex(index);
    }
        
    /**
           * Builds a tree from a given forward slash delimited string.
           * 
           * @param str The string to build the tree from
           */
    public void buildTreeFromString( final String str) {
        // Fetch the root node
       // DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        String [] strings;
        // Split the string around the delimiter

        strings = str.split(Pattern.quote(System.getProperty("file.separator")));


        // Create a node object to use for traversing down the tree as it 
        // is being created
        DefaultMutableTreeNode node = rootNode;

        // Iterate of the string array
        for (String s: strings) {
            // Look for the index of a node at the current level that
            // has a value equal to the current string
            int index = childIndex(node, s);

            // Index less than 0, this is a new node not currently present on the tree
            if (index < 0) {
                // Add the new node
                DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(s);
                node.insert(newChild, node.getChildCount());
                node = newChild;
            }
            // Else, existing node, skip to the next string
            else {
                node = (DefaultMutableTreeNode) node.getChildAt(index);
            }
        }
        
        
    }

    /**
            * Returns the index of a child of a given node, provided its string value.
            * 
            * @param node The node to search its children
            * @param childValue The value of the child to compare with
            * @return The index
            */
     private int childIndex(final DefaultMutableTreeNode node, final String childValue) {
         Enumeration<DefaultMutableTreeNode> children = node.children();
         DefaultMutableTreeNode child = null;
         int index = -1;

         while (children.hasMoreElements() && index < 0) {
             child = children.nextElement();

             if (child.getUserObject() != null && childValue.equals(child.getUserObject())) {
                 index = node.getIndex(child);
             }
         }

         return index;
     }
     
    public void beginReload()
    {
        lastOpenedNode = lastOpenedNode();
        rootNode.removeAllChildren();
        
    }
     
    public void endReload()
    {
        
       treeModel.reload();
       expandtoLastOpenNode();
        
        
    }
    
    public String lastOpenedNode()
    {
        String tmp = new String();
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) 
        {
            for(Object node :currentSelection.getPath())
            {
                if(!currentSelection.getLastPathComponent().equals(node.toString()))
                {
                    tmp += node.toString() + File.separator;
                }
            }
            
            return tmp;
        }
        else
        {
            return lastOpenedNode;
        }
        
        
    }
    
    public void expandtoLastOpenNode() {
        int row = 0;
        DefaultMutableTreeNode node = rootNode;
        
        String [] strings = lastOpenedNode.split(Pattern.quote(System.getProperty("file.separator")));
        
        for(String s: strings)
        {
            
            // Look for the index of a node at the current level that
            // has a value equal to the current string
            int index = childIndex(node, s);
            
            //System.out.print(s + " index: "+ index +"\n");
            // Index less than 0, this is a new node not currently present on the tree
            if (index < 0) {
                // Node exist
               row++;
               tree.expandRow(row);
            }
            
            
            
        }
        
        
        
        /*while (row < tree.getRowCount()) {
          
          row++;
        }*/
    }

    /** 
         * remove all nodes without the root
         */
    public void clear() 
    {
            rootNode.removeAllChildren();
            tree.repaint();
    }
   

    /**
     *
     * @return tree
     */
    
    public javax.swing.JTree getTree()
    {
        return tree;
    }

	/** 
         * remove current Node
         */
    public void removeCurrentNode() 
    {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) 
        {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());

            if (parent != null) 
            {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }

        toolkit.beep();
    }

	/** 
         * insert a child to current Node
     * @param child
     * @return 
         */
    public DefaultMutableTreeNode addObject(Object child) 
    {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) 
        {
                parentNode = rootNode;
        } 

        else 
        {
                parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

        /**
         * 
         * @param parent
         * @param child
         * @return 
         */
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) 
    {
        return addObject(parent, child, false);
    }

        /**
         * 
         * @param parent
         * @param child
         * @param shouldBeVisible
         * @return 
         */
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) 
    {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

        if (parent == null) 
        {
                parent = rootNode;       
        }

        try{


            treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        }
        catch(java.lang.NullPointerException ex)
        {
            out.print("DynamicJTree: " + ex, 3);
        }


        if (shouldBeVisible) 
        {
                tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }

        return childNode;
    }
        
    
    

        
	
        
}
