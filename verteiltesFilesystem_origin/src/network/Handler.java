/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 * Used Libraries
 */
import fileSystem.fileSystemException;
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
import substructure.GUIOutput;

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
    static GUIOutput out = GUIOutput.getInstance();

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
        /**
         * get the output and buffer the input
         */
        OutputStream outPS = null;
        try
        {
            outPS = client.getOutputStream();
        } catch (IOException ex)
        {
            out.print("(Handler - run) : " + ex.toString(), 2);
        }
        BufferedReader reader;
        /**
         * convert to a string and write into an outputsteam
         */
        if (outPS != null)
        {
            try (PrintWriter writer = new PrintWriter(outPS))
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
                    } /**
                     * file.delete handler
                     */
                    else if (args[anz].equals("FileDelete"))
                    {
                        boolean deleteFile = Delete.deleteFile(args[0], args[1]);
                    } /**
                     * file.create handler
                     */
                    else if (args[anz].equals("FileCreate"))
                    {
                        boolean createFile = Create.createFile(args[0], args[1]);
                    } else if (args[anz].equals("FileTransfer"))
                    {
                        FiletransferServer.FileTransferServer(args);
                    } else if (args[anz].equals("CheckAdminLoggedin"))
                    {
                        File file = new File(substructure.PathHelper.getFile("admin.loggedin"));
                        boolean exists = file.exists();
                        if (exists)
                        {
                            String doWhat = "AntwortAdminLoggedin";
                            String[] argsNeu = new String[3];
                            argsNeu[0] = args[0];
                            argsNeu[1] = "true";
                            argsNeu[2] = doWhat;
                            StartClientServer.startClient(argsNeu);
                        }

                    } else if (args[anz].equals("AntwortAdminLoggedin"))
                    {
                        network.AdminPannel.setLoggedin(true);
                    } else if (args[anz].equals("AdminMessage"))
                    {
                        out.print("(Handler) AdminMessage: " + args[0], 1);
                    } else if (args[anz].equals("AdminKickUser"))
                    {
                        String ownIP = gui.Config.getCurrentIp();

                        if (args[0].equals(ownIP))
                        {
                            IPList.clearList();
                            IPList.InsertIpInList(ownIP);
                            out.print("(Handler) you removed from network by an admin");

                        } else
                        {
                            IPList.removeIP(args[0]);
                            out.print("(Handler) Exit: " + args[0] + " get kicked");
                        }
                    } else if (args[anz].equals("Exit"))
                    {
                        IPList.removeIP(args[0]);
                        out.print("(Handler) Exit: " + args[0] + " get removed");
                    } else if (args[anz].equals("ChangeOwnIP"))
                    {
                        IPList.replaceIP(args[0], args[1]);

                        out.print("(Handler) ChangeOwnIP: " + args[0] + " to " + args[1]);
                    } else if (args[anz].equals("newClient"))
                    {
                        IPList.InsertIpInList(args[0]);
                        out.print("(Handler) newClient eintrag: " + args[0]);
                    } else if (args[anz].equals("fileSize"))
                    {
                        out.print("FILESIZE", 3);
                        int fs = FiletransferServer.fileSize(args);
                        StringBuilder sb = new StringBuilder();
                        sb.append(fs);
                        String strI = sb.toString();
                        String anClient = strI + "#entf#" + "fileSizeAnswer";
                        writer.write(anClient);
                        writer.flush();
                    }
                }
                reader.close();
                writer.close();
                client.close();
            } catch (fileSystemException ex)
            {
                out.print("(Handler - run) : " + ex.toString(), 2);
            } catch (IOException ex)
            {
                out.print("(Handler - run - if) : " + ex.toString(), 2);
            }
        }
        /**
         * catch exceptions and logg them
         */
    }
}
