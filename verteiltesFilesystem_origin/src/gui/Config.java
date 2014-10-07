/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import network.getIPv4Address;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class Config
{
    static  GUIOutput out =  GUIOutput.getInstance();
    static fileSystem c = fileSystem.getInstance();
    static JLabel folderL, pathL, logL;
    JPanel configP;
    static JButton folderB, logB;
    static String folderCMD = "folder", logCMD = "log", configFile = "config.properties", value = "ROOT_DIR", currentIP=null;
    
    /**
     *
     * @param Pane
     */
    public Config(javax.swing.JTabbedPane Pane)
    {
        folderL = new JLabel("Aktueller Pfad: ");
        
        if(isRootDir())
        {
            pathL   = new JLabel("Pfad");
        }
        else
        {
            pathL   = new JLabel(getRootDir());
        }
        
        folderB = new JButton("Ordner wählen");
        
        logL    = new JLabel("Zeige Logfile: ");
        logB    = new JButton("Aus");
        
        configP = new JPanel();
        
        folderB.setActionCommand("folder");
        logB.setActionCommand("log");
        
        folderB.addActionListener(new ConfigListener());
        logB.addActionListener(new ConfigListener());
        
        configP.setLayout(new GridBagLayout());
        
        GridBagConstraints cons = new GridBagConstraints();
        cons.insets = new Insets(5, 5, 5, 5);

         cons.gridwidth = GridBagConstraints.REMAINDER;
        //add Components to Panel
        
        configP.add(folderL, cons);
        configP.add(pathL, cons);
        
       
        configP.add(folderB, cons);
        
        configP.add(logL, cons);
        configP.add(logB, cons);
        
        
        Pane.addTab("Config", configP);
    }
    
    /**
     *
     * @return true or false if RootDir is set
     */
    static public boolean isRootDir()
        {
            
            Properties prop = new Properties();
            try {
                
                
                // load a properties file
                prop.load(new FileReader(substructure.PathHelper.getFile(configFile)));
                return prop.getProperty(value).isEmpty();
                
                
            } catch (FileNotFoundException ex) {
                out.print("(Config.java) " + configFile + "nicht gefunden");
            } catch (IOException ex) {
                out.print("(Config.java) " + ex.toString());
            } catch (NullPointerException ex) {
                out.print("(Config.java) " + ex.toString());
                return true;
            } catch (fileSystemException ex) { 
             Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
         } 
            
            return false;
            
        }

    /**
     *
     * @return Folder Path
     */
    static public String getRootDir()
        {
            
            Properties prop = new Properties();
            try {
                
                
                // load a properties file
                prop.load(new FileReader(substructure.PathHelper.getFile(configFile)));
                return prop.getProperty(value);
            } catch (FileNotFoundException ex) {
                out.print("(Config.java) " + configFile + "nicht gefunden");
            } catch (IOException ex) {
                out.print("(Config.java) " + ex.toString());
            }catch (fileSystemException ex) { 
             Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
         } 
            
           
            return null;
        }
    
    /**
     *
     */
    static public void filechooser()
    {
        /*
                                *   Erstellt ordner auswahl
                                *  speichert Pfad in config.properties
                                */

                                JFileChooser jfc = new javax.swing.JFileChooser(".");
                                jfc.setApproveButtonText("Auswählen");
                                jfc.setDialogTitle("Bitte Verzeichnis auswählen");
                                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                int auswahl = jfc.showOpenDialog(new JFrame());
                                if (auswahl == JFileChooser.APPROVE_OPTION)
                                {
                                    String getPath = jfc.getSelectedFile().getPath();
                                    pathL.setText(getPath);
                                    //out.print(Path);

                                    Properties prop = new Properties();
                                    OutputStream output = null;

                                    try {

                                       

                                        // set the properties value
                                        prop.setProperty(value, getPath);

                                        // save properties to project root folder
                                        prop.store(new FileWriter(substructure.PathHelper.getFile(configFile)), null);
                                        
                                        try
                                        {
                                            c.setnewFileSystem(getIPv4Address.getIPv4Address(), Config.getRootDir());
                                        } catch (fileSystemException ex)
                                        {
                                            out.print("Lokales FileSystem konnte nicht Indexiert werden");
                                        } catch (UnknownHostException ex)
                                        {
                                            out.print("(fileSystem_Start) - startSequence : " + ex.toString(), 2);
                                        }

                                    } catch (IOException io) {

                                    } catch (fileSystemException ex) { 
                                        out.print(ex.toString());
                                    } finally {
                                        if (output != null) {
                                            try {
                                                output.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }
                                }
                                else if(auswahl == JFileChooser.CANCEL_OPTION)
                                {
                                    new GuiPromptHelper(GuiPromptHelper.showWarning, "Ohne ausgewählten Pfad wird der Client sich nicht"
                                            + " ins Netz einwählen");
                                }
    }
    

    static public void setCurrentIp(String IP)
    {
        currentIP = IP;
    }
    
    static public String getCurrentIp()
    {
        return currentIP;
    }
    
    private static  class ConfigListener implements ActionListener {

        public ConfigListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String cmd = e.getActionCommand();
            if (folderCMD.equals(cmd)) 
            {
                filechooser();
            }
            else if (logCMD.equals(cmd))
            {
                if(logB.getText().equals("An"))
                {
                    logB.setText("Aus");
                    out.setVisible(false);
                }
                else
                {
                    logB.setText("An");
                    out.setVisible(true);
                }
            }
        }
        
  
        
    }
}
