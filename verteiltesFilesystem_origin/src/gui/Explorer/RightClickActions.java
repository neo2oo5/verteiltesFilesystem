/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Explorer;

import fileSystem.fileSystem;
import gui.GuiPromptHelper;
import java.net.UnknownHostException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import network.Interfaces;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class RightClickActions {
    
     private static GUIOutput out = GUIOutput.getInstance();
     private static fileSystem c = fileSystem.getInstance();
     
     private static String fileinFileMSG = "Es kann in einer Datei keine Datei angelegt werden.",
            fsDownloadMSG = "Ein FileSystem kann nicht gedownloadet werden.",
            fsDeleteMSG = "Ein FileSystem kann nicht geloescht werden.";
    
    public static void FileDownload(DefaultMutableTreeNode currentNode, String[] args)
    {
        out.print("download");
                    if (currentNode.isLeaf() == true)
                    {


                        if (args != null)
                        {
                            out.print("(Download) IPv4: " + args[0] + " filename: " + args[1] + " sourcePath: " + args[2] + " targetPath: " + args[3] + "- Filename after Download: " + args[0]+"_"+args[1]);
                            try
                            {
                                Interfaces.interfaceFileTransfer(args[0],args[2] , args[1], args[1]);
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
    }
    
    public static void FileCreate(DefaultMutableTreeNode currentNode, String[] args)
    {
        MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());

                        out.print(currentNode.getUserObject() + " " + currentNode.isLeaf()+"\n");
                        if (currentNode.isLeaf() == false)
                        {
                            GuiPromptHelper prompt = new GuiPromptHelper(GuiPromptHelper.showInput, "Datei Name?");
                            String fileName = prompt.toString();
                            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(fileName);
                            
                            System.out.print(currentNode.getUserObject());
                            //netzwerk ordner hinzufueg funktion



                            if (args != null && fileName != null)
                            {
                                try
                                {
                                    out.print("(Create) IPv4: " + args[0] + " filename: " + fileName + " sourcePath: " + args[2] + "targetPath: " + args[3]);
                                    Interfaces.interfaceFileCreate(args[0], args[2], fileName);
                                    c.addElement(args[0], fileName);
                                    DynamicTree.getTreeModel().insertNodeInto(childNode, currentNode, currentNode.getChildCount());
                                    
                                    out.print("Datei erstellt");
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
    
    public static void FileRename(DefaultMutableTreeNode currentNode, String[] args)
    {
                        System.out.print("rename");
                        GuiPromptHelper prompt = new GuiPromptHelper(GuiPromptHelper.showInput, "Neuer Name?");
                        String fileName = prompt.toString();

                        currentNode.setUserObject(prompt.toString());



                        if (args != null && fileName != null)
                        {
                            out.print("(Rename) IPv4: " + args[0] + " filename old: " + args[1] + " filename new: " +fileName+ " sourcePath: " + args[2] + "targetPath: " + args[3]);
                            try
                            {
                                Interfaces.interfaceFileRename(args[0], args[2], args[1], fileName);
                                c.deleteElement(args[0], args[2] + args[1]);
                                c.addElement(args[0], args[2] + fileName);
                            } catch (UnknownHostException ex)
                            {
                                out.print("(rightClickMenu) - PopupListener : " + ex.toString(), 2);
                            }
                            out.print("datei umbenannt");
                        }
    }
    
    public static void FileDelete(DefaultMutableTreeNode currentNode, String[] args)
    {
        if (currentNode.isLeaf() == true)
                        {
                            DynamicTree.getTreeModel().removeNodeFromParent(currentNode);
                            //netzwerk loesch funktion


                            if (args != null)
                            {
                                out.print("(Remove) IPv4: " + args[0] + " filename: " + args[1] + " sourcePath: " + args[2] + "targetPath: " + args[3]);
                                try
                                {
                                    Interfaces.interfaceFileDelete(args[0], args[2], args[1]);
                                    c.deleteElement(args[0], args[2]+args[1]);
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
    }
}
