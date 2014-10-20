/**
 * Package
 */
package network;

/**
 * Klasse StartClientServer
 * 
 * Klasse dient dazu den Server oder Client als thread zu starten
 * - Client gibt den gewünschten String Array mit
 * 
 * @author David Lampa
 * @version 1.0
 * 
 */
public class StartClientServer
{
    /**
     * Klasse StartServer
     * - dient dazu den Server als Thread zu starten
     * 
     * @Runnable
     */
    static class StartServer implements Runnable
    {

        @Override
        /**
         * Starte den Server
         */
        public void run()
        {
            Server.server();
        }
    }

    /**
     * Klasse StartClient
     * - dient dazu den Clienten als Thread zu starten
     * - mit dem gewünschten String Array zum Übergeben
     * 
     * @Runnable
     */
    static class StartClient implements Runnable
    {

        /**
         * Variablen Initialisieren
         */
        String[] args;

        /**
         * Konstruktur, dient dazu, um dne gewünschten String mit zu geben
         */
        public StartClient(String args[])
        {
            this.args = args;
        }

        @Override
        /**
         * Clienten Starten
         */
        public void run()
        {
            Client.client(args);
        }
    }

    /**
     * Thread startServer
     * - Startet den Server als Thread
     * 
     * @return server // gibt zurück ob Thread start erfolgreich war
     */
    public static Thread startServer()
    {
        Thread server = new Thread(new StartServer());
        server.start();
        return server;

    }

    /**
     * Thread startClient
     * - Startet den Clienten als Thread
     * - Übergibt den gewünschten String Array
     * 
     * @param args String[] // gewünschtes String Array, welches übergeben werden soll
     * @return server // gibt zurück ob Thread start erfolgreich war
     */
    public static Thread startClient(String args[])
    {
        Thread client = new Thread(new StartClient(args));
        client.start();
        return client;
    }
}
