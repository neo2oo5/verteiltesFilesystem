/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package substructure;

import java.io.File;


/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class PathHelper {
    
    static GUIOutput out =  GUIOutput.getInstance();
    private static String SysDir = File.separator + "System" + File.separator;
    
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
    
    
    
    public static String getFile(String file)
    {  
        return setPath(file);  
    }
  
}
