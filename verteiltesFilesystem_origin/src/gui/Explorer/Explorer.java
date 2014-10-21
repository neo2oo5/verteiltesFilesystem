package gui.Explorer;

import java.util.*;
import java.nio.file.*;
import fileSystem.fileSystem;
import gui.Config;
import javax.swing.tree.DefaultMutableTreeNode;
import network.PingServer;
import substructure.GUIOutput;

/**
 * Erstellt den Explorer Tab
 * @author Kevin Bonner  - kevin.bonner@gmx.de
 */
public class Explorer
{

    private static GUIOutput out = GUIOutput.getInstance();
    private static fileSystem c = fileSystem.getInstance();
    private static DynamicTree treePanel;

    /**
     * Erstellt Instanz des DynamicJTree
     * @param Pane
     */
    public Explorer(javax.swing.JTabbedPane Pane)
    {
        treePanel = new DynamicTree(Pane);

    }

    /**
     * Added den DynamicJtree dem Tab an einem bestimmten Index
     * @param Pane
     * @param index
     */
    public void addTab(javax.swing.JTabbedPane Pane, int index)
    {
        treePanel.addTab(Pane, index);
    }

    private static void changePathSeperator(String IP, List<String> tmp)
    {

        List<Path> fs = c.get(IP);

        for (int i = 0; i < fs.size(); i++)
        {

            String path = fs.get(i).toString();

            String tmps = "";

            tmps = ExplorerHelper.convertPath(path);
            tmp.add(tmps);
        }

    }

    /**
     * Entfernt FS über IP
     * @param currentIP
     */
    public static void removeIP(String currentIP)
    {
        treePanel.removeOverIP(currentIP);
    }

    /**
     * Syncronisiert den JTreeExplorer mit dem FS
     */
    public static synchronized void initExplorerTree()
    {

        List<String> ips = c.getAllIps();
        List<String> tmp = new ArrayList<>();

        //loescht alte IP aus FS und EXplorer
        for (int b = 0; b < c.getClientCount(); b++)
        {

            String currentIP = ips.get(b);

            if (!PingServer.PingServer(currentIP))
            {
                c.remove(currentIP);
                treePanel.removeOverIP(currentIP);
            }
        }

        //prueft ob pfad gesetzt
        if (!Config.isRootDir())
        {
            Config.filechooser();
        } else if (c.isAccessDenied(Config.getRootDir()))
        {
            Config.filechooser();
        } else
        {
            c.setNewFileSystem(Config.getCurrentIp(), Config.getRootDir());
        }

        //geht alle FS durch und traeg sie in JTree ein
        for (int z = 0; z < c.getClientCount(); z++)
        {

            String currentIP = ips.get(z);
            tmp.clear();
            changePathSeperator(currentIP, tmp);

            //Created the Tree Structure in Explorer
            int index = treePanel.childIndex(treePanel.getRootNode(), currentIP);
            DefaultMutableTreeNode parent;

            //neue ip
            if (index < 0)
            {

                parent = treePanel.addObject(treePanel.getRootNode(), currentIP);

            } //ip existiert bereits
            else
            {
                parent = treePanel.getObjectAtIndex(treePanel.getRootNode(), index);

            }

            for (int i = 0; i < tmp.size(); i++)
            {

                treePanel.buildTreeFromString(parent, tmp.get(i).toString());
            }

            treePanel.removeOldFsEntrys(tmp, currentIP);

        }

    }

}
