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
import static java.lang.Thread.sleep;
import java.text.DateFormat.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.*;
/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class GUIOutput extends Output{
    
    protected static final Color D_Red     = Color.getHSBColor( 0.000f, 1.000f, 0.502f );
    protected static final Color D_Yellow  = Color.getHSBColor( 0.167f, 1.000f, 0.502f );
    protected static final Color D_Green   = Color.getHSBColor( 0.333f, 1.000f, 0.502f );
    protected static final Color D_White   = Color.getHSBColor( 0.000f, 0.000f, 0.753f );
    protected static final Color B_Black   = Color.getHSBColor( 0.000f, 0.000f, 0.502f );
    private static GUIOutput instance = null;
    private List<String> log = Collections.synchronizedList(new ArrayList<String>());
    private List<String> queue = Collections.synchronizedList(new ArrayList<String>());
    JFrame f = new JFrame("Log");
    ColorPane pane = new ColorPane();;
    JScrollPane spane = new JScrollPane(pane);
    
    
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

         f.setPreferredSize(new Dimension(600, 400));
         f.setSize(600, 400);
         f.setVisible(false);
  
    }
  
    
    private void readLogfile()
    {
        try {
		BufferedReader in = new BufferedReader(new FileReader(substructure.PathHelper.getFile(Logfile)));
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
                
                spane.validate();
                f.setContentPane(spane);
               // f.pack();
                
                
            
           
  
    }
 
    
    private void setText(ColorPane pane)
    {   
        synchronized (queue) { 
            synchronized (log) { 
                for(int i = 0; i < log.size(); i++){
            
           /* p = p.toString().replace(ANSI_RED, "")
                                 .replace(ANSI_YELLOW, "")
                                 .replace(ANSI_GREEN, "");*/
          
            
             
                    if(log.get(i).contains(ANSI_RED))
                    {   
                        queue.add(ANSI_RED.toString() + "--##--" + log.get(i).toString().replace(ANSI_RED, "") + "\n");
                    }

                    else if(log.get(i).contains(ANSI_YELLOW))
                    {   
                        queue.add(ANSI_YELLOW.toString() + "--##--" +  log.get(i).toString().replace(ANSI_YELLOW, "") + "\n");
                    }

                    else if(log.get(i).contains(ANSI_GREEN))
                    {   
                        queue.add(ANSI_GREEN.toString() + "--##--" +  log.get(i).toString().replace(ANSI_GREEN, "") + "\n");
                    }
                    
                    
                                            for(int y= 0; y < queue.size(); y++)
                                            {
                                                String tmp[] = queue.get(y).split(Pattern.quote("--##--"));
                                                
                                                pane.append(getANSIColor(tmp[0]), tmp[1]);
                                            }
                                            
                    log.remove(i);
                                        
                }
            }
            //  pane.append(getANSIColor(ANSI_GREEN), p.toString().replace(ANSI_GREEN, "") + "\n");
        }
        
      
    }
    
   
   public void refreshGuiLog()
   {
       
       pane.validate();
       spane.validate();
       f.revalidate();
   }
   
    

}
