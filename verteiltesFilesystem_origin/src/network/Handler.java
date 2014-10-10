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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
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
        try
        {
            /**
             * get the output and buffer the input
             */
            OutputStream outPS = client.getOutputStream();
            BufferedReader reader;
            /**
             * convert to a string and write into an outputsteam
             */
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
                        FiletransferServer.FileTransferServer(args);
                    } else if (args[anz].equals("CheckAdminLoggedin"))
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
                        } else
                        {
                            System.out.println("ex nicht");
                        }

                    } else if (args[anz].equals("AntwortAdminLoggedin"))
                    {
                        network.AdminPannel.setLoggedin(true);
                    } else if (args[anz].equals("AdminMessage"))
                    {
                        out.print(args[0], 1);
                    } else if (args[anz].equals("AdminKickUser"))
                    {
                        String iplist = substructure.PathHelper.getFile("IPs.txt");
                        int anzahl = 0;
                        String anServer = null;
                        BufferedReader inFile = null;
                        inFile = new BufferedReader(new FileReader(iplist));
                        String ip = null;
                        String newIPList[] = null;
                        while ((ip = inFile.readLine()) != null)
                        {
                            if (!ip.equals(args[0]))
                            {
                                //speichern
                                newIPList[anzahl++] = ip;
                            }

                        }

                        File file = new File(iplist);

                        /**
                         * write the IP in the address table
                         */
                        FileWriter writerNeu;
                        int i = 0;
                        if (anzahl == 0)
                        {
                            writerNeu = new FileWriter(file, false);
                        } else
                        {
                            while (i <= anzahl)
                            {
                                try
                                {
                                    if (i == 0)
                                    {
                                        writerNeu = new FileWriter(file, false);
                                    } else
                                    {
                                        writerNeu = new FileWriter(file, true);
                                    }

                                    writerNeu.write(newIPList[i]);
                                    writerNeu.write(System.getProperty("line.separator"));
                                    writerNeu.flush();
                                    writerNeu.close();
                                    i++;
                                    /**
                                     * catch exceptions
                                     */
                                } catch (IOException e)
                                {

                                    out.print("(Handler - run -> AdminKickUser) : " + e.toString(), 2);
                                }
                            }
                        }
                    }  else if (args[anz].equals("Exit"))
                    {
                        out.print("-> args[0] = " + args[0], 3);
                        out.print("-> args[1] = " + args[1], 3);
                        String iplist = substructure.PathHelper.getFile("IPs.txt");
                        int anzahl = 0;
                        String anServer = null;
                        BufferedReader inFile = null;
                        inFile = new BufferedReader(new FileReader(iplist));
                        String ip = null;
                        String newIPList[] = null;
                        while ((ip = inFile.readLine()) != null)
                        {
                            if (!ip.equals(args[0]))
                            {
                                //speichern
                                newIPList[anzahl++] = ip;
                            }

                        }

                        File file = new File(iplist);

                        /**
                         * write the IP in the address table
                         */
                        FileWriter writerNeu;
                        int i = 0;
                        if (anzahl == 0)
                        {
                            writerNeu = new FileWriter(file, false);
                        } else
                        {
                            while (i <= anzahl)
                            {
                                try
                                {
                                    if (i == 0)
                                    {
                                        writerNeu = new FileWriter(file, false);
                                    } else
                                    {
                                        writerNeu = new FileWriter(file, true);
                                    }

                                    writerNeu.write(newIPList[i]);
                                    writerNeu.write(System.getProperty("line.separator"));
                                    writerNeu.flush();
                                    writerNeu.close();
                                    i++;
                                    /**
                                     * catch exceptions
                                     */
                                } catch (IOException e)
                                {

                                    out.print("(Handler - run -> AdminKickUser) : " + e.toString(), 2);
                                }
                            }
                        }
                    } else if (args[anz].equals("ChangeOwnIP"))
                    {
                        /**
                                                * write the IP in the address table
                                                */
                        out.print("changeIp" + args[0]);
                        out.print("changeIp" + args[1]);
                        IPFile.setIPtoFile(args[1]);
                        gui.Config.setCurrentIp(args[1]);
                        IPFile.removeIPfromFile(gui.Config.getCurrentIp());
                        
                        

                                        
                    } else if (args[anz].equals("newClient"))
                    {
                        out.print("(Handler) newClient eintrag: " + args[0]);
                        String iplist = null;
                        try
                        {
                            iplist = substructure.PathHelper.getFile("IPs.txt");
                        } catch (fileSystemException ex)
                        {
                            out.print("(Handler - run -> ChangeOwnIP) : " + ex.toString(), 2);
                        }
                        File file = new File(iplist);
                        FileWriter writerNewClient;

                        try
                        {
                            writerNewClient = new FileWriter(file, true);

                            writerNewClient.write(args[0]);
                            writerNewClient.write(System.getProperty("line.separator"));
                            writerNewClient.flush();
                            writerNewClient.close();
                            /**
                             * catch exceptions
                             */
                        } catch (IOException e)
                        {
                            out.print("(CheckWhoIsOnline - run) : " + e.toString(), 2);
                        }
                       
                        out.print("(Handler) newClient abgeschlossen");
                    }
                }
                reader.close();
                writer.close();
                client.close();
            } catch (fileSystemException ex)
            {
                out.print("(Handler - run) : " + ex.toString(), 2);
            }
            /**
             * catch exceptions and logg them
             */
        } catch (IOException ex)
        {
            out.print("(Handler - run) : " + ex.toString(), 2);
        }
    }
}
