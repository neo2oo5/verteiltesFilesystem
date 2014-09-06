/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileSystem;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class virtualfileSystem implements Serializable
{
    private DirectoryStream<Path> virtualFSystem[] = null;
            
            
            
    virtualfileSystem(String path) {
        try {
            Files.newDirectoryStream(converttoPath(path) , "*");
        } catch (IOException ex) {
            Logger.getLogger(virtualfileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean initFilesystem(String path)
    {   int x = 0;
        try {
            if(virtualFSystem[x] == null)
            {
                virtualFSystem[x] = Files.newDirectoryStream(converttoPath(path) , "*");
                x++;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(virtualfileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private Path converttoPath(Object obj)
    {
       return FileSystems.getDefault().getPath(String.valueOf(obj));
    }
    
    @Override
    public String toString()
    {
        String out ="";
        try{
           
               for (Object file_or_folder: fileSystem.get(find(clients, IP))) {
                    out += " " + file_or_folder + " - Datei oder Ordner? " + Files.isDirectory(converttoPath(file_or_folder)) + "\n";
                }
              
           
       } catch (DirectoryIteratorException ex) {
           // I/O error encounted during the iteration, the cause is an IOException
           //throw ex.getCause();
       }
        return out;
    }
    
}
