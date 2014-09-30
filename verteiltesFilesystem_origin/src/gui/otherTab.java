/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.*;
import javax.swing.JTextArea;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class otherTab {
    
    private JTextArea aboutus;
    private JTextArea info;
    
    public otherTab(javax.swing.JTabbedPane TabbedPane)
    {
        aboutus = new JTextArea(loadTabContent("ueberuns.txt"));
        aboutus.setEditable(false);

        System.out.print(TabbedPane.getPreferredSize());

        
        
        
        
        
        
        
        
        TabbedPane.addTab("Über Uns", aboutus);
        TabbedPane.addTab("Info", new JTextArea(loadTabContent("info.txt")));
    }
    
    private String loadTabContent(String file)
    {
        String content = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(file)));
            String line = null;
            while((line = br.readLine()) != null) {
               content += line + "\n"; 
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
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
