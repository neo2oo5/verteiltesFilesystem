/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import substructure.GUIOutput;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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


public class DynamicTree extends JPanel implements MouseListener
{
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private GUIOutput out = GUIOutput.getInstance();
    private JPopupMenu popup;
    private JMenuItem menuItemOrdnerLoeschen;
    private JMenuItem menuItemOrdnerErstellen;
    private JMenuItem menuItemDateiLoeschen;
    private JMenuItem menuItemDateiErstellen;
    private static String CFO_CMD = "createFolder";
    private static String RFO_CMD = "removeFolder";
    private static String CFI_CMD = "createFolder";    
    private static String RFI_CMD = "removeFolder";    
        
        /**
         * Erstellt den Tree
         */
	public DynamicTree(javax.swing.JTabbedPane Pane) 
        {
            
		

		rootNode = new DefaultMutableTreeNode("Root Node");
		treeModel = new DefaultTreeModel(rootNode);

		tree = new JTree(treeModel);
		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);

		JScrollPane scrollPane = new JScrollPane(tree);
                tree.addMouseListener(this);
		Pane.addTab("Explorer", scrollPane);
                
	}

	/** 
         * Löscht alle Äste abgesehen vom Root. 
         */
	public void clear() 
        {
		rootNode.removeAllChildren();
		treeModel.reload();
	}
    /*
        * Gibt Tree zurueck
        */
        public javax.swing.JTree getTree()
        {
            return tree;
        }

	/** 
         * Löscht den aktuellen Ast. 
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
         * Fügt ein Child zum aktuellen Ast hinzu. 
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

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getButton() == 3)
        {
            showMenu(e);
        }
        
    }
    public void showMenu(MouseEvent e)
    {
      popup = new JPopupMenu();
        menuItemOrdnerErstellen = new JMenuItem("Ordner erstellen");
        menuItemOrdnerLoeschen = new JMenuItem("Ordner loeschen");
        menuItemDateiErstellen = new JMenuItem("Datei erstellen");
        menuItemDateiLoeschen = new JMenuItem("Datei loeschen");


        menuItemOrdnerErstellen.addActionListener(new PopupListener());
        menuItemOrdnerLoeschen.addActionListener(new PopupListener());
        menuItemDateiErstellen.addActionListener(new PopupListener());
        menuItemDateiLoeschen.addActionListener(new PopupListener());
        
        menuItemOrdnerErstellen.setActionCommand(CFO_CMD);
        menuItemOrdnerLoeschen.setActionCommand(RFO_CMD);
        menuItemDateiErstellen.setActionCommand(CFI_CMD);
        menuItemDateiLoeschen.setActionCommand(RFI_CMD);
        
        popup.add(menuItemOrdnerErstellen);
        popup.add(menuItemOrdnerLoeschen);
        popup.add(menuItemDateiErstellen);
        popup.add(menuItemDateiLoeschen);
    
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
        
        class PopupListener implements ActionListener {
          

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand(); //To change body of generated methods, choose Tools | Templates.
                if (CFI_CMD.equals(command)) 
                {
                   out.print("datei erstellt");
                   TreePath currentSelection = tree.getSelectionPath();
                    if (currentSelection != null) 
                    {
                        
                            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
                            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
                            
                            out.print(currentNode.getUserObject() + " " + currentNode.isLeaf());
                            
                            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new GuiPromptHelper(GuiPromptHelper.showInput, "ordner name?"));
                             treeModel.insertNodeInto(childNode, currentNode, currentNode.getChildCount());
                            
                            if (parent != null) 
                            {
                                    
                                    return;
                            }
                    }
                } 
                else if(RFI_CMD.equals(command))
                {
                    out.print("datei entfernt");
                }
                else if(CFO_CMD.equals(command))
                {
                    out.print("ordner erstellt");
                }
                else if(RFO_CMD.equals(command))
                {
                    out.print("ordner entfernt");
                }
            }
        }
}