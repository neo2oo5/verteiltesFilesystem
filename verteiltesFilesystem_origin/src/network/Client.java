/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 * Used Libraries
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;
import substructure.GUIOutput;

/**
 *
 * @author Lamparari
 */
/**
 * class wich starts a client to communicate with the server *
 */
public class Client
{

    static GUIOutput outTXT = GUIOutput.getInstance();

    public static void client(String[] args)
    {
        /**
         * reads the input from the shell
         */
        Scanner eingabe = new Scanner(System.in);

        try
        {
            int j = 0;
            while(j < args.length){
                outTXT.print(j + " - " + args[j], 3);
                j++;
            }
            /**
             * initiate the client socket (Port 1717, name = input)
             */
            Socket client = new Socket(args[0], 1717);
            String anServer = "";
            anServer += args[1];
            for (int i = 2; i < args.length; i++)
            {
                anServer += "#entf#" + args[i];
            }

            /**
             * --- Streams ---
             */
            /**
             * Get the output and handle it
             */
            OutputStream out = client.getOutputStream();
            BufferedReader reader;
            /**
             * convert input to a string
             */
            try (PrintWriter writer = new PrintWriter(out))
            {
                InputStream in = client.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                // ---------------------

                writer.write(anServer);
                writer.flush();

                String s = null;
                while ((s = reader.readLine()) != null)
                {

                    outTXT.print("Client: Empfangen vom Server: " + s, 3);

                    writer.close();
                    reader.close();
                    client.close();
                }

//                /**
//                 * get the input
//                 */
//                InputStream in = client.getInputStream();
//                /**
//                 * Buffer the input
//                 */
//                reader = new BufferedReader(new InputStreamReader(in));
//
//                writer.write(anServer);
//                /**
//                 * stop to puffer the output of the printwriter
//                 */
//                writer.flush();
//                /**
//                 * close the applications
//                 */
//                reader = new BufferedReader(new InputStreamReader(in));
//                /**
//                 * handler for the file editor interfaces
//                 */
//                String s = null;
//                while ((s = reader.readLine()) != null)
//                {
//                    String[] argsNeu = s.split(Pattern.quote("#entf#"));
//                    int anz = argsNeu.length - 1;
//                    if (argsNeu[anz].equals("FileRename"))
//                    {
//                        System.out.println("-------- geht: " + argsNeu[0]);
//                        int fs = Integer.parseInt(argsNeu[0]);
//                        FiletransferClient.setFileSize(fs);
//                    }
//                }
//                writer.close();
//                reader.close();
//                client.close();
            }
            /**
             * catch exceptions and log them
             */
        } catch (IOException ex)
        {
            outTXT.print("(Client - client) : " + ex.toString(), 2);
        }

    }
}
