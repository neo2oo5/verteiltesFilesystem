package fileSystem;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.PingServer;
import substructure.GUIOutput;
import substructure.PathHelper;

/**
 * Grundklasse welche ein Dateisystem als Liste nachbildet(Singelton Klasse)
 * Dokumente:
 * http://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html
 * http://openjdk.java.net/projects/nio/javadoc/java/nio/file/DirectoryStream.html
 * bekannte Fehler: Exceptions werden weitergeworfen muessen noch abgefangen werden
 * @author Daniel Gauditz
 */
public class fileSystem implements Runnable
{
    OutputStream                    fos            = null;
    InputStream                     fis            = null;
    private final  GUIOutput        out            = GUIOutput.getInstance();
    private static String           outGoingList   = "System/myFileList.ser";
    private final List<List<Path>>  fileSystem     = new ArrayList<>();
    private String                  workingDir = System.getProperty("user.dir");
    private List<String>            clients        = new ArrayList<>();
    
    /**
     * Helper Funktion 
     * @param clients
     * @param name
     * @return Liste mit Integer welcher die Stelle angibt
     *         bei der String name zum ersten mal auftaucht
     */
    public static int find (List<String> clients , String name) 
    {  
       return clients.indexOf(name);
    }
    
    /**
     * Funktion welche die Instanzierung von außen verhindert
     */
    @Override public void run()
    {
      out.print("FileSystem Thread gestartet");
      getInstance();
    }

	
    /**
     * Funktion welche die einigartige Instanz generiert
     */
    private static class fileSystemHolder 
    {
        private static fileSystem INSTANCE = new fileSystem();
    }
    
    /**
     * Lazy Loading mit unsynchronisierter getinstance()-Methode
     * aber mit synchronisierter Instanzierung und doppeltem Null-Check
     * @return fileSystem Instanz zurueck
     */
    public static fileSystem getInstance() 
    {
        if(fileSystemHolder.INSTANCE == null)
        {
            synchronized(fileSystemHolder.INSTANCE)
            {
                if(fileSystemHolder.INSTANCE == null)
                {
                    fileSystemHolder.INSTANCE = new fileSystem();
                }
            }
        }
        return fileSystemHolder.INSTANCE;
    }
    
    //Update
    
    
    /**
     * Schaut ob man auf dem Ordner eine Schreibberechtigung hat
     * @param path
     * @return false wenn nein true wenn ja
     */
    public boolean isAccessDenied(String path)
    {
         File file = new File(path);
         return !file.canWrite();
    }
    
    public boolean isFolerToLarge(String Path)
    {
        int folderSize = new File(Path).list().length;
        return folderSize >= 50;
    }
    
    /**
     * Ueberprueft ob das File nicht groeßer als 50 MB ist.
     * @param entry
     * @return false wenn zu groß true wenn ok
     */
    private boolean checkFileSize(Path entry)
    {
        File file = new File(entry.toString());
        long fileSize = file.length();
        return fileSize <= (5*Math.pow(10,7));
    }
    	
    /**
     * Funktion welche rekursiv Ordner durchsucht mit Hilfe eines Stacks
     * @param Path
     * @return gibt Ergebnise der Durchsuchung zurueck 
     * @throws IOException 
     */
    private List <Path> initFS(Path Path)
    {
        Deque<Path> stack = new ArrayDeque<>();
        final List<Path> result = new LinkedList<>();
        stack.push(Path);
        while(!stack.isEmpty())
        {
            Path path = stack.pop();
            try
            {
                if(!Files.isHidden(path) && Files.isWritable(path))
                {
                    try (DirectoryStream<Path> stream =
                            Files.newDirectoryStream(path))
                    {
                        for(Path entry : stream)
                        {
                            if (Files.isDirectory(entry))
                            {
                                if(isFolerToLarge(entry.toString()) == false)
                                {
                                   /* out.print("(fileSystem - isFolderToLarge) "
                                            + ": nicht indexiert -->" 
                                            + entry, 2);*/
                                }
                                else
                                {
                                    stack.push(entry);
                                }
                            }
                            else
                            {
                                if(checkFileSize(entry) == false)
                                {
                                    out.print("(fileSystem - checkFileSize) : "
                                            + "nicht indexiert -->" + entry, 2);
                                }
                                else
                                {
                                    result.add(entry);
                                }
                            }
                        }
                    }
                    catch(IOException ex)
                    {
                        out.print("(fileSystem - initFS) : " 
                                + ex.toString(), 3);
                    }
                }
            } catch (IOException ex)
            {
                out.print("(fileSystem - initFS) : " + ex.toString(), 3);
            }  
        }
        return result;
    }
	
