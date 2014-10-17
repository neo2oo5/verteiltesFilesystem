/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import fileSystem.fileSystemException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import substructure.GUIOutput;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class otherTab {
    
    private JTextArea aboutus;
    private JTextArea info;
    static  GUIOutput out =  GUIOutput.getInstance();
    
    /**
     *
     * @param TabbedPane
     */
    public otherTab(javax.swing.JTabbedPane TabbedPane)
    {
        aboutus = new JTextArea(loadTabContent("ueberuns.txt"));
        aboutus.setEditable(false);
        //System.out.print(TabbedPane.getPreferredSize());

        info = new JTextArea(loadTabContent("info.txt"));
        info.setEditable(false);
        
        
        
        
        
        
        TabbedPane.addTab("Über Uns", aboutus);
        TabbedPane.addTab("Info", info);
    }
    
    private String loadTabContent(String file)
    {
        String content = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(substructure.PathHelper.getFile(file)));
            String line = null;
            while((line = br.readLine()) != null) {
               content += line + "\n"; 
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch (fileSystemException ex) { 
             out.print(ex.toString());
         } finally {
            if(br != null) {
                try {
                    br.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return content;
        
    }
}
