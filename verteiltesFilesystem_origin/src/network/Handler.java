/**
 * Package
 */
package network;

/**
 * Imports
 */
import java.io.File;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import substructure.GUIOutput;
import java.util.regex.Pattern;
import java.io.InputStreamReader;
import fileSystem.fileSystemException;

/**
 * Klasse Handler
 * 
 * Diese Klasse dient dazu, die vom anderen User gewünschte Aktion Lokal auszuführen
 * 
 * @author David Lampa
 * @version 1.0
 * 
 * @Runnable
 */
public class Handler implements Runnable
{
    /**
     * Variablen Initialisieren
     */
    private Socket client;
    static GUIOutput outMsg = GUIOutput.getInstance();

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
                        outMsg.print("(Handler) AdminMessage: " + args[0], 1);
                        if(args[0].equals("Admin Logged out!")) AdminPannel.setLoggedin(false);
                        else if(args[0].equals("Admin Logged in!")) AdminPannel.setLoggedin(true);
                    } else if (args[anz].equals("AdminKickUser"))
                    {
                        String ownIP = gui.Config.getCurrentIp();

                        if (args[0].equals(ownIP))
                        {
                            IPList.clearList();
                            IPList.InsertIpInList(ownIP);
                            outMsg.print("(Handler) you removed from network by an admin");

                        } else
                        {
                            IPList.removeIP(args[0]);
                            outMsg.print("(Handler) Exit: " + args[0] + " get kicked");
                        }
                    } else if (args[anz].equals("Exit"))
                    {
                        IPList.removeIP(args[0]);
                        outMsg.print("(Handler) Exit: " + args[0] + " get removed");
                    } else if (args[anz].equals("ChangeOwnIP"))
                    {
                        IPList.replaceIP(args[0], args[1]);

                        outMsg.print("(Handler) ChangeOwnIP: " + args[0] + " to " + args[1]);
                    } else if (args[anz].equals("newClient"))
                    {
                        IPList.InsertIpInList(args[0]);
                        outMsg.print("(Handler) newClient eintrag: " + args[0]);
                    }
                     else if (args[anz].equals("arrangePort"))
                    {
                        DynamicPorts dp = DynamicPorts.getInstance();
                        String[] ipp = args[1].split(Pattern.quote("."));
                        
                        outMsg.print("(Handler) Port wird mit Client ausgehandelt: " + args[0]);
                        //Bearbeite Anfrage
                        if(ipp[2].length()== 1)
                        {
                            if(ipp[2].equals("1"))
                            {

                                if(dp.setPort(ipp[0], Integer.valueOf(ipp[1])) != -1 )
                                {
                                    //sent ok (port nr)
                                    dp.arrangePort(args[0], ipp[1] + ".11", true);
                                    outMsg.print("Port " + ipp[1] + " wurde zum Transfer vorgeschlagen und aktzeptiert");
                                }
                                else
                                {
                                    //sent -1
                                    dp.arrangePort(args[0], ipp[1] + ".10", true);
                                    outMsg.print("Port " + ipp[1] + " wurde abgelehnt da er schon benutzt wird");

                                }
                            }
                        }
                        // Bearbeite Antwort
                        else if(ipp[2].length()== 2)
                        {
                            if(ipp[2].codePointAt(1) == 1)
                            {
                                if(dp.setPort(ipp[0], Integer.valueOf(ipp[1])) != -1 )
                                {
                                    //sent ok (port nr)
                                    outMsg.print("Port " + ipp[1] + " wurde zum Transfer aktzeptiert");
                                }
                                else
                                {
                                    outMsg.print("Port" + ipp[1] + " wurde abgelehnt");
                                }
                            }
                        }
                    }
                }
                /**
                 * reader, writer, client close
                 */
                reader.close();
                writer.close();
                client.close();
            } catch (IOException ex)
            {
                /**
                 * Fehler abfangen und ausgeben
                 */
                outMsg.print("(Handler - run - if) : " + ex.toString(), 3);
            }
        } catch (IOException ex)
        {
            /**
             * Fehler abfangen und ausgeben
             */
            outMsg.print("(Handler - run - if) : " + ex.toString(), 3);
        } finally
        {
            try
            {
                outPS.close();
            } catch (IOException ex)
            {
                /**
                 * Fehler abfangen und ausgeben
                 */
                outMsg.print("(Handler - run - if) : " + ex.toString(), 3);
            }
        }
    }
}
