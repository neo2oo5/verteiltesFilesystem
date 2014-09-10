/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileSystem;


import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author xoxoxo
 */
public class fileSystem{
    
    private final List<List<List<Node>>> fileSystem = new ArrayList<>();
    private int clientscount            = 0;
    private String clients[]            = new String[100];
    private boolean isLocked            = false;
    private boolean isInterrupted       = false;
    private String  whoLocked;
    private String  token;
    private String workingDir = System.getProperty("user.dir");
   
    
    private fileSystem() {
       // this.fileSystem = new ArrayList<>();
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
            virtualfileSystem fs = new virtualfileSystem(path);
            
            fileSystem.add(clientscount, fs.get());
            clients[clientscount] = IP;
            clientscount++;
        }
      
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public List<List<Node>> get(String IP)
    {
        return fileSystem.get(find(clients,IP));
    }
    public String list (String IP)
    {
        String out ="";
        try{
           List<List<Node>> fs = fileSystem.get(find(clients,IP));
           print(fs);
           
       } catch (DirectoryIteratorException ex) {
           // I/O error encounted during the iteration, the cause is an IOException
           //throw ex.getCause();
       }
        return out;
    }
    
    private void print(List<Node> n)
    {
        for (Iterator<Node> it = n.iterator(); it.hasNext();) {
            List<Node> entry = (List<Node>) it.next();
            System.out.print(entry + "\n");
        } 
    }
    
    public static int find (String[] array , String name) {  
      return Arrays.asList(array).indexOf(name);  
    }  
    
   
    
}
