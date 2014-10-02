/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package substructure;
import gui.ColorPane;
import gui.GuiPromptHelper;
import java.awt.Color;
import java.awt.Dimension;

import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author xoxoxo
 */
public class GUIOutput extends Output{
    
    protected static final Color D_Red     = Color.getHSBColor( 0.000f, 1.000f, 0.502f );
    protected static final Color D_Yellow  = Color.getHSBColor( 0.167f, 1.000f, 0.502f );
    protected static final Color D_Green   = Color.getHSBColor( 0.333f, 1.000f, 0.502f );
    protected static final Color D_White   = Color.getHSBColor( 0.000f, 0.000f, 0.753f );
    protected static final Color B_Black   = Color.getHSBColor( 0.000f, 0.000f, 0.502f );
    private static GUIOutput instance = null;
    private ArrayList<String> log = new ArrayList( );
    JFrame f;
    ColorPane pane;
    JScrollPane spane;
    
    
    public static GUIOutput getInstance() {
      if(instance == null) {
         instance = new GUIOutput();
      }
      return instance;
   }
    
    public void setVisible(boolean e)
    {
        f.setVisible(e);
    }
    
    private GUIOutput()
    {
         f = new JFrame("Log");
         pane = new ColorPane();
         f.setPreferredSize(new Dimension(600, 400));
         f.setSize(600, 400);
         f.setVisible(false);
  
    }
  
    
    private void readLogfile()
    {
        try {
		BufferedReader in = new BufferedReader(substructure.PathHelper.getFile(Logfile));
		String line;
               
		while ((line = in.readLine()) != null) {
			log.add(line);
		}
	} catch (Exception e) {
		new GuiPromptHelper(GuiPromptHelper.showWarning, "GUIOutput: Es gab ein Fehler beim lesen des Logfiles. " + e.toString());
	}
    }
    
    private Color getANSIColor(String ANSIColor) {
       
        if (ANSIColor.equals(ANSI_RED))   { return D_Red; }
        else if (ANSIColor.equals(ANSI_GREEN))   { return D_Green; }
        else if (ANSIColor.equals(ANSI_YELLOW))   { return D_Yellow; }
        else{return D_White;}

     }
    /**
     *
     * @param msg
     * @param mode
     * @param debug
     */
    @Override
    public void out(Object msg, int mode, int debug)
    {
            setLogfile();
            super.out(msg, mode, debug);
            System.out.flush();
            readLogfile();
           
            
           
                pane.setText("");
                setText(pane);
                pane.validate();
                spane = new JScrollPane(pane);
                spane.validate();
                f.setContentPane(spane);
                f.pack();
                
                
            
           
  
    }
 
    
    private void setText(ColorPane pane)
    {   

        for(String p : log){
            
           /* p = p.toString().replace(ANSI_RED, "")
                                 .replace(ANSI_YELLOW, "")
                                 .replace(ANSI_GREEN, "");*/
            if(p.contains(ANSI_RED))
            {   
                pane.append(getANSIColor(ANSI_RED), p.toString().replace(ANSI_RED, "") + "\n");
            }
            
            else if(p.contains(ANSI_YELLOW))
            {   
                pane.append(getANSIColor(ANSI_YELLOW), p.toString().replace(ANSI_YELLOW, "") + "\n");
            }
          
            else if(p.contains(ANSI_GREEN))
            {   
                pane.append(getANSIColor(ANSI_GREEN), p.toString().replace(ANSI_GREEN, "") + "\n");
            }
          
            
            
        }
        
      
    }
   
    

}
