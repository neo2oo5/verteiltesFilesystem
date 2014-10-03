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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lamparari
 */
/**
 * class to run a computer as a server
 */
public class Server
{

    public static void server()
    {

        /**
         * Thread pool with a fixed number of threads
         */
        ExecutorService executor = Executors.newFixedThreadPool(30);

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
            System.out.println("Server gestartet!\n");

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
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
