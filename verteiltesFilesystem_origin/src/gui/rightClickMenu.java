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
public class rightClickMenu {
    private static String CFI_CMD = "createFile";    
    private static String RFI_CMD = "removeFile";    
    private static String DFI_CMD = "downloadFile";
    private static String REFI_CMD = "renameFile";
    private String downloadFolder   = "Downloads";
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
        class PopupListener implements ActionListener {
          

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand(); //To change body of generated methods, choose Tools | Templates.
                TreePath currentSelection = DynamicTree.tree.getSelectionPath();
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
                                DynamicTree.treeModel.insertNodeInto(childNode, currentNode, currentNode.getChildCount());
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
                        DynamicTree.treeModel.removeNodeFromParent(currentNode);
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
                        try {
                            
                            String IPv4 = "", sourcePath = "", targetPath = "", filename = "", path = DynamicTree.getPath(currentNode);
                            
                            IPv4        = path.substring(0, path.indexOf("/"));
                            filename    = path.substring(path.lastIndexOf("/")+1, path.length());
                            sourcePath  = path.substring(path.indexOf("/"), path.lastIndexOf("/"));
                            targetPath  = substructure.PathHelper.getFolder(downloadFolder);
                            
                            out.print("(Download) IPv4: " + IPv4 + " filename: " + filename + " sourcePath: " + sourcePath);
                            Interfaces.interfaceFileTransfer(IPv4, sourcePath, targetPath, filename);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(rightClickMenu.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (fileSystemException ex) {
                            out.print(ex.toString());
                        }
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
