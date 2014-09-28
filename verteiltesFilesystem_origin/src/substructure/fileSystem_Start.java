/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package substructure;

import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import gui.GUI;
import gui.GuiPromptHelper;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xoxoxo
 */
public class fileSystem_Start {
    private static FileInputStream input;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException, fileSystemException {
        
        /** Singelton Klasse
         * Im gesamten Projekt benutzbar aufruf ueber folgenden ein Zeiler
         *   GUIOutput out =  GUIOutput.getInstance();
         *   out.print(Object msg, int modelevel)
         *  Level:
         * 0) None
         * 1) Information (Default)
         * 2) Warning
         * 3) Error
         *
         * Wird Standart maeßig in log.txt geschrieben
         * Kann in config.properties den modelevel geaendert werden
         *
         */
        fileSystem c = fileSystem.getInstance();
        Path path = Paths.get("E:/BAF");
        c.setnewFileSystem("127.0.0.1", path);
        
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            { 
               
                new GUI().setVisible(true);
                
            }
        });
        
            /*
            String debug ="";
            
            Properties prop = new Properties();
            boolean trigger = false;
            
            input = new FileInputStream("config.properties");
            
            // load a properties file
            prop.load(input);
            
            do
            {
            // get the property value and print it out
            try{
            debug = prop.getProperty("ROOT_DIR");
            trigger = true;
            fileSystem  c = fileSystem.getInstance();
            c.setnewFileSystem("127.0.0.1", debug);
            // c.setnewFileSystem("127.0.0.2", "  c.setnewFileSystem("127.0.0.1", debug);/home/xoxoxo/Downloads");
            //System.out.print(c.listAll());
            System.out.print(c.list("127.0.0.1"));
            }
            catch(Exception e)
            {
            //new GuiPromptHelper(GuiPromptHelper.showWarning, "Output: Modelevel konnte nicht gelesen werden "+e);
            trigger = false;
            Thread.currentThread().sleep(5000);
            }
            }while(trigger == false);
            
            */
       
	
        
        
    }
    
}
