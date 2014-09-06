/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileSystem;


import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author xoxoxo
 */
public class fileSystem{
    
    private final List<virtualfileSystem> fileSystem;
    private int clientscount            = 0;
    private String clients[]            = new String[100];
    private boolean isLocked            = false;
    private boolean isInterrupted       = false;
    private String  whoLocked;
    private String  token;
    private String workingDir = System.getProperty("user.dir");
   
    
    private fileSystem() {
        this.fileSystem = new ArrayList<>();
    }
    
    /**
     * Gibt die Instanz zurueck (Singelton)
     * @return
     */
    public static fileSystem getInstance() {
        return fileSystemHolder.INSTANCE;
    }
    
    private static class fileSystemHolder {

        private static final fileSystem INSTANCE = new fileSystem();
    }

    /**
     *  sperrt Struktur fuer andere Module
     *  Jedes Modul generiert einen Token somit werden doppel zugriffe verhindert
     * @param token
     */
    public void setLock(String token)
    {
        isLocked = true;
        this.token = token;
    }
    
    /**
     * Entfernt den Lock
     * @param token
     * @return
     */
    public boolean removeLock(String token)
    {
        if(token.equals(this.token))
        {
            isLocked = true;
            return true;
        }
        else
        {
            return false;
        }
    }
 
    /**
     * Sollte ein Modul vergessen den Lock entfernen kann er auch so entfernt werden
     * Letzte Moeglickeit!
     * Muss man sich noch einigen wann er benutzt werden darf
     * @return
     */
    public boolean rootremoveLock()
    {
        this.token  ="";
        isLocked    = false;
        return true;
    }
    
    /**
     * Muss  zwischen Langen operationen unterbrochen werden kann man diese funktion nutzen um zugriff zu erlangen
     * @return
     */
    public boolean setInterrupt()
    {
        isInterrupted = true;
        return true;
    }
            
    /**
     *  Gibt den Lock Status zurueck
     * @return
     */
    public boolean getLockState()
    {
        return isLocked;
    }
    
    /**
     *  Fuegt file oder folder ein wirft exception falls interrupt angefordert wurde
     * @throws fileSystemException
     */
    public void addObject() throws fileSystemException
    {
        if(isInterrupted == true)
        {
            throw new fileSystemException("Ein Interrupt wurde gemeldet!");
        }
    }
    
    public void setnewFileSystem(String IP, String path) throws fileSystemException
    {
        try{
            fileSystem.add(clientscount, new virtualfileSystem(path));
            clients[clientscount] = IP;
            clientscount++;
        }
      
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String listAll ()
    {
        String out ="";
        try{
           for (DirectoryStream entry: fileSystem) {
               for (Object file_or_folder: entry) {
                    out += " " + file_or_folder + " - Datei oder Ordner? " + Files.isDirectory(converttoPath(file_or_folder)) + "\n";
                }
              
           }
       } catch (DirectoryIteratorException ex) {
           // I/O error encounted during the iteration, the cause is an IOException
           //throw ex.getCause();
       }
        return out;
    }
    public String list (String IP)
    {
        
    }
    
    public static int find (String[] array , String name) {  
      return Arrays.asList(array).indexOf(name);  
    }  
    
   
    
}
