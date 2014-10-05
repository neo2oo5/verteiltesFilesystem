/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package substructure;

import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import gui.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.plaf.ColorUIResource;
import network.getIPv4Address;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class fileSystem_Start
{

    private static FileInputStream input;
    static GUIOutput out = GUIOutput.getInstance();
    static fileSystem c = fileSystem.getInstance();
    public static GUI gUI = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException, fileSystemException
    {

        /**
         * Singelton Klasse Im gesamten Projekt benutzbar aufruf ueber folgenden
         * ein Zeiler GUIOutput out = GUIOutput.getInstance(); out.print(Object
         * msg, int modelevel) Level: 0) None 1) Information (Default) 2)
         * Warning 3) Error
         *
         * Wird Standart maeßig in log.txt geschrieben Kann in config.properties
         * den modelevel geaendert werden
         *
         */
        //network.Interfaces.inerfaceStartProgram();
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
                //set UI Defaults
                javax.swing.UIManager.put("nimbusBase", new ColorUIResource(0, 0, 0));
                javax.swing.UIManager.put("textForeground", new ColorUIResource(255, 69, 0));

                //start GUI
                gUI = new GUI();
                gUI.setVisible(true);
                gUI.setOnOffState();

                //initiate startSequence
                startSequence();

                //start scheduler
                new substructure.runTimeUpdater();

            }
        });

    }

    static private void startSequence()
    {
        if (Config.isRootDir())
        {
            Config.filechooser();
        } else
        {
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
        }
    }
}
