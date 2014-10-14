 package gui.Explorer;
 
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

 
 /**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */

class JTreeModelListener implements TreeModelListener {
    public void treeNodesChanged(TreeModelEvent e) {
      DefaultMutableTreeNode node;
      node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());

        if(node != node)
        {
          try {
              int index = e.getChildIndices()[0];
              node = (DefaultMutableTreeNode) (node.getChildAt(index));
          } 
          catch (NullPointerException exc) {
              
          }

           // System.out.println("The user has finished editing the node.");
          //  System.out.println("New value: " + node.getUserObject());
        }
    }


  public void treeNodesInserted(TreeModelEvent e) {
    //System.out.println(e.getTreePath().toString());
  }

  public void treeNodesRemoved(TreeModelEvent e) {
   // System.out.println(e.getTreePath().toString());
  }

  public void treeStructureChanged(TreeModelEvent e) {
    //System.out.print(e.getTreePath().toString());
  }
}