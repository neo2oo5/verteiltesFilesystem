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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Lamparari
 */
/**
 * run class "Handler" with threads
 */
public class Handler implements Runnable
{

    private Socket client;

    /**
     *
     * @param client
     */
    public Handler(Socket client)
    {
        this.client = client;
    }

    @Override
    public void run()
    {
        try
        {
            /**
             * get the output and buffer the input
             */
            OutputStream out = client.getOutputStream();
            BufferedReader reader;
            /**
             * convert to a string and write into an outputsteam
             */
            try (PrintWriter writer = new PrintWriter(out))
            {
                /**
                 * get input stream and buffer it
                 */
                InputStream in = client.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                /**
                 * handler for the file editor interfaces
                 */
                String s = null;
                while ((s = reader.readLine()) != null)
                {
                    String[] args = s.split(Pattern.quote("#entf#"));
                    int anz = args.length - 1;
                    /**
                     * file.rename handler
                     */
                    if (args[anz].equals("FileRename"))
                    {
                        boolean renameFile = Rename.renameFile(args[0], args[1], args[2]);
                        System.out.println("Rename successfull?: " + renameFile);
                    } /**
                     * file.delete handler
                     */
                    else if (args[anz].equals("FileDelete"))
                    {
                        boolean deleteFile = Delete.deleteFile(args[0], args[1]);
                        System.out.println("Delete successfull?: " + deleteFile);
                    } /**
                     * file.create handler
                     */
                    else if (args[anz].equals("FileCreate"))
                    {
                        boolean createFile = Create.createFile(args[0], args[1]);
                        System.out.println("Create successfull?: " + createFile);
                    } else if (args[anz].equals("FileTransfer"))
                    {
                        System.out.println("test1");
                        boolean transferFile = FileTransfer.FT(args);
                        System.out.println("Transfer successfull?: " + transferFile);
                    }
                    else if (args[anz].equals("CheckAdminLoggedin"))
                    {
                        File file = new File(substructure.PathHelper.getFile("admin.loggedin"));
                        boolean exists = file.exists();

                        if (exists)
                        {
                            System.out.println("ex-------");
                            String doWhat = "AntwortAdminLoggedin";
                            String[] argsNeu = new String[3];
                            argsNeu[0] = args[0];
                            argsNeu[1] = "true";
                            argsNeu[2] = doWhat;
                            StartClientServer.startClient(argsNeu);
                        }
                        else System.out.println("ex nicht");

                    } else if (args[anz].equals("AntwortAdminLoggedin"))
                    {
                        network.AdminPannel.setLoggedin(true);
                    }
                }
                reader.close();
                writer.close();
                client.close();
            }
            /**
             * catch exceptions and logg them
             */
        } catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
