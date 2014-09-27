/*
* Docs:
* http://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html
* http://openjdk.java.net/projects/nio/javadoc/java/nio/file/DirectoryStream.html
* Bildet die Dateisysteme nach als Liste (Singelton Klasse)
* Fehlende Funktionen: 
 */

package fileSystem;


import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Kevin.Bonner@gmx.de
 */
public class fileSystem{
    
    private final List<List<Path>> fileSystem = new ArrayList<>();
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
            isLocked = false;
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
    
    /**
     * nimmt neues Dateisystem auf z.B localhost oder Remoteclient
     * @param IP
     * @param path
     * @throws fileSystemException
     */
    public void setnewFileSystem(String IP, String path) throws fileSystemException
    {
        try{
        
            
            fileSystem.add(clientscount, initFS(path));
            clients[clientscount] = IP;
            clientscount++;
        }
      
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private List<Path> initFS(String Path) throws IOException
    {
         List<Path> result = new ArrayList<>();
       try (DirectoryStream<Path> stream = Files.newDirectoryStream(converttoPath(Path), "*")) {
           for (Path entry: stream) {
               if(Files.isDirectory(entry) == false && Files.isHidden(entry) == false)
               result.add(entry);
           }
       } catch (DirectoryIteratorException ex) {
           // I/O error encounted during the iteration, the cause is an IOException
           throw ex.getCause();
       }
       return result;
    }
    
    /**
     *
     * @param IP
     * @return
     */
    public List<Path> get(String IP)
    {
        return fileSystem.get(find(clients, IP));
    }

    /**
     *
     * @param IP
     * @return
     */
    public String list (String IP)
    {
        String out ="";
        try{
           for (Path entry: fileSystem.get(find(clients,IP)))
           {
                       out += entry + " \n";
           }
           
           
       } catch (DirectoryIteratorException ex) {
           // I/O error encounted during the iteration, the cause is an IOException
           //throw ex.getCause();
       }
        return out;
    }
    
    /**
     * PLATZHALTER 
     * Hier muss Davids Funktion eingebunden werden
     * Gibt die IP des Clienten zurück ->(ZWECK) erstellen eines Strings mit all deinen Files und deiner IP Adresse
     * @return 
     */
    public String myIp()
    {
        String IP = "127.0.0.1";
        return IP;
    }
    
    /**
     * Funktion zum Senden deiner Daten 
     * @param IP
     * @return 
     */
    public String outGoingList (String IP)
    {
        String output = "";
        try
        {
           for (Path entry: fileSystem.get(find(clients,IP)))
           {
               output += IP+"#"+entry + " \n";
           }
        }
        catch (DirectoryIteratorException ex) 
        {
           // I/O error encounted during the iteration, the cause is an IOException
           //throw ex.getCause();
       }
        return output;
    }   
    
    /**
     * Transferiert einen String in das Filesystem  
     * funktioniert noch nicht wie geplant sonder ruft setnewFileSystem wieder auf...
     * @throws fileSystemException 
     */
    public void mergeInComingList() throws fileSystemException
    {
        String inComingList = "172.1.1.9#E:\\BAF\n184.2.2.9#E:\\BAF\\Test";
        String[] parts = inComingList.split("\n");
        for (int i=0;i<parts.length;i++)
        {
               String[] seperated=parts[i].split("#",2);
               setnewFileSystem(seperated[0],seperated[1]);
        }
    }
    
    /**
     * IPS als StingArray
     * @return 
     */
    public String[] getAllIps()
    {
        return this.clients;
    }
    /**
     * getClientCount
     * @return 
     */
    public int getClientCount()
    {
        return this.clientscount;
    }
    /******************************************************* Helper *****************************************************************************/
    
    
    /**
     *
     * @param array
     * @param name
     * @return
     */
    public static int find (String[] array , String name) {  
      return Arrays.asList(array).indexOf(name);  
    }
    
    private Path converttoPath(Object obj)
    {
       return FileSystems.getDefault().getPath(String.valueOf(obj));
    }
    
   
    
}
