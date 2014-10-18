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
    static GUIOutput outTxT = GUIOutput.getInstance();

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

        OutputStream outPS = null;

        try
        {
            /**
             * get the output and buffer the input
             */
            outPS = client.getOutputStream();
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
                    } else if (args[anz].equals("FileTransferClient"))
                    {
                        FiletransferClient.FileTransferClient(args);
                    } else if (args[anz].equals("CheckAdminLoggedin"))
                    {
                        try
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
                        } catch (fileSystemException ex)
                        {
                            // Keine Rückmeldung da nicht von bedeutung
                        }

                    } else if (args[anz].equals("AntwortAdminLoggedin"))
                    {
                        network.AdminPannel.setLoggedin(true);
                    } else if (args[anz].equals("AdminMessage"))
                    {
                        outTxT.print("(Handler) AdminMessage: " + args[0], 1);
                        if(args[0].equals("Admin Logged out!")) AdminPannel.setLoggedin(false);
                    } else if (args[anz].equals("AdminKickUser"))
                    {
                        String ownIP = gui.Config.getCurrentIp();

                        if (args[0].equals(ownIP))
                        {
                            IPList.clearList();
                            IPList.InsertIpInList(ownIP);
                            outTxT.print("(Handler) you removed from network by an admin");

                        } else
                        {
                            IPList.removeIP(args[0]);
                            outTxT.print("(Handler) Exit: " + args[0] + " get kicked");
                        }
                    } else if (args[anz].equals("Exit"))
                    {
                        IPList.removeIP(args[0]);
                        outTxT.print("(Handler) Exit: " + args[0] + " get removed");
                    } else if (args[anz].equals("ChangeOwnIP"))
                    {
                        IPList.replaceIP(args[0], args[1]);

                        outTxT.print("(Handler) ChangeOwnIP: " + args[0] + " to " + args[1]);
                    } else if (args[anz].equals("newClient"))
                    {
                        IPList.InsertIpInList(args[0]);
                        outTxT.print("(Handler) newClient eintrag: " + args[0]);
                    }
                     else if (args[anz].equals("arrangePort"))
                    {
                        DynamicPorts dp = DynamicPorts.getInstance();
                        
                        //Bearbeite Anfrage
                        if(args[2].length()== 5)
                        {
                            if(args[2].codePointAt(4) == 1)
                            {

                                if(dp.setPort(Integer.valueOf(args[2].substring(0, 3))) != -1 )
                                {
                                    //sent ok (port nr)
                                    dp.arrangePort(args[0], args[2] + "1", true);
                                    outTxT.print("Port " + args[2].substring(0, 3) + " wurde zum Transfer vorgeschalgen und aktzeptiert");
                                }
                                else
                                {
                                    //sent -1
                                    dp.arrangePort(args[0], args[2] + "0", true);
                                    outTxT.print("Port " + args[2].substring(0, 3) + " wurde abgelehnt da er schon benutzt wird");

                                }
                            }
                        }
                        // Bearbeite Antwort
                        else if(args[2].length()== 6)
                        {
                            if(args[2].codePointAt(5) == 1)
                            {
                                if(dp.setPort(Integer.valueOf(args[2].substring(0, 3))) != -1 )
                                {
                                    //sent ok (port nr)
                                    outTxT.print("Port " + args[2].substring(0, 3) + " wurde zum Transfer aktzeptiert");
                                }
                                else
                                {
                                    outTxT.print("Port" + args[2].substring(0, 3) + " wurde abgelehnt");
                                }
                            }
                        }
                        
                        
                                
                        
                        outTxT.print("(Handler) Port wird mit Client ausgehandel: " + args[0]);
                    }
                }
                reader.close();
                writer.close();
                client.close();
            } catch (IOException ex)
            {
                outTxT.print("(Handler - run - if) : " + ex.toString(), 2);
            }
        } catch (IOException ex)
        {
            outTxT.print("(Handler - run - if) : " + ex.toString(), 2);
        } finally
        {
            try
            {
                outPS.close();
            } catch (IOException ex)
            {
                outTxT.print("(Handler - run - if) : " + ex.toString(), 2);
            }
        }
    }
}
