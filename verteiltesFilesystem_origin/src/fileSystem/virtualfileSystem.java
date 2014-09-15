/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileSystem;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class virtualfileSystem implements Serializable
{
    private Node virtualFSystem[];
            
            
            
    virtualfileSystem(String path) {
        initFilesystem(path, null, virtualFSystem);
    }
    private void initFilesystem(Object path)
    {
        initFilesystem(path, null, virtualFSystem);
    }
    
    private Node[] initFilesystem(Object path, Folder parent,  Node virtualFSystem[])
    {   
        try{
               int x = 0;
               DirectoryStream<Path> stream = Files.newDirectoryStream(converttoPath(path), "*");
               Node tmparray[] = null;
               
               for (Path file_or_folder: stream) {
                    
                   
                    
                    if(Files.isDirectory(converttoPath(file_or_folder)) == true)
                    {  
                        Folder nextparent = new Folder(file_or_folder.toString(), parent);
                        virtualFSystem[x] = new Node();
                        virtualFSystem[x] = initFilesystem(file_or_folder, nextparent, Array.get(virtualFSystem,x));
                        
                    }
                    else
                    {
                        virtualFSystem[x] =  new File(file_or_folder.toString(), parent); 
                    }
                }
               
                return virtualFSystem;
              
           
       } catch (DirectoryIteratorException ex) {
           // I/O error encounted during the iteration, the cause is an IOException
           //throw ex.getCause();
       } catch (IOException ex) {
           // Logger.getLogger(virtualfileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
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
           
               for (Object file_or_folder: virtualFSystem) {
                    out += " " + file_or_folder + " - Datei oder Ordner? " + Files.isDirectory(converttoPath(file_or_folder)) + "\n";
                }
              
           
       } catch (DirectoryIteratorException ex) {
           // I/O error encounted during the iteration, the cause is an IOException
           //throw ex.getCause();
       }
        return out;
    }
    
    public List<List<Node>> get()
    {
        return virtualFSystem;
    }
}
