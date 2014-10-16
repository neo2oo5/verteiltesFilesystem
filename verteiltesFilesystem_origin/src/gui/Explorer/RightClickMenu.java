/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Explorer;

import gui.Config;
import gui.GuiPromptHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.net.UnknownHostException;
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
public class RightClickMenu
{

    private static String CFI_CMD = "createFile";
    private static String RFI_CMD = "removeFile";
    private static String DFI_CMD = "downloadFile";
    private static String REFI_CMD = "renameFile";

    

    private JPopupMenu popup;
    private JMenuItem menuItemFileDownload, menuItemFileDelete, menuItemFileCreate, menuItemFileRename;
    private GUIOutput out = GUIOutput.getInstance();

    public RightClickMenu(MouseEvent e)
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
            TreePath currentSelection = DynamicTree.getTree().getSelectionPath();
            String[] args = new String[5];
            DefaultMutableTreeNode currentNode = null;
            
            if(currentSelection != null)
            {
                currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
                args = ExplorerHelper.getNetOperationData(currentNode);
                
                for(String arg: args)
                {
                    System.out.print(arg+"\n");
                }
            }
            
           System.out.print("selection: "+currentSelection + "node" + currentNode.getUserObject()+" \n");
           System.out.print("admin: "+Interfaces.interfaceIAmAdmin()+"\n");
            
            
            if(currentNode != null)
            {
                //local operationa
                if(Config.getCurrentIp() == args[0])
                {
                    //datei erstellen
                    if (CFI_CMD.equals(command))
                    {
                        RightClickActions.FileCreate(currentNode, args);
                    //datei umbenennen
                    } else if (REFI_CMD.equals(command))
                    {

                        RightClickActions.FileRename(currentNode, args);
                    //datei löschen
                    } else if (RFI_CMD.equals(command))
                    {
                        RightClickActions.FileDelete(currentNode, args);
                    } 
                }
                
                else if(Interfaces.interfaceIAmAdmin() == true)
                    //datei erstellen
                    if (CFI_CMD.equals(command))
                    {
                        RightClickActions.FileCreate(currentNode, args);
                    //datei umbenennen
                    } else if (REFI_CMD.equals(command))
                    {

                        RightClickActions.FileRename(currentNode, args);
                    //datei löschen
                    } else if (RFI_CMD.equals(command))
                    {
                        RightClickActions.FileDelete(currentNode, args);
                    } 
                    //datei download
                    else if (DFI_CMD.equals(command) && currentNode != null)
                    {
                        RightClickActions.FileDownload(currentNode, args);
                    }
                    else if(currentNode != null)
                    {
                       out.print("Für diese Operation benötigen Sie Admin rechte."); 
                    }
            }
               
                
            
            
            
            

        }

    }
}
