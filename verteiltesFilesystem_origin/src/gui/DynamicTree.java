/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import fileSystem.fileSystemException;
import java.awt.Color;
import substructure.GUIOutput;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class DynamicTree extends JPanel implements MouseListener
{


    protected static DefaultMutableTreeNode rootNode;
    protected static DefaultTreeModel treeModel;
    protected static JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static GUIOutput out = GUIOutput.getInstance();
    private JPopupMenu popup;
    private JMenuItem menuItemFileDownload, menuItemFileDelete, menuItemFileCreate, menuItemFileRename;
    private static JLabel loadingl;
    JScrollPane scrollPane;
    private static String CFI_CMD = "createFile";    
    private static String RFI_CMD = "removeFile";    
    private static String DFI_CMD = "downloadFile";
    private static String REFI_CMD = "renameFile";
    private static String downloadFolder   = "Downloads";

        
    /**
         * Create JTree
         * @param Pane
         */
	public DynamicTree(javax.swing.JTabbedPane Pane) 
        {
            
		

		rootNode = new DefaultMutableTreeNode("Root Node");
		treeModel = new DefaultTreeModel(rootNode);

		tree = new JTree(treeModel);
		tree.setEditable(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);

                
		scrollPane = new JScrollPane(tree);
                tree.addMouseListener(this);
                
                
                if(Config.isRootDir())
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

                  // Split the string around the delimiter
                  String [] strings = str.split(File.separator);

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

	/** 
         * remove all nodes without the root
         */
	public void clear() 
        {
		rootNode.removeAllChildren();
		treeModel.reload();
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
        
    public static String getPath(DefaultMutableTreeNode path)
    {
        String jTreeVarSelectedPath = "";
        Object[] paths = path.getPath();
        for (int i=1; i<paths.length; i++) {
            jTreeVarSelectedPath += paths[i];
            if (i+1 <paths.length ) {
                jTreeVarSelectedPath += File.separator;
            }
        }
        
        return jTreeVarSelectedPath;
    }
    
    public static String[] getNetOperationData(DefaultMutableTreeNode currentNode)
    {
        try {
            return getNetOperationData(currentNode, substructure.PathHelper.getFolder(downloadFolder));
        } catch (fileSystemException ex) {
            out.print(ex.toString());
        }
            return null;
    }
    
    public static String[] getNetOperationData(DefaultMutableTreeNode currentNode, String targetPath)
    {
        String  path    = getPath(currentNode);
        String result[] = null;
       
            
        result[0]       = path.substring(0, path.indexOf("/")); //IP
        result[1]   = path.substring(path.lastIndexOf("/")+1, path.length()); //filename
        result[2]  = path.substring(path.indexOf("/"), path.lastIndexOf("/")); //sourcePath
        result[3]  = targetPath; //targetPath
        
        
        return   result;
    }
    //open Popupmenu
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getButton() == 3)
        {
            showMenu(e);
        }
        
    }

    /**
     * Create Rightclick Menu
     * @param e
     */
    public void showMenu(MouseEvent e)
    {
      new rightClickMenu(e);
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        
	class MyTreeModelListener implements TreeModelListener 
        {
		public void treeNodesChanged(TreeModelEvent e) 
                {
			DefaultMutableTreeNode node;
			node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());

			int index = e.getChildIndices()[0];
			node = (DefaultMutableTreeNode) (node.getChildAt(index));
		}

		public void treeNodesInserted(TreeModelEvent e) 
                {
                    out.print("DynamicJTree: Node wurde eingefuegt ", 1);
		}

		public void treeNodesRemoved(TreeModelEvent e) 
                {
                    out.print("DynamicJTree: Node wurde entfernt", 1);
		}

		public void treeStructureChanged(TreeModelEvent e) 
                {
                    
                    out.print("DynamicJTree: ", 1);
		}
                
                
	}
        
}
