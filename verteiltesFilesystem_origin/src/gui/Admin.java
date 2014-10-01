/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.Dimension;
import java.io.FileInputStream;
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
     
     
     
     public Admin(JPanel panel)
     {
         out.print("Admin Konstruktor");
         AdminControlPanel apanel = new AdminControlPanel();
         apanel.setVisible(true);
         apanel.setSize(new Dimension(500, 500));
         
         panel.add(apanel);
       
        
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
 
		input = new FileInputStream("user.properties");
 
		// load a properties file
		prop.load(input);
 
		// get the property value and print it out
                propusername = prop.getProperty("ADMINUSERNAME");
                proppassword = prop.getProperty("ADMINPASSWORD");
		out.print("Conf Username: " + propusername);
                out.print("Conf Password: " + proppassword);
                out.print("Param Username: " + username);
                out.print("Param Password: " + password);
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
