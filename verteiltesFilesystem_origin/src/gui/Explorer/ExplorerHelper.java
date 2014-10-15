/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Explorer;

import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class ExplorerHelper {
    
    private static String downloadFolder   = "Downloads";
    private static GUIOutput out = GUIOutput.getInstance();
    private static fileSystem c = fileSystem.getInstance();
    
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
        String result[] = new String[5];
        
            
        result[0]  = path.substring(0, path.indexOf("/")); //IP
        List<Path>  fs  =  c.get(result[0]);
        result[1]  = path.substring(path.lastIndexOf("/")+1, path.length()); //filename
        
        result[3]  = targetPath; //targetPath
        
        for(Path pathfs: fs)
        {
            if(pathfs.toString().contains(result[1]))
            {
                result[2]  = pathfs.toString(); //sourcePath
            }
        }
        
        return   result;
    }
}
