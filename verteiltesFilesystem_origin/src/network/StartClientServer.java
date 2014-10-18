/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author Lamparari
 */
public class StartClientServer
{

    /**
     * Use method runnable to run "StartClient" and "StartServer" as a Thread
     */
    static class StartServer implements Runnable
    {

        @Override
        /**
         * run the server
         */
        public void run()
        {
            Server.server();
        }

    }

    /**
     * Use method runnable to run "StartClient" and "StartServer" as a Thread
     */
    static class StartClient implements Runnable
    {

        String[] args;

        /**
         * start this objekt parallel to the upper class
         */
        public StartClient(String args[])
        {
            this.args = args;
        }

        @Override
        /**
         * run the client
         */
        public void run()
        {
            Client.client(args);
        }

    }

    /**
     * class to start the server
     */
    public static Thread startServer()
    {
        Thread server = new Thread(new StartServer());
        server.start();
        return server;

    }

    /**
     * class to start a client
     */
    public static Thread startClient(String args[])
    {
        Thread client = new Thread(new StartClient(args));
        client.start();
        return client;
    }

}
