/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;
/** Used Libraries */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lamparari
 */

/** class wich starts a client to communicate with the server **/
public class Client
{

    public static void client(String[] args)
    {
        /** reads the input from the shell */
        Scanner eingabe = new Scanner(System.in);

        try
        {
            /** initiate the client socket (Port 1717, name = input) */
            Socket client = new Socket(args[0], 1717);
            String anServer = "";
            anServer += args[1];
            for (int i = 2; i < args.length; i++)
            {
                anServer += "#entf#" + args[i];
            }
            
            /** --- Streams --- */
            /** Get the output and handle it */
            OutputStream out = client.getOutputStream();
            BufferedReader reader;
            /** convert input to a string */
            try (PrintWriter writer = new PrintWriter(out))
            {
                /** get the input */
                InputStream in = client.getInputStream();
                /** Buffer the input */
                reader = new BufferedReader(new InputStreamReader(in));

                writer.write(anServer);
                /** stop to puffer the output of the printwriter */
                writer.flush();
                /** close the applications */
                writer.close();
                reader.close();
                client.close();

            }
        /** catch exceptions and log them */ 
        } catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
