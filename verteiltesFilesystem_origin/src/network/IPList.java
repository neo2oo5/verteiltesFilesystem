/**
 * Package
 */
package network;

/**
 * Imports
 */
import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import substructure.GUIOutput;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import substructure.PathHelper;
import java.io.FileNotFoundException;
import fileSystem.fileSystemException;
import java.util.Random;

/**
 * Klasse IPList
 * 
 * Diese Klasse dient dazu aus der IPs.txt Datei die verbundenen IP's in eine
 * IPListe zu machen , mit der man die gewünschten Aktionen ausführen kann
 * 
 * @author David Lampa
 * @version 1.0
 * 
 */
public class IPList
{
    /**
     * Variablen Initialisieren
     */
    static GUIOutput outMsg = GUIOutput.getInstance();
    private static String ipListPath;

    /**
     *
     * @return
     */
    public static String GetIPListPath()
    {
        try
        {
            return PathHelper.getFile("IPs.txt");
        } catch (fileSystemException ex)
        {
            outMsg.print("(GetIPListPath) " + ex.toString(), 3);
            return null;
        }
    }

    /**
     *
     * @param IPtoInsert
     */
    public static synchronized void InsertIpInList(String IPtoInsert)
    {
        BufferedWriter fw = null;
        try
        {
            ipListPath = GetIPListPath();
            File file = new File(ipListPath);

            if (!SearchIP(IPtoInsert))
            {
                fw = new BufferedWriter(new FileWriter(file, true));
                synchronized (fw)
                {
                    fw.write(IPtoInsert);
                    fw.write(LineSeperator());
                    fw.flush();
                    //writer.write(System.getProperty("line.separator"));
                }

            }

        } catch (IOException ex)
        {
            outMsg.print("(InsertIpInList) " + ex.toString(), 3);
        } finally
        {

            try
            {
                if (fw != null)
                {
                    fw.close();
                }
            } catch (IOException ex)
            {
                outMsg.print("(InsertIpInList) " + ex.toString(), 3);
            }
        }
    }

    /**
     *
     */
    public static void clearList()
    {
        ipListPath = GetIPListPath();
        File file = new File(ipListPath);
        FileWriter writer;
        try
        {
            writer = new FileWriter(file, false);
            writer.close();
        } catch (IOException ex)
        {
            outMsg.print("(InsertIpInList) " + ex.toString(), 3);
        }

    }

    /**
     *
     * @param searchedIP
     * @return
     */
    public static boolean SearchIP(String searchedIP)
    {
        boolean ipFound = false;

        List<String> IPListe = getIPList();

        for (String ip : IPListe)
        {
            if (ip.equals(searchedIP))
            {
                ipFound = true;
            }
        }

        return ipFound;
    }

    /**
     *
     * @param searchedIP
     */
    public static synchronized void removeIP(String searchedIP)
    {
        BufferedWriter fw = null;
        try
        {
            List<String> IPList = getIPList();

            IPList.remove(searchedIP);

            ipListPath = GetIPListPath();
            File file = new File(ipListPath);
            fw = new BufferedWriter(new FileWriter(file, false));

            for (String ip : IPList)
            {
                fw.write(ip);
                fw.write(LineSeperator());
                fw.flush();
            }

        } catch (IOException ex)
        {
            outMsg.print("(removeIP) " + ex.toString(), 3);

        } finally
        {
            try
            {
                fw.flush();
                fw.close();

            } catch (IOException ex)
            {
                outMsg.print(ex.toString());
            }
        }
    }

    /**
     *
     * @param searchedIP
     * @param toReplaceIP
     */
    public static synchronized void replaceIP(String searchedIP, String toReplaceIP)
    {
        FileWriter writer = null;
        try
        {

            List<String> IPList = getIPList();

            IPList.add(toReplaceIP);
            IPList.remove(searchedIP);

            ipListPath = GetIPListPath();
            File file = new File(ipListPath);
            writer = new FileWriter(file, false);

            for (String ip : IPList)
            {
                writer.write(ip);
                writer.write(LineSeperator());
                writer.flush();
            }

        } catch (IOException ex)
        {
            outMsg.print("(replaceIP) " + ex.toString(), 3);

        } finally
        {
            try
            {
                writer.close();
            } catch (IOException ex)
            {
                outMsg.print("(replaceIP) " + ex.toString(), 3);
            }
        }

    }

    /**
     *
     * @return
     */
    public static List<String> getIPList()
    {
        ArrayList<String> IPList = new ArrayList<>();
        try
        {
            BufferedReader inFile = null;

            ipListPath = GetIPListPath();
            inFile = new BufferedReader(new FileReader(ipListPath));
            String ip = null;
            while ((ip = inFile.readLine()) != null)
            {
                IPList.add(ip);
            }
            inFile.close();
        } catch (FileNotFoundException ex)
        {
            outMsg.print("(getIPList) " + ex.toString(), 3);
        } catch (IOException ex)
        {
            outMsg.print("(getIPList) " + ex.toString(), 3);
        }
        return Collections.synchronizedList(IPList);
    }
//       
//        
//
//      
//        
//
//        public static String anyItem()
//        {
//            Random randomGenerator = new Random();
//
//            int index = randomGenerator.nextInt(getIPList().size());
//            String item = getIPList().get(index);
//            return item;
//        }
//    public static List<String> getIPListRandom()
//    {
//        ArrayList<String> IPListRandom = new ArrayList<>();
//        List<String> IPList = getIPList();
//        int laengeListe = IPList.size();
//        
//        do
//        {
//            String item = anyItem();
//            
//            if(IPListRandom.indexOf(item) == -1)
//            {
//                IPListRandom.add(item);
//            }
//          
//            
//        }while(IPListRandom.size() < laengeListe);
//                
//        return Collections.synchronizedList(IPListRandom);
//    }

    public static String LineSeperator()
    {
        String os = substructure.PathHelper.getOSName();
        if (os.contains("Linux"))
        {
            return "\r";
        } else if (os.contains("Mac"))
        {

            return "\r";
        } else if (os.contains("Windows"))
        {
            return "\r\n";
        }
        return "\r\n";
    }

}
