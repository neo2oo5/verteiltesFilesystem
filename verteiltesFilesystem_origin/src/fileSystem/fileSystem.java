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
     * @param array
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
         if(file.canWrite())
         {
             return false;
         }
         else
         {
             return true;
         }
    }
    
    public boolean isFolerToLarge(String Path)
    {
        int folderSize = new File(Path).list().length;
        if(folderSize<50)
        {
            return false;
        }
        else
        {
            return true;
        }
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
        if(fileSize>(5*Math.pow(10,7)))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    	
    /**
     * Funktion welche rekursiv Ordner durchsucht mit Hilfe eines Stacks
     * @param Path
     * @return gibt Ergebnise der Durchsuchung zurueck 
     * @throws IOException 
     */
    private List <Path> initFS(Path Path) throws IOException
    {
        Deque<Path> stack = new ArrayDeque<>();
        final List<Path> result = new LinkedList<>();
        stack.push(Path);
        while(!stack.isEmpty())
        {
            Path path = stack.pop();
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
                                out.print("(fileSystem - isFolderToLarge) "
                                        + ": nicht indexiert -->" + entry, 2);
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
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            
        }
        return result;
    }
	
    /**
     * Erzeugt das Abbild des FileSystems
     * @param IP
     * @param path
     * @throws fileSystemException 
     */
    public void setnewFileSystem(String IP, String path) 
            throws fileSystemException
    {
        try
        {
             Path finalPath = Paths.get(path);
             try
             {  
                 fileSystem.set(find(clients, IP), initFS(finalPath));
                 
             }
             catch(ArrayIndexOutOfBoundsException e)
             {
                 fileSystem.add(clients.size(), initFS(finalPath));
                 clients.add(IP);
                 
             }
             
             outGoingList(IP);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
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
     * @param IP
     * @return FileSystem als String
     */
    public String fileSystemToString (String IP) //Nachfragen ob benoetigt
    {
        String out = "";
        try
	{	
           for (Path entry: fileSystem.get(find(clients,IP)))
           {
               out += entry + "\n";
           }          
        } 
	catch (DirectoryIteratorException ex) 
	{
           // I/O error encounted during the iteration, the 
            //cause is an IOException
           //throw ex.getCause();
        }
        return out;
    }
	
    /**
     * Funktion welche das FileSystem in einem String abspeichert 
     * (sieht so aus lokale Ip --##-- absoluter Pfad    --##-- ist das Trenn
     * zeichen)
     * diesen serialisiert und ihn in der Datei myFile.ser abspeichert
     * Die Datei liegt in Projektordner/System
     * @param IP
     * @return FileSystem abgebildet in einen String
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public String outGoingList (String IP) 
            throws FileNotFoundException, IOException
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
        catch (IOException e) 
        {
            System.err.println(e);
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch(Exception e)
            {              
                e.printStackTrace();
            }
        }
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
        deleteLocalObject();
    }	
    
    /**
     * Funktion welche inComingList.ser aus ProjectFolder/System/tmp loescht
     * @throws IOException
     * @throws fileSystemException 
     */
    public void deleteInComingObject()
    {
        try
        {
            Path path =
                    Paths.get
        (substructure.PathHelper.getFile("inComingList.ser"));
            Files.delete(path);
        } catch (fileSystemException ex)
        {
            out.print("(fileSystem - deleteInComingObject) : " 
                    + ex.toString(), 3);
        } catch (IOException ex)
        {
            out.print("(fileSystem - deleteInComingObject) : " 
                    + ex.toString(), 3);
        }
    }
	
    /**
     * Funktion welche aus dem serialisierten Datei inComingList.ser 
     * das FileSystem eines anderen Clienten ausliest und mit dem lokalen 
     * Dateisystem verbindet, so dass man auch dessen Daten im Explorer 
     * einsehen kann
     * @throws IOException
     * @throws ClassNotFoundException 
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
            //fis.close();   
        }
        catch(IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
               fis.close(); 
            }
            catch(Exception e)
            {
		//Dummy
                 
            }
        }
       
        List<Path> result = new ArrayList<>();
        String[] parts = inComingList.split("\n");
        
        for(int count=0 ;count < parts.length ;count++)
        {
            if(parts[count].length() > 0)
            {
                String[] seperatedString = parts[count].split("--##--",2);

                if(seperatedString.length > 0)
                {
                    
                    String path = seperatedString[1];
                    Path finalPath = Paths.get(path);
                    String IP = seperatedString[0];
                    result.add(finalPath);
                    try
                    {
                        fileSystem.remove(find(clients, seperatedString[0]));
                        fileSystem.add(find(clients, IP), result);
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        fileSystem.add(clients.size(),result);
                        clients.add(IP);
                    }
                }
            }
        }   
    }
    
    @Override
    public String toString()
    {
        String out="";
        List<String> ips = getAllIps();

            for(int z = 0; z < getClientCount(); z++)
             {
                 List<Path> fs = get(ips.get(z));
        
                
                for (int i = 0; i < fs.size(); i++) {

                    String path = fs.get(i).toString();
                    
                    out += "IP: " + ips.get(z) + " Path: "+ path+"\n";
                }
             }
            
        return out;
    }
}