    /**
     * Erzeugt das Abbild des FileSystems
     * @param IP
     * @param path 
     */
    public void setNewFileSystem(String IP, String path) 
    {
        Path finalPath = Paths.get(path);
        try
        {
            fileSystem.set(find(clients, IP), initFS(finalPath));  
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            fileSystem.add(clients.size(), initFS(finalPath));
            clients.add(IP);
            out.print("neuer Client hinzugefuegt");
        }    
        outGoingList(IP);
       // out.print("fileSystem erfolgreich erstellt");
    }
    
    /**
     * Funktion fuegt manuel dem FileSystem ein Element zu
     * @param IP
     * @param Path 
     */
    public void addElement(String IP, String Path)
    {
        List<Path> localFileSystem = fileSystem.get(find(clients, IP));
        localFileSystem.add(Paths.get(Path));
        fileSystem.set(find(clients,IP), localFileSystem);
    }
    
    /**
     * Loescht ein Element manuel aus dem FileSystem
     * @param IP
     * @param Path 
     */
    public void deleteElement(String IP, String Path)
    {
        List<Path> localFileSystem = fileSystem.get(find(clients, IP));
        localFileSystem.remove(Paths.get(Path));
        fileSystem.set(find(clients,IP), localFileSystem);
    }
    /**
     * Hilfsfunktion
     * @param IP
     * @return gibt einen bestimmte IP zurueck
     */
    public List<Path> get(String IP)
    {
        return fileSystem.get(find(clients, IP));
    }
    
    public void remove(String IP)
    {
        fileSystem.remove(find(clients, IP));
        clients.remove(IP);
        out.print("IP erfolgreich entfernt");
    }
	
    /**
     * Hilfsfunktion
     * @return gibt alle IPs zurueck
     */
    public List<String> getAllIps()
    {
        return this.clients;
    }
	
    /**
     * Hilfsfunktion
     * @return gibt die Anzahl der Clienten zurueck
     */
    public int getClientCount()
    {
        return this.clients.size();
    }
	
    /**
     * Funktion die einen String aus dem FileSystem erzeugt
     * welcher dieses abbildet
     * @return FileSystem als String
     */
    @Override
    public String toString()
    {
        String outGoing="";
        List<String> ips = getAllIps();
        for(int z = 0; z < getClientCount(); z++)
        {
            List<Path> fs = get(ips.get(z));
            for (Path f : fs) 
            {
                String path = f.toString();
                outGoing += "IP: " + ips.get(z) + " Path: "+ path+"\n";
            }
        }   
        //out.print("FileSystem zu String erfolgreich");
        return outGoing;
    }
	
    /**
     * Funktion welche das FileSystem in einem String abspeichert 
     * (sieht so aus lokale Ip --##-- absoluter Pfad    --##-- ist das Trenn
     * zeichen)
     * diesen serialisiert und ihn in der Datei myFile.ser abspeichert
     * Die Datei liegt in Projektordner/System
     * @param IP
     * @return FileSystem abgebildet in einen String 
     */
    public String outGoingList (String IP) 
    {
        String output = "";
        try
        {
           for (Path entry: fileSystem.get(find(clients,IP)))
           {
               output += IP+"--##--"+entry + "\n";
           }
           fos = new FileOutputStream(outGoingList);
           ObjectOutputStream o = new ObjectOutputStream(fos);
           o.writeObject(output);
        }
        catch (IOException ex) 
        {
            out.print("(fileSystem - outGoingList) : " + ex.toString(), 3);
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch(IOException ex)
            {              
                out.print("(fileSystem - OutGoingList) : " + ex.toString(), 3);
            }
        }
      //  out.print("myFile.ser erfolgreich erstellt");
        return output;
    } 	
	
