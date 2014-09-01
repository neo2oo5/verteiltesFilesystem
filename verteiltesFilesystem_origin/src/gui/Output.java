/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;
import java.io.*;
import java.io.FileOutputStream;
import java.util.Properties;



/**
 *
 * @author xoxoxo
 */
public class Output
{
    /*
        * Ein wenig Farbe fuer den Terminal
        *
        */
    protected String Logfile                   = "log.txt";
    protected PrintStream orig                 = System.out;
    protected static final String ANSI_BLACK   = "\u001B[30m";
    protected static final String ANSI_WHITE   = "\u001B[37m";
    protected static final String ANSI_RED     = "\u001B[31m";
    protected static final String ANSI_GREEN   = "\u001B[32m";
    protected static final String ANSI_YELLOW  = "\u001B[33m";
   
    public Output()
    {
        
    }
    
    public Output(Object msg)
    {
        setLogfile();
        print(msg);
        
        setStdout();
        print(msg);
    }
    
    public Output(Object msg, int modelevel)
    {
        setLogfile();
        print(msg, modelevel);
        
        setStdout();
        print(msg, modelevel);
    }
    
    protected void setStdout()
    {
        System.setOut(orig);
    }
    
    protected void setLogfile()
    {
        try{   
            System.setOut(new PrintStream(new FileOutputStream(Logfile,true)));  
        }
        catch(Exception ex)
        {
            new GuiPromptHelper(GuiPromptHelper.showError, ex.toString());
        }  
    }
    
    
    
    public  void print(Object msg)
    {
        print(msg, 1);
    }
    
    public  void print(Object msg, int modelevel)
    {
        int debug = 0;
        Properties prop = new Properties();
	InputStream input = null;
 
        /*Liest aus Datei den ModeLevel
                * Level: 
                * 0) None
                * 1) Information (Default)
                * 2) Warning
                * 3) Error
                */
	try {
 
		input = new FileInputStream("config.properties");
 
		// load a properties file
		prop.load(input);
 
		// get the property value and print it out
                 debug = Integer.parseInt(prop.getProperty("MODE"));

		
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        
      
         
          
            
            out(msg, modelevel, debug);
        
    }
    
    public void out(Object msg, int mode, int debug)
    {
         String print = "";
            
            
            if(debug >= 3 && mode == 3)
            {
                print = ANSI_RED + "ERROR: ";   
            }
            else if(debug >= 2 && mode == 2)
            {
                print = ANSI_YELLOW + "WARNING: ";
            }
            else if(debug >= 1 && mode == 1)
            {
                print = ANSI_GREEN + "INFORMATION: ";
            }
            
            if(mode != 0){
                System.out.println(print + msg);
            }
    }
    
}
