package gui.Explorer;

import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import gui.Config;
import substructure.*;
import java.awt.Toolkit;
import java.io.File;
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

/**
 * Stellt Funktionen zur Bearbeitung des JTree Models bereit
 * @author Kevin Bonner  - kevin.bonner@gmx.de
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
     * Erstellt auf einem Pane ein Jtree mit einem standart Root Node
     *
     * @param Pane
     */
    public DynamicTree(javax.swing.JTabbedPane Pane)
    {

        treeModel.addTreeModelListener(new JTreeModelListener());

        tree = new JTree(treeModel);
        tree.setEditable(false);

        scrollPane = new JScrollPane(tree);

        tree.addMouseListener(new JTreeMouseListener());

        out.print("online: " + network.Interfaces.interfaceNetworkOnline());
        if (fileSystem_Start.Debug == true)
        {
            Pane.addTab("Explorer", scrollPane);
        }
        if (Config.isRootDir() == true && network.Interfaces.interfaceNetworkOnline() == true)
        {
            Pane.addTab("Explorer", scrollPane);
        } else
        {
            try
            {
                ImageIcon loading = new ImageIcon(substructure.PathHelper.getFile("ajax-loader.gif"));
                loading.setImage(loading.getImage().getScaledInstance(100, 100, 2));
                loadingl = new JLabel("", loading, JLabel.CENTER);

                Pane.addTab("Explorer", loadingl);

            } catch (fileSystemException ex)
            {
                out.print(ex.toString());
            }
        }

    }

    /**
     * Aktualisiert das Explorer Tab nach dem das Programm Online ging
     * Entfernt den Lade Balken
     *
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
     * Erstellt aus dem Pfad die Ordnerstruktur im JTree
     *
     * @param parent
     * @param str Dateisystem Pfad
     */
    public void buildTreeFromString(DefaultMutableTreeNode parent, final String str)
    {
        // Fetch the root node
        //DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        String[] strings;
        // Split the string around the delimiter

        strings = str.split(Pattern.quote(System.getProperty("file.separator")));

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        // Create a node object to use for traversing down the tree as it 
        // is being created
        DefaultMutableTreeNode node = parent;

        // Iterate of the string array
        for (String s : strings)
        {
            // Look for the index of a node at the current level that
            // has a value equal to the current string
            int index = childIndex(node, s);

            // Index less than 0, this is a new node not currently present on the tree
            if (index < 0)
            {
                // Add the new node
                DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(s);

                model.insertNodeInto(newChild, node, node.getChildCount());

                node = newChild;

            } // Else, existing node, skip to the next string
            else
            {
                node = (DefaultMutableTreeNode) node.getChildAt(index);
            }
        }

    }

    /**
     * Gibt den Index des Übergebenen Nodes zurück
     * 
     *
     * @param node The node to search its children
     * @param childValue The value of the child to compare with
     * @return Index
     */
    public int childIndex(final DefaultMutableTreeNode node, final String childValue)
    {
        Enumeration<DefaultMutableTreeNode> children = node.children();
        DefaultMutableTreeNode child = null;
        int index = -1;

        while (children.hasMoreElements() && index < 0)
        {
            child = children.nextElement();

            if (child.getUserObject() != null && childValue.equals(child.getUserObject()))
            {
                index = node.getIndex(child);
            }
        }

        return index;
    }

    /**
     * Entfernt alte nicht mehr im FileSystem enthaltene Pfade
     * @param fs
     * @param IP
     */
    public void removeOldFsEntrys(List<String> fs, String IP)
    {

        List<String> fsLocal = fs;
        List<DefaultMutableTreeNode> nodeList = new ArrayList<>();

        //fetch current Node
        int index = childIndex(getRootNode(), IP);
        DefaultMutableTreeNode node = getObjectAtIndex(rootNode, index);

        //create DefaultMultableTreeNode List
        checkNode(node, nodeList, IP, fsLocal);

        for (int i = 0; i < fsLocal.size(); i++)
        {
            boolean trigger = false;
            for (int y = 0; y < nodeList.size(); y++)
            {

                String nodePath = "";
                String fsLocals = File.separator + fsLocal.get(i);
                TreeNode[] path = nodeList.get(y).getPath();

                for (TreeNode n : path)
                {
                    if (!n.toString().equals("Root Node") && !validIP(n.toString()))
                    {
                        nodePath += File.separator + n.toString();
                    }

                }

                if (fsLocals.contains(nodePath))
                {
                    nodeList.remove(y);

                }

            }

        }

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        for (int y = 1; y < nodeList.size(); y++)
        {

            if (nodeList.get(y).getParent() != null)
            {
                model.removeNodeFromParent((DefaultMutableTreeNode) nodeList.get(y).getParent());
            }
            model.removeNodeFromParent(nodeList.get(y));
            nodeList.remove(y);

        }

    }

    /**
     * 
     * @param ip
     * @return true = ist ip , false = keine IP
     */
    private static boolean validIP(String ip)
    {
        try
        {
            if (ip == null || ip.isEmpty())
            {
                return false;
            }

            String[] parts = ip.split("\\.");
            if (parts.length != 4)
            {
                return false;
            }

            for (String s : parts)
            {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255))
                {
                    return false;
                }
            }
            if (ip.endsWith("."))
            {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe)
        {
            return false;
        }
    }

    /**
     * Funktion Liest Rekursiv alle Nodes in eine Liste
     * @param Node
     * @param nodeList
     * @param IP
     * @param tmp
     * @return true = hat weiter ChildNodes, false = keine weiteren ChildNodes
     */
    public boolean checkNode(DefaultMutableTreeNode Node, List<DefaultMutableTreeNode> nodeList, String IP, List<String> tmp)
    {

        nodeList.add(Node);

        int childcount = Node.getChildCount();
        //has node more than 0  childs return true
        if (Node.getChildCount() > 0)
        {
            DefaultMutableTreeNode next = Node;
            for (int i = 0; i < childcount; i++)
            {
                checkNode((DefaultMutableTreeNode) next.getChildAt(i), nodeList, IP, tmp);

            }
            return true;
        } else
        {
            return false;
        }

    }

    /**
     * Gibt den Index eines Nodes zurück
     * @param node
     * @param index
     * @return DefaultMutableTreeNode
     */
    public DefaultMutableTreeNode getObjectAtIndex(DefaultMutableTreeNode node, int index)
    {
        if (node.getChildCount() > 0)
        {
            return (DefaultMutableTreeNode) node.getChildAt(index);
        } else
        {
            return null;
        }
    }

    /**
     * Öffnet den letzten geöffneten Pfad
     * @return TreePath
     */
    public TreePath lastOpenedNode()
    {

        List paths = new ArrayList();

        TreePath currentSelection = DynamicTree.getTree().getSelectionPath();
        if (currentSelection != null)
        {
            for (Object node : currentSelection.getPath())
            {

                paths.add(node);

            }

            return new TreePath(paths.toArray());
        } else
        {
            return null;
        }

    }

    /**
     *
     */
    public void expandtoLastOpenNode()
    {

        if ((lastOpenedNode = lastOpenedNode()) != null)
        {
            tree.expandPath(lastOpenedNode);
        }
    }

    /**
     * Löscht alle Nodes aus den Root Node
     */
    public void clear()
    {
        rootNode.removeAllChildren();
        tree.repaint();
    }

    /**
     * Gibt den JTree zurück
     * @return tree
     */
    public static synchronized javax.swing.JTree getTree()
    {
        return tree;
    }

    /**
     * Gibt den JTree Model zurück
     * @return
     */
    public static synchronized DefaultTreeModel getTreeModel()
    {
        return (DefaultTreeModel) tree.getModel();
    }

    /**
     * Gibt den Root Node zurück
     * @return
     */
    public static synchronized DefaultMutableTreeNode getRootNode()
    {
        return rootNode;
    }

    /**
     * Löscht den aktuell gewählten Node
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

    /**
     * Löscht ein FS per IP
     * @param ip
     */
    public void removeOverIP(String ip)
    {
        DefaultMutableTreeNode root = getRootNode();
        int childcount = root.getChildCount();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        if (childcount > 0)
        {
            for (int y = 0; y < childcount; y++)
            {
                DefaultMutableTreeNode currentchildNode = (DefaultMutableTreeNode) root.getChildAt(y);
                if (currentchildNode.getUserObject().toString().equals(ip))
                {
                    // System.out.print(currentchildNode.getUserObject());
                    model.removeNodeFromParent(currentchildNode);
                }
            }
        }
    }

    /**
     * Fügt ein ChildNode ein
     *
     * @param child
     * @return DefaultMutableTreeNode
     */
    public DefaultMutableTreeNode addObject(Object child)
    {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null)
        {
            parentNode = rootNode;
        } else
        {
            parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

    /**
     * Fügt ein ChildNode ein
     * @param parent
     * @param child
     * @return DefaultMutableTreeNode
     */
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child)
    {
        return addObject(parent, child, false);
    }

    /**
     * Fügt ein ChildNode ein
     * @param parent
     * @param child
     * @param shouldBeVisible
     * @return DefaultMutableTreeNode
     */
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible)
    {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

        if (parent == null)
        {
            parent = rootNode;
        }

        try
        {

            treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        } catch (java.lang.NullPointerException ex)
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
