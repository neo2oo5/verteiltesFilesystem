/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Explorer;

import fileSystem.fileSystemException;
import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class ExplorerHelper {
    
    private static String downloadFolder   = "Downloads";
    private static GUIOutput out = GUIOutput.getInstance();
    
    public static String getPath(DefaultMutableTreeNode path)
    {
        String jTreeVarSelectedPath = "";
        Object[] paths = path.getPath();
        for (int i=1; i<paths.length; i++) {
            jTreeVarSelectedPath += paths[i];
            if (i+1 <paths.length ) {
                jTreeVarSelectedPath += File.separator;
            }
        }
        
        return jTreeVarSelectedPath;
    }
    
    public static String[] getNetOperationData(DefaultMutableTreeNode currentNode)
    {
        try {
            return getNetOperationData(currentNode, substructure.PathHelper.getFolder(downloadFolder));
        } catch (fileSystemException ex) {
            out.print(ex.toString());
        }
            return null;
    }
    
    public static String[] getNetOperationData(DefaultMutableTreeNode currentNode, String targetPath)
    {
        String  path    = getPath(currentNode);
        String result[] = null;
       
            
        result[0]       = path.substring(0, path.indexOf("/")); //IP
        result[1]   = path.substring(path.lastIndexOf("/")+1, path.length()); //filename
        result[2]  = path.substring(path.indexOf("/"), path.lastIndexOf("/")); //sourcePath
        result[3]  = targetPath; //targetPath
        
        
        return   result;
    }
}
