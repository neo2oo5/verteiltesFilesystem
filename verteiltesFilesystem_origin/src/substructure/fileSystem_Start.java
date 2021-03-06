package substructure;

import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import gui.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Die Haupt Datei des Programmes hier beginnt alles.
 * @author Kevin Bonner  - kevin.bonner@gmx.de
 */
public class fileSystem_Start
{

    private static FileInputStream input;
    static GUIOutput out = GUIOutput.getInstance();
    static fileSystem c = fileSystem.getInstance();

    /**
     *
     */
    public static GUI gUI = null;

    /**
     *
     */
    public static boolean Debug = false; //nur fuer debug

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @throws fileSystemException
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
         * switchOnOff: leitet ausgaben auf terminal oder gui log
         */
        out.switchOnOff(!Debug);

        /**
         * Startet FS als Thread
         *
         */
        Thread t1 = new Thread(new fileSystem());
        t1.setName("FileSystem");
        t1.start();

        /**
         * Startet die Netzwerkverbindung
         */
        network.Interfaces.interfaceStartProgram();

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

                //initiate startSequence
                startSequence();
                //set UI Defaults
                // javax.swing.UIManager.put("nimbusBase", new ColorUIResource(0, 0, 0));
                //javax.swing.UIManager.put("textForeground", new ColorUIResource(255, 69, 0));

                //start GUI
                gUI = new GUI();
                gUI.setVisible(true);
                gUI.setOnOffState();

                /*
                 *
                 */
                gUI.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent)
                    {
                        if (JOptionPane.showConfirmDialog(gUI,
                                "Wollen Sie das Programm wirklich beenden?", "Wirklich schließen?",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                        {
                            try
                            {

                                network.Interfaces.interfaceExitProg();
                                out.print("Client wird beendet");
                                //beendet Gui
                                gUI.dispose();
                                //beendet Prozess
                                System.exit(0);

                            } catch (UnknownHostException ex)
                            {
                                Logger.getLogger(fileSystem_Start.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });

                //start scheduler
                new substructure.runTimeUpdater();

            }
        });

    }

    static private void startSequence()
    {

        if (!Config.isRootDir())
        {
            Config.filechooser();
        } else if (c.isAccessDenied(Config.getRootDir()))
        {
            Config.filechooser();
        } else
        {
            c.setNewFileSystem(Config.getCurrentIp(), Config.getRootDir());
        }
    }
}
