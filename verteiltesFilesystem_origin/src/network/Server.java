/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 * Used Libraries
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
/**
 * class to run a computer as a server
 */
public class Server
{

    static GUIOutput out = GUIOutput.getInstance();

    /**
     *
     */
    public static void server()
    {

        /**
         * Thread pool with a fixed number of threads
         */
        ExecutorService executor = Executors.newFixedThreadPool(80);

        /**
         * server socket, named "server"
         */
        ServerSocket server;
        try
        {
            /**
             * server port = 1717
             */
            server = new ServerSocket(1717);
            /**
             * check if the server was started
             */
            out.print("Programm gestartet!", 1);
            out.print("Server Gestartet!", 1);

            /**
             * search for client requests to accept them
             */
            while (true)
            {
                /**
                 * wait for a client request
                 */
                Socket client = server.accept();
                executor.execute(new Handler(client));

            }
            /**
             * catch exceptions and log them
             */
        } catch (IOException ex)
        {
            out.print("(Server - server) : " + ex.toString(), 2);
        }

    }
}
