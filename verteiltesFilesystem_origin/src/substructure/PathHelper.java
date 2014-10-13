/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package substructure;

import fileSystem.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class PathHelper{
    
    static GUIOutput out =  GUIOutput.getInstance();
    private static String SysDir = System.getProperty("user.dir") + File.separator + "System" + File.separator;
    
    public static String getOSName()
    {
        String os = System.getProperty("os.name");
        
        if(os.contains("Linux"))
        {
            return  "Linux";
        }
        else if(os.contains("Mac"))
        {

            return  "Mac";
        }
        else if(os.contains("Windows"))
        {
            return  "Windows";
        }
        
        return null;
    }
    

 private static String setPath(String Node)
    {
            if(getOSName().equals("Linux"))
            {
                return  SysDir + Node;
            }
            else if(getOSName().equals("Mac"))
            {
                
                return  SysDir + Node;
            }
            else if(getOSName().equals("Windows"))
            {
                return  SysDir + Node;
            }  
        return null;
    }
    
    
    public static String getFile(String file) throws fileSystemException
    {  
        Path fPath;
        
        if(file.length() > 0)
        {
            fPath = Paths.get(setPath(file) +File.separator);
        }
        else
        {
            fPath = Paths.get(setPath(file));
        }
        
        if(Files.exists(fPath))
        {
            return setPath(file);
        }
        else
        {
            throw new fileSystemException("File not Found. Path:" + file);
        }
        
        
    }
    
    public static String getFolder(String folder)  throws fileSystemException
    {
        Path fPath;
        
        if(folder.length() > 0)
        {
            fPath = Paths.get(setPath(folder) +File.separator);
        }
        else
        {
            fPath = Paths.get(setPath(folder));
        }
        
        if(Files.exists(fPath))
        {
            return setPath(folder) + File.separator;
        }
        else
        {
            throw new fileSystemException("File not Found. Path:" + folder);
        }
    }
  
}