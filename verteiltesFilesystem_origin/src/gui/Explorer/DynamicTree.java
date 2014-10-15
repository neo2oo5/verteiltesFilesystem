/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.Explorer;

import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import gui.Config;
import java.awt.EventQueue;
import substructure.GUIOutput;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class DynamicTree extends JPanel
{


    private static DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Root Node");
    private static DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
    private static JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static GUIOutput out = GUIOutput.getInstance();
    private static fileSystem c = fileSystem.getInstance();
    

    private static JLabel loadingl;
    private TreePath lastOpenedNode;
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
        //tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        //tree.setShowsRootHandles(true);


        scrollPane = new JScrollPane(tree);
       
        tree.addMouseListener(new JTreeMouseListener());

        try {   
            out.print("online: " + network.Interfaces.interfaceNetworkOnline());
            
            
            if(Config.isRootDir() == true &&  network.Interfaces.interfaceNetworkOnline() == true)
            {
               
               
                    Pane.addTab("Explorer", scrollPane);
               
                
            }
            else
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
    public void buildTreeFromString(DefaultMutableTreeNode parent, final String str) {
        // Fetch the root node
       //DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        String [] strings;
        // Split the string around the delimiter

        strings = str.split(Pattern.quote(System.getProperty("file.separator")));

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        
        // Create a node object to use for traversing down the tree as it 
        // is being created
        DefaultMutableTreeNode node = parent;

        // Iterate of the string array
        for (String s: strings) {
            // Look for the index of a node at the current level that
            // has a value equal to the current string
            int index = childIndex(node, s);

            // Index less than 0, this is a new node not currently present on the tree
            if (index < 0) {
                // Add the new node
                DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(s);
                //node.insert(newChild, node.getChildCount());
                
                
                model.insertNodeInto(newChild, node, node.getChildCount());
                
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
     public int childIndex(final DefaultMutableTreeNode node, final String childValue) {
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
     
     public void removeOldFsEntrys(List<String> fs, String IP)
     {
         

         List<String> fsLocal = fs;
         List<DefaultMutableTreeNode> nodeList = new ArrayList<>();
            
            //fetch current Node
            int index = childIndex(getRootNode(), IP);
            DefaultMutableTreeNode node = getObjectAtIndex(rootNode, index);
            
            
            
             
                 //create DefaultMultableTreeNode List
                checkNode(node, nodeList, IP, fsLocal);

                for(int y=0; y < nodeList.size(); y++)
                {
                    for(int i = 0; i< fsLocal.size(); i++)
                    {
                        String [] strings;
                        // Split the string around the delimiter

                        strings = fsLocal.get(i).split(Pattern.quote(System.getProperty("file.separator")));
                        for(String s: strings)
                        {
                            if(s.equals(nodeList.get(y).getUserObject().toString()))
                            {
                                
                                nodeList.remove(y);
                            }
                        }
                    }
                   
                }
             
                    
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
             
            for(int y=1; y < nodeList.size(); y++)
            {
                //System.out.print(nodeList.get(y).getUserObject() + "\n");
                model.removeNodeFromParent(nodeList.get(y));
 
            }
   
        
     }
     
    public boolean checkNode(DefaultMutableTreeNode Node, List<DefaultMutableTreeNode> nodeList, String IP, List<String> tmp)
    {
        
        
        nodeList.add(Node);
        
        int childcount = Node.getChildCount();
        //has node more than 0  childs return true
        if(Node.getChildCount() > 0)
        {
            DefaultMutableTreeNode next = Node;
            for(int i = 0; i < childcount; i++)
            {  
                checkNode((DefaultMutableTreeNode)next.getChildAt(i), nodeList, IP, tmp);

            }
            return true;    
        }
        else
        {
            return false;
        }
  
    }
     
    public DefaultMutableTreeNode getObjectAtIndex(DefaultMutableTreeNode node, int index)
    {
        if(node.getChildCount() > 0)
        {
            return (DefaultMutableTreeNode)node.getChildAt(index);
        }
        else
        {
            return null;
        }
    }
     
     
     
    
     
   
    
    public TreePath lastOpenedNode()
    {
        
         List paths = new ArrayList();
        
        //TreePath currentSelection = tree.getSelectionPath();
        TreePath currentSelection = DynamicTree.getTree().getSelectionPath();
        if (currentSelection != null) 
        {
            for(Object node : currentSelection.getPath())
            {
                
                paths.add(node);
                    
                
            }
            
            
            return new TreePath(paths.toArray());
        }
        else
        {
            return null;
        }
        
        
    }
    
    public void expandtoLastOpenNode() {
        
        if((lastOpenedNode = lastOpenedNode()) != null)
        {
            tree.expandPath(lastOpenedNode);
        }
        
        /*
        String [] strings = lastOpenedNode.split(Pattern.quote(System.getProperty("file.separator")));
        DefaultMutableTreeNode node;
        DefaultMutableTreeNode tmpNode;
        
        int childcount = 0;
        
        node = rootNode;
        
        for(String s: strings)
        {
            childcount = node.getChildCount();
           // childcount-=1;
            //System.out.print(childcount+"\n");
            
            if(childcount > 0)
            {
                //iterate al hilds of current Node
                for(int i = 0; i < childcount; i++)
                {   
                    
                    //select next child
                    tmpNode = (DefaultMutableTreeNode) node.getNextNode();

                    if(tmpNode.toString().equals(s))
                    {
                        //System.out.print("-----string: "+s+"-----currentNode: "+tmpNode+"\n" );
                        //level++;
                        //System.out.print("currentNode: "+ tmpNode + "\n");
                        tree.expandRow(tmpNode.getLevel());


                        node = (DefaultMutableTreeNode) tmpNode.getChildAt(0);
                        
                        System.out.print("nextNode: "+node.toString()+"\n lastopenstring: " + lastOpenedNode + "\n");
                    }
                               
                }

            }
       
   
        }
        */
        
        
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
    
    public static synchronized javax.swing.JTree getTree()
    {
        return tree;
    }
    
    public static synchronized DefaultTreeModel getTreeModel()
    {
        return (DefaultTreeModel) tree.getModel();
    }
    
    public static synchronized DefaultMutableTreeNode getRootNode()
    {
        return rootNode;
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

            if (parent != null && currentNode != null) 
            {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }

        toolkit.beep();
    }
    
    
    public void removeOverIP(String ip)
    {
        int childcount = getRootNode().getChildCount();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        
        
        if(childcount > 0)
        {
            for(int y=0; y < childcount; y++)
            {
                DefaultMutableTreeNode currentchildNode = (DefaultMutableTreeNode) getRootNode().getChildAt(y);
                if(currentchildNode.getUserObject().toString().equals(ip))
                {   System.out.print(currentchildNode.getUserObject());
                    model.removeNodeFromParent(currentchildNode);
                }
            }
        }
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
