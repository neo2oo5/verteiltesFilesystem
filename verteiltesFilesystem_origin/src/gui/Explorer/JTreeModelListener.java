package gui.Explorer;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Momentan ohne Besondere Funktion Dient generell zum ausgeben der Interaktionen
 * mit dem JtreeModel
 *
 * @author Kevin Bonner  - kevin.bonner@gmx.de
 */
class JTreeModelListener implements TreeModelListener
{

    public void treeNodesChanged(TreeModelEvent e)
    {
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());

        if (node != node)
        {
            try
            {
                int index = e.getChildIndices()[0];
                node = (DefaultMutableTreeNode) (node.getChildAt(index));
            } catch (NullPointerException exc)
            {

            }

        }
    }

    public void treeNodesInserted(TreeModelEvent e)
    {

    }

    public void treeNodesRemoved(TreeModelEvent e)
    {

    }

    public void treeStructureChanged(TreeModelEvent e)
    {

    }
}
