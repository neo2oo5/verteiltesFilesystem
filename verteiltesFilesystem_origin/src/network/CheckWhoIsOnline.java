/**
 * Package
 */
package network;

/**
 * Imports
 */
import gui.Config;
import java.util.List;
import java.util.ArrayList;
import fileSystem.fileSystem;

/**
 * Klasse CheckWhoIsOnline
 * 
 * Klasse die ein Multicast sendet (bei Systemstart)
 * Ansonsten auf ein Multicast neuer User Antwortet
 * - IP Übergibt eigene IP oder empfängt IP's zum eintragen in die IP Liste
 * 
 * @author David Lampa, Kevin Bonner
 * @version 1.0
 * 
 * @extends Thread
 */
public class CheckWhoIsOnline extends Thread
{
    /**
     * Variablen Initialisieren
     */
    private static Thread multicast = null;
    private static fileSystem c = fileSystem.getInstance();
    private static List<String[]> ipList = new ArrayList<>();
    private static boolean init = false;

    /**
     * Funktion doMulticast
     * 
     * Diese Funktion führt den Multicast aus (bei Systemstart)
     * Ansonsten auf ein Multicast neuer User Antwortet
     * - IP Übergibt eigene IP oder empfängt IP's zum eintragen in die IP Liste
     */
    public static void doMulticast()
    {
        /**
         * Neuen Thread mit Multicast starten
         * - erneut Multicast starten, wenn kein Multicast ausgeführt wird
         */
        multicast = new Thread(new SSDPNetworkClient());
        multicast.setName("Multicast");
        
        if (multicast == null)
        {
            multicast.start();
        } else
        {
            if (!multicast.isAlive())
            {
                multicast.start();
            }
        }
    }

    /**
     * Funktion doPingTest
     * 
     * Diese Funktion prüft ob die IP's die im FileSystem vorhanden sind noch erreichbar sind
     * Falls dies nicht der Fall sein sollte werden die Daten aus dem FileSystem entfernt
     */
    public static void doPingTest()
    {
        /**
         * Hole eine IP aus dem FileSystem und Pinge diese an
         */
        initPingTest();

        List<String> ips = c.getAllIps();
        for (int z = 0; z < c.getClientCount(); z++)
        {
            String currentIP = ips.get(z);
            int index = -1;
            String[] ipSet = new String[2];

            //fetch index from ip array
            for (int i = 0; i < ipList.size(); i++)
            {
                ipSet = ipList.get(i);
                if (currentIP.equals(ipSet[1]))
                {
                    index = ipList.indexOf(i);
                }
            }
            //count +1
            if (index >= 0)
            {
                if (!PingServer.PingServer(currentIP) && !currentIP.equals(Config.getCurrentIp()))
                {
                    ipSet[0] = String.valueOf(Integer.getInteger(ipSet[0]) + 1);
                }

                ipList.set(index, ipSet);

                /**
                 * IP ist nicht erreichbar und wird aus dem FileSystem 
                 * und aus der IPliste entfernt
                 */
                for (int i = 0; i < ipList.size(); i++)
                {
                    String[] ipSetnew = new String[2];
                    ipSet = ipList.get(i);
                    if (Integer.getInteger(ipSetnew[0]) >= 3)
                    {
                        //remove from fs
                        c.remove(ipSetnew[1]);
                        //remove from IPList
                        IPList.removeIP(ipSetnew[0]);
                    }
                }
            }

        }
    }

    /**
     * Funktion initPingTest
     * 
     * Diese Funktion prüft ob die IP's die im FileSystem vorhanden sind noch erreichbar sind
     */
    private static void initPingTest()
{
        if (init == false)
        {
            /**
             * Hole die IPListe aus dem FileSystem und prüfe diese auf erreichbarkeit
             */
            List<String> ips = c.getAllIps();
            for (int z = 0; z < c.getClientCount(); z++)
            {
                String currentIP = ips.get(z);
                String[] ipSet = new String[2];
                ipSet[0] = "1";
                ipSet[1] = currentIP;

                if (!PingServer.PingServer(currentIP))
                {
                    ipSet[0] = String.valueOf(Integer.getInteger(ipSet[0]) + 1);
                }

                ipList.add(ipSet);
            }
            init = true;
        }
    }
}
