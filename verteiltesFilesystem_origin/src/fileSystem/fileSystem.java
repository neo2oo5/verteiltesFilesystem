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
import substructure.GUIOutput;


public class fileSystem
{
    private static  GUIOutput out =  GUIOutput.getInstance();
    private final List<List<Path>> fileSystem = new ArrayList<>();
    private int clientscount            = 0;
    private String clients[]            = new String[100];
    private String[] newElementList        = new String[100];
    private boolean isLocked            = false;
    private boolean isInterrupted       = false;
    private String  whoLocked;
    private String  token;
    private String workingDir = System.getProperty("user.dir");
    OutputStream fos = null;
    private static String outGoingList = "System/myFileList.ser";
    InputStream fis = null;
	
	private fileSystem()
    {
       //DUMMY
    }
	

    public static fileSystem getInstance() 
	{
        return fileSystemHolder.INSTANCE;
    }
	
	private static class fileSystemHolder 
	{
        private static final fileSystem INSTANCE = new fileSystem();
    }
	
    private List <Path> initFS(Path Path) throws IOException
    {
        Deque<Path> stack = new ArrayDeque<>();
        final List<Path> result = new LinkedList<>();
        stack.push(Path);
        while(!stack.isEmpty())
        {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(stack.pop()))
            {
                for(Path entry : stream)
                {
                    if (Files.isDirectory(entry))
                    {
                        stack.push(entry);
                    }
                    else
                    {
                        result.add(entry);
                        String newElement = entry.toString();
                    }
                }
            }
            catch(NoSuchFileException e)
            {
                out.print("Pfad existiert nicht");
            }
            catch(AccessDeniedException e)
            {
                out.print("Sie haben für diesen Pfad keine Berechtigung");
            }
        }
        return result;
    }
	
    public void setnewFileSystem(String IP, String path) throws fileSystemException
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
                 fileSystem.add(clientscount, initFS(finalPath));
                 clients[clientscount] = IP;
                 clientscount++;
             }
        }
     
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
	
	public List<Path> get(String IP)
    {
        return fileSystem.get(find(clients, IP));
    }
	
	public String[] getAllIps()
    {
        return this.clients;
    }
	
	public int getClientCount()
    {
        return this.clientscount;
    }
	
	public String fileSystemToString (String IP)
    {
        String out ="";
        try
		{	
           for (Path entry: fileSystem.get(find(clients,IP)))
           {
                       out += entry + " \n";
           }
           
           
        } 
	    catch (DirectoryIteratorException ex) 
	    {
           // I/O error encounted during the iteration, the cause is an IOException
           //throw ex.getCause();
        }
        return out;
    }
	
    public String outGoingList (String IP) throws FileNotFoundException, IOException
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
	
	public void renameOutGoingObject() throws IOException, fileSystemException
    {
        Path path = Paths.get(substructure.PathHelper.getFile("myFileList.ser"));
        Files.move(path, path.resolveSibling("InComingList"));
    }
	
	public void delteOutGoingObject() throws fileSystemException, IOException
    {
        Path path = Paths.get(substructure.PathHelper.getFile("myFileList.ser"));
        Files.delete(path);
    }
	
	public void mergeList(String inComingList) throws fileSystemException, FileNotFoundException, IOException, ClassNotFoundException
    {
        try
        {
            fis = new FileInputStream(inComingList);
            ObjectInputStream o =new ObjectInputStream(fis);
            inComingList = (String) o.readObject();
        }
        catch(IOException e)
        {
            System.err.println(e);
        }
        catch(ClassNotFoundException e)    
        {
            System.err.println(e);
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
        for(int count=0;count<parts.length;count++)
        {
            String[] seperatedString = parts[count].split("--##--",2);
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
                fileSystem.add(clientscount,result);
                clients[clientscount] = IP;
                clientscount++;
            }
        }
    }
	
	public void deleteInComingObject() throws IOException, fileSystemException
    {
        Path path = Paths.get(substructure.PathHelper.getFile("myFileList.ser"));
        Files.delete(path);
    }

	/******************************************************* Helper *****************************************************************************/
    
    public static int find (String[] array , String name) 
    {  
      return Arrays.asList(array).indexOf(name);  
    }
}