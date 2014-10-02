/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.*;
import substructure.GUIOutput;

/**
 *
 * @author xoxoxo
 */
public class Admin
{
     private static  GUIOutput out =  GUIOutput.getInstance();
     private JLabel welcome = new JLabel("Willkomen");
     private AdminControlPanel apanel;
     
     
     
     public Admin(JPanel configpanel, JPanel loginpanel)
     {
         out.print("Admin Konstruktor");
         apanel = new AdminControlPanel(loginpanel);
         
         
         configpanel.add(apanel);
       
        
     }
     
     public void refresh()
     {
         apanel.refreshUserlist();
     }
    /**
     *
     * @param username
     * @param password
     * @return
     */
    public static boolean Login(String username, String password)
    {
        String propusername="";
        String proppassword="";
        
        if(username == null && username.isEmpty() && password == null && password.isEmpty())
        {
            out.print("Keine Login Daten!");
            return false;
        }
        
        Properties prop = new Properties();
	InputStream input = null;
 
	try {
 
	
 
		// load a properties file
		prop.load(new FileReader(substructure.PathHelper.getFile("user.properties")));
 
		// get the property value and print it out
                propusername = prop.getProperty("ADMINUSERNAME");
                proppassword = prop.getProperty("ADMINPASSWORD");
		out.print("Conf Username: " + propusername);
                out.print("Conf Password: " + proppassword);
                out.print("Param Username: " + username);
                out.print("Param Password: " + password);
	} catch (IOException ex) {
		ex.printStackTrace();
	} 
        
        if(username.equals(propusername) && password.equals(proppassword))
        {
            
            out.print("Login erfolgreich");
            return true;
        }
        else
        {
            out.print("Falsche login Daten!");
            return false;
        }
        
        
    }
}
