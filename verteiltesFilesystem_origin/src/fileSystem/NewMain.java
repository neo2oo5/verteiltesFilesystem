/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileSystem;

import gui.Config;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class NewMain {
    static fileSystem c = fileSystem.getInstance();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try
            {
                c.setnewFileSystem("127.0.0.1", "E:\\Downloads");
            } catch (fileSystemException ex)
            {
                System.out.print("Lokales FileSystem konnte nicht Indexiert werden");
            }
        
        //c.mergeList();
        
        String ips[] = c.getAllIps();

            for(int i = 0; i < c.getClientCount(); i++)
            {
                 List<Path>  fs =  c.get(ips[i]);
                 
                 for (int z = 0; z < fs.size(); z++) {
                     
                    // System.out.print(fs.get(z) + "\n");
                     
                 }
                
            }
    }
    
}
