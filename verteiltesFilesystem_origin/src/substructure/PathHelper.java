/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package substructure;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class PathHelper {
    
    static GUIOutput out =  GUIOutput.getInstance();
    private static String SysDir = "/System/";
    
    private static String getOSName()
    {
        return System.getProperty("os.name");
    }
    
    private static String setPath(String file)
    {
        if("Linux".equals(getOSName()))
        {
            return "." + SysDir + file;
        }
        else if("Windows".equals(getOSName()))
        {
            return SysDir + file;
        }
        
        return null;
    }
    
    
    
    public static FileReader getFile(String file) throws FileNotFoundException
    {
        
        
        
        return new FileReader(setPath(file));
        
    }
    
    public static FileOutputStream setFile(String file) throws FileNotFoundException
    {
        return new FileOutputStream(setPath(file));
  
    }
}