    /**
     * Funktion um die Datei myFileList.ser zu loeschen
     * @throws fileSystemException
     * @throws IOException 
     */
    public void deleteLocalObject() throws fileSystemException, IOException
    {
        Path path = 
                Paths.get(substructure.PathHelper.getFile("myFileList.ser"));
        Files.delete(path);
        out.print("Lokales Objekt erfolgreich geloescht");
    }
    
    /**
     * Funktion welche myFileList.ser erst in den Ordner 
     * ProjectFolder/System/tmp kopiert dort in inComingList.ser
     * umbenennt und dann myFileList.ser mit Hilfe von deleteOutGOingObject()
     * im Ordner ProjectFolder/System loescht
     * @throws IOException
     * @throws fileSystemException 
     */
    public void renameLocalObject() throws IOException, fileSystemException
    {
        Path path = 
                Paths.get(substructure.PathHelper.getFile("myFileList.ser"));
        Files.move(path, path.resolveSibling("inComingList"));
        out.print("Lokales Object erfolgreich umbenannt");
        deleteLocalObject();
    }	
    
    /**
     * Funktion welche inComingList.ser aus ProjectFolder/System/tmp loescht 
     */
    public void deleteInComingObject()
    {
        try
        {
            Path path =
                    Paths.get
        (substructure.PathHelper.getFile("inComingList.ser"));
            Files.delete(path);
        }
        catch (fileSystemException | IOException ex)
        {
            out.print("(fileSystem - deleteInComingObject) : " 
                    + ex.toString(), 3);
        }
        out.print("Eingehendes Objekt erfolgreich geloescht");
    }
	
    /**
     * Funktion welche aus dem serialisierten Datei inComingList.ser 
     * das FileSystem eines anderen Clienten ausliest und mit dem lokalen 
     * Dateisystem verbindet, so dass man auch dessen Daten im Explorer 
     * einsehen kann 
     */
    public void mergeList()
    {
        String inComingList = null;
        try
        {
            inComingList = PathHelper.getFile("Downloads"
                    + File.separator + "inComingList.ser");
        } catch (fileSystemException ex)
        {
            out.print("(fileSystem - mergeList) : " + ex.toString(), 3);
        }
      
        try
        {
            
            fis = new FileInputStream(inComingList);
             
            try (ObjectInputStream o = new ObjectInputStream(fis))
            {
                inComingList = (String) o.readObject();
            }  
        }
        catch(IOException | ClassNotFoundException ex)
        {
            out.print("(fileSystem - mergeList) : " + ex.toString(), 3);
        }
        finally
        {
            try
            {
               fis.close(); 
            }
            catch(IOException ex)
            {
                out.print("(fileSystem - mergeList) : " + ex.toString(), 3);
            }
        }
       
        List<Path> result = new ArrayList<>();
        String[] parts = null;
        if(inComingList!= null)
        {
            parts = inComingList.split("\n");
        }
        
        for (String part : parts)
        {
            if (part.length() > 0)
            {
                String[] seperatedString = part.split("--##--", 2);
                if(seperatedString.length > 0)
                {
                    
                    String path = seperatedString[1];
                    Path finalPath = Paths.get(path);
                    String IP = seperatedString[0];
                    result.add(finalPath);
                    try
                    {
                        
                        fileSystem.set(find(clients, IP), result);
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        fileSystem.add(clients.size(),result);
                        clients.add(IP);
                    }
                }
            }
        }   
        out.print("FileSysteme erfolgreich gemerged");
    }
    
}
