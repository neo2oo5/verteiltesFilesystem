/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import fileSystem.fileSystemException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import substructure.GUIOutput;
import network.*;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class rightClickMenu
{

    private static String CFI_CMD = "createFile";
    private static String RFI_CMD = "removeFile";
    private static String DFI_CMD = "downloadFile";
    private static String REFI_CMD = "renameFile";

    private static String fileinFileMSG = "Es kann in einer Datei keine Datei angelegt werden.",
            fsDownloadMSG = "Ein FileSystem kann nicht gedownloadet werden.",
            fsDeleteMSG = "Ein FileSystem kann nicht geloescht werden.";

    private JPopupMenu popup;
    private JMenuItem menuItemFileDownload, menuItemFileDelete, menuItemFileCreate, menuItemFileRename;
    private GUIOutput out = GUIOutput.getInstance();

    public rightClickMenu(MouseEvent e)
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

    /*
     * RightClick Menu - ActionListener
     *
     *
     */
    class PopupListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand(); //To change body of generated methods, choose Tools | Templates.
            TreePath currentSelection = DynamicTree.tree.getSelectionPath();
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());

            if (CFI_CMD.equals(command))
            {

                if (currentSelection != null)
                {

                    MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());

                    //out.print(currentNode.getUserObject() + " " + currentNode.isLeaf());
                    if (currentNode.isLeaf() == false)
                    {
                        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new GuiPromptHelper(GuiPromptHelper.showInput, "Ordner Name?"));
                        DynamicTree.treeModel.insertNodeInto(childNode, currentNode, currentNode.getChildCount());
                                //netzwerk ordner hinzufueg funktion

                        String args[];

                        if ((args = DynamicTree.getNetOperationData(currentNode)) != null)
                        {
                            try
                            {
                                out.print("(Create) IPv4: " + args[0] + " filename: " + args[1] + " sourcePath: " + args[2] + "targetPath: " + args[3]);
                                Interfaces.interfaceFileCreate(args[0], args[2], args[1]);
                                out.print("datei erstellt");
                            } catch (UnknownHostException ex)
                            {
                                out.print("(rightClickMenu) - PopupListener : " + ex.toString(), 2);
                            }
                        }

                    } else
                    {
                        new GuiPromptHelper(GuiPromptHelper.showError, fileinFileMSG);
                    }

                    if (parent != null)
                    {

                        return;
                    }
                }
            } else if (RFI_CMD.equals(command))
            {

                if (currentNode.isLeaf() == true)
                {
                    DynamicTree.treeModel.removeNodeFromParent(currentNode);
                    //netzwerk loesch funktion
                    String args[];

                    if ((args = DynamicTree.getNetOperationData(currentNode)) != null)
                    {
                        out.print("(Remove) IPv4: " + args[0] + " filename: " + args[1] + " sourcePath: " + args[2] + "targetPath: " + args[3]);
                        try
                        {
                            Interfaces.interfaceFileDelete(args[0], args[2], args[1]);
                        } catch (UnknownHostException ex)
                        {
                            out.print("(rightClickMenu) - PopupListener : " + ex.toString(), 2);
                        }
                        out.print("datei gelöscht");
                    }
                } else
                {
                    new GuiPromptHelper(GuiPromptHelper.showError, fsDeleteMSG);
                }

            } else if (DFI_CMD.equals(command))
            {
                if (currentNode.isLeaf() == true)
                {
                    String args[];

                    if ((args = DynamicTree.getNetOperationData(currentNode)) != null)
                    {
                        out.print("(Download) IPv4: " + args[0] + " filename: " + args[1] + " sourcePath: " + args[2] + "targetPath: " + args[3]);
                        try
                        {
                            Interfaces.interfaceFileTransfer(args[0], args[1], args[2], args[3]);
                        } catch (UnknownHostException ex)
                        {
                            out.print("(rightClickMenu) - PopupListener : " + ex.toString(), 2);
                        }
                        out.print("datei download beendet");
                    }
                } else
                {
                    new GuiPromptHelper(GuiPromptHelper.showError, fsDownloadMSG);
                }
            } else if (REFI_CMD.equals(command))
            {
                GuiPromptHelper prompt = new GuiPromptHelper(GuiPromptHelper.showInput, "Neuer Name?");

                currentNode.setUserObject(prompt.toString());

                String args[];

                if ((args = DynamicTree.getNetOperationData(currentNode)) != null)
                {
                    out.print("(Rename) IPv4: " + args[0] + " filename: " + args[1] + " sourcePath: " + args[2] + "targetPath: " + args[3]);
                    try
                    {
                        Interfaces.interfaceFileRename(args[0], args[2], args[1], prompt.toString());
                    } catch (UnknownHostException ex)
                    {
                        out.print("(rightClickMenu) - PopupListener : " + ex.toString(), 2);
                    }
                    out.print("datei umbenannt");
                }
            }

        }

    }
}
