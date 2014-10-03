/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

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


    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private GUIOutput out = GUIOutput.getInstance();
    private JPopupMenu popup;
    private JMenuItem menuItemFileDownload, menuItemFileDelete, menuItemFileCreate, menuItemFileRename;
    private static JLabel loadingl;
    JScrollPane scrollPane;
    private static String CFI_CMD = "createFile";    
    private static String RFI_CMD = "removeFile";    
    private static String DFI_CMD = "downloadFile";
    private static String REFI_CMD = "renameFile";

        
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
                    ImageIcon loading = new ImageIcon(substructure.PathHelper.getFile("ajax-loader.gif"));
                    loading.setImage(loading.getImage().getScaledInstance(100, 100, 2));
                    loadingl = new JLabel("", loading, JLabel.CENTER);
                    Pane.addTab("Explorer",loadingl);
                    
                    
                    
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
      popup = new JPopupMenu();
      
        menuItemFileDownload = new JMenuItem("Datei downloaden");
        menuItemFileCreate = new JMenuItem("Datei erstellen");
        menuItemFileDelete = new JMenuItem("Datei loeschen");
        menuItemFileRename = new JMenuItem("Datei umbenennen");

        menuItemFileDownload.addActionListener(new PopupListener());
        menuItemFileCreate.addActionListener(new PopupListener());
        menuItemFileDelete.addActionListener(new PopupListener());
        menuItemFileRename.addActionListener(new PopupListener());
        
        menuItemFileDownload.setActionCommand(DFI_CMD);
        menuItemFileCreate.setActionCommand(CFI_CMD);
        menuItemFileDelete.setActionCommand(RFI_CMD);
        menuItemFileRename.setActionCommand(REFI_CMD);
        
        popup.add(menuItemFileDownload);
        popup.add(menuItemFileCreate);
        popup.add(menuItemFileDelete);
        popup.add(menuItemFileRename);
    
        popup.show(e.getComponent(), e.getX(), e.getY());
       
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
        /*
                * RightClick Menu - ActionListener
                *
                *
        */
        class PopupListener implements ActionListener {
          

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand(); //To change body of generated methods, choose Tools | Templates.
                TreePath currentSelection = tree.getSelectionPath();
                DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
                
                if (CFI_CMD.equals(command)) 
                {
                   out.print("datei erstellt");
                   
                    if (currentSelection != null) 
                    {
                        
                            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
                            
                            //out.print(currentNode.getUserObject() + " " + currentNode.isLeaf());
                            if(currentNode.isLeaf() == false)
                            {
                                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new GuiPromptHelper(GuiPromptHelper.showInput, "Ordner Name?"));
                                treeModel.insertNodeInto(childNode, currentNode, currentNode.getChildCount());
                                //netzwerk hinzufueg funktion
                            }
                            else
                            {
                                new GuiPromptHelper(GuiPromptHelper.showError, "Es kann in einer Datei keine Datei angelegt werden.");
                            }
                            
                            if (parent != null) 
                            {
                                    
                                    return;
                            }
                    }
                } 
                else if(RFI_CMD.equals(command))
                {
                    
                    if(currentNode.isLeaf() == true)
                    {
                        treeModel.removeNodeFromParent(currentNode);
                        //netzwerk loesch funktion
                    }
                    else
                    {
                        new GuiPromptHelper(GuiPromptHelper.showError, "Ein FileSystem kann nicht geloescht werden.");
                    }
                    out.print("datei entfernt");
                }
                else if(DFI_CMD.equals(command))
                {
                    if(currentNode.isLeaf() == true)
                    {
                       //download
                    }
                    else
                    {
                        new GuiPromptHelper(GuiPromptHelper.showError, "Ein FileSystem kann nicht gedownloadet werden.");
                    }
                }
                else if (REFI_CMD.equals(command))
                {
                    currentNode.setUserObject(new GuiPromptHelper(GuiPromptHelper.showInput, "Neuer Name?"));
                    out.print("Node Text geändert");
                }
               
            }
        }
}
