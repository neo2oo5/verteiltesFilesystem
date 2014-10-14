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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.*;
import network.getIPv4Address;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class Config
{
    private static  GUIOutput out =  GUIOutput.getInstance();
    private static fileSystem c = fileSystem.getInstance();
    private static JLabel folderL, pathL = new JLabel(), logL, ipName[] = new JLabel[100];
    private JPanel configP, interfaceP;
    private static JButton folderB, logB, netAdapter[] = new JButton[100];
    private static ButtonGroup netAdapterG = new ButtonGroup();
    private static final String folderCMD = "folder", logCMD = "log", configFile = "config.properties", value = "ROOT_DIR";
    private static String  currentIP=null;
    
    /**
     *
     * @param Pane
     */
    public Config(javax.swing.JTabbedPane Pane)
    {
        
         createNetAdapterPanel();
        createFilechooserAndLogComponents();
        
       
        
        
        
        
       
        
        Pane.addTab("Config", configP);
    }
    
    private void createNetAdapterPanel()
    {
         ArrayList<String[]> ips = getIPList();
        
            interfaceP = new JPanel();
            interfaceP.setVisible(true);
            interfaceP.setSize(new Dimension(500, 250));
         
            int x = 0;
            int y = 5;
            int width = 200;
            int height = 20;
            for(int i = 0; i < ips.size(); i++)
            {
                String[] tmp = ips.get(i);
                
                ipName[i] = new JLabel();
                ipName[i].setText(tmp[0]);
                ipName[i].setBounds(x, y += 20, width, height);
                ipName[i].setVisible(true);
                interfaceP.add(ipName[i]);
                
                netAdapter[i] = new JButton();
                netAdapter[i].setText(tmp[1]);
                netAdapter[i].setBounds(x, y += 20, width, height);
                netAdapter[i].setActionCommand(tmp[1]);
                netAdapter[i].addActionListener(new InterfacelListener());
                netAdapter[i].setVisible(true);
                netAdapterG.add(netAdapter[i]);
                interfaceP.add(netAdapter[i]);
                
            }
            
            
            
            
            
    }
    
    private void createFilechooserAndLogComponents()
    {
        
         /*
                * Filechooser
                */
        folderL = new JLabel("Aktueller Pfad: ");
        
        if(!isRootDir())
        {
            pathL.setText("Pfad");
        }
        else
        {
            pathL.setText(getRootDir());
        }
       
        folderB = new JButton("Ordner wählen");
        logL    = new JLabel("Zeige Logfile: ");
        logB    = new JButton("Aus");
        configP = new JPanel();
        configP.setVisible(true);
        configP.setSize(new Dimension(1024, 900));
        
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
        
        configP.add(interfaceP);
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
                return !prop.getProperty(value).isEmpty();
                
                
            } catch (FileNotFoundException ex) {
                out.print("(Config.java) " + configFile + "nicht gefunden");
            } catch (IOException ex) {
                out.print("(Config.java) " + ex.toString());
            } catch (NullPointerException ex) {
                //out.print("(Config.java) " + ex.toString());
                return false;
            } catch (fileSystemException ex) { 
             Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
         } 
            
            return true;
            
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
        boolean trigger = true;
        do{   
            String tmpPath = createFileChooser();
            
            if(tmpPath.length() > 0)
            {
                trigger = c.isAccessDenied(tmpPath);
            }
            else
            {
                trigger = true;
            }
                
        }while(trigger);
    }
    
    static private String createFileChooser()
    {
      /*
                                *   Erstellt ordner auswahl
                                *  speichert Pfad in config.properties
                                */
                                String getPath = "";
                                JFileChooser jfc = new javax.swing.JFileChooser(".");
                                jfc.setApproveButtonText("Auswählen");
                                jfc.setDialogTitle("Bitte Verzeichnis auswählen");
                                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                int auswahl = jfc.showOpenDialog(new JFrame());
                                if (auswahl == JFileChooser.APPROVE_OPTION)
                                {
                                    getPath = jfc.getSelectedFile().getPath();
                                    pathL.setText(getPath);
                                    //out.print(Path);

                                    Properties prop = new Properties();
                                    OutputStream output = null;

                                    try {

                                       

                                        // set the properties value
                                        prop.setProperty(value, getPath);

                                        // save properties to project root folder
                                        prop.store(new FileWriter(substructure.PathHelper.getFile(configFile)), null);
                                        
                                       

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
                                
                                return getPath;
    }
    
     static public void setCurrentIp(String IP)
    {
        currentIP = IP;
    }
    
    static public String getCurrentIp()
    {
        return currentIP;
    }
    
    static public ArrayList<String[]> getIPList()
    {
        ArrayList<String[]> ipList = new ArrayList<>();
        String[] AdapterName = null;
        
        
        try {
            ArrayList<String> ips = getIPv4Address.getIPv4Address();
            
            for(int i=0; i < ips.size(); i++)
            {
                AdapterName = ips.get(i).split(Pattern.quote("/"));
                ipList.add(AdapterName);
            }
 
        } catch (UnknownHostException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ipList;
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
                    if(out.getVisible())
                    {
                        logB.setText("An");
                        out.setVisible(true);
                    }
                    else
                    {
                        new GuiPromptHelper(GuiPromptHelper.showWarning, "GUILog wurde in den Start Parameter deaktiviert");
                    }
                }
            }
        }
        
  
        
    }

    private static class InterfacelListener implements ActionListener {

        public InterfacelListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            ArrayList<String[]> ips = getIPList();
            
            for(int i = 0; i < ips.size(); i++)
            {   String[] tmp = ips.get(i);
                
            
                if(tmp[1].equals(cmd))
                {
                    try {
                        
                        network.Interfaces.interfaceChangeOwnIP(getCurrentIp(), tmp[1]);
                    } catch (UnknownHostException ex) {
                        out.print("IP konnte nicht geändert werden.",3);
                    }
                }
            }
          
        }
    }
}
