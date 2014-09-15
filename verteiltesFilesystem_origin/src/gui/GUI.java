/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;





import substructure.GUIOutput;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.*;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
import java.awt.*;
import javax.swing.tree.DefaultMutableTreeNode;




/**
 *
 * @author xoxoxo
 */
public class GUI extends javax.swing.JFrame
{
    private  GUIOutput out =  GUIOutput.getInstance();
    private int ActiveTabIndex              =   0;
    /**
     * Creates new form GUI
     */
    public GUI()
    {
        initComponents();
        owninitComponents();
    }
    
 
    private void owninitComponents()
    {
      

        //tree.setPreferredSize(new Dimension(300, 150));
        
        
        AdminConfigPanel.setVisible(false);
       // imagetest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/hdd.jpeg")));
        //imagetest.setText("");
        
        //init ExplorerTab
        Explorer explorer   = new Explorer();
        explorer.init(jTabbedPane5);
        
        
        
        /*Set Icon Image*/
        /*
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("/substructure/hdd.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIconImage(image);
        
        */
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane5 = new javax.swing.JTabbedPane();
        AdminPanel = new javax.swing.JPanel();
        AdminLoginPanel = new javax.swing.JPanel();
        AdminLoginSent = new javax.swing.JButton();
        AdminLoginLabel = new javax.swing.JLabel();
        AdminUsernameField = new javax.swing.JTextField();
        AdminPasswordField = new javax.swing.JPasswordField();
        AdminConfigPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        ConfigPanel = new javax.swing.JPanel();
        PathName = new javax.swing.JLabel();
        Path = new javax.swing.JLabel();
        FolderChooser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Verteiltes Filesystem");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTabbedPane5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane5MouseClicked(evt);
            }
        });

        AdminLoginSent.setText("Anmelden");
        AdminLoginSent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AdminLoginSentMouseClicked(evt);
            }
        });
        AdminLoginSent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminLoginSentActionPerformed(evt);
            }
        });

        AdminLoginLabel.setText("Login:");

        AdminUsernameField.setText("jTextField1");

        AdminPasswordField.setText("jPasswordField1");

        javax.swing.GroupLayout AdminLoginPanelLayout = new javax.swing.GroupLayout(AdminLoginPanel);
        AdminLoginPanel.setLayout(AdminLoginPanelLayout);
        AdminLoginPanelLayout.setHorizontalGroup(
            AdminLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AdminLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AdminLoginSent, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AdminPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AdminUsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AdminLoginLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AdminLoginPanelLayout.setVerticalGroup(
            AdminLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AdminLoginLabel)
                .addGap(37, 37, 37)
                .addComponent(AdminUsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(AdminPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(AdminLoginSent)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout AdminConfigPanelLayout = new javax.swing.GroupLayout(AdminConfigPanel);
        AdminConfigPanel.setLayout(AdminConfigPanelLayout);
        AdminConfigPanelLayout.setHorizontalGroup(
            AdminConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminConfigPanelLayout.createSequentialGroup()
                .addGap(249, 249, 249)
                .addComponent(jLabel4)
                .addContainerGap(418, Short.MAX_VALUE))
        );
        AdminConfigPanelLayout.setVerticalGroup(
            AdminConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminConfigPanelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel4)
                .addContainerGap(283, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AdminPanelLayout = new javax.swing.GroupLayout(AdminPanel);
        AdminPanel.setLayout(AdminPanelLayout);
        AdminPanelLayout.setHorizontalGroup(
            AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminPanelLayout.createSequentialGroup()
                .addComponent(AdminConfigPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AdminLoginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2232, Short.MAX_VALUE))
        );
        AdminPanelLayout.setVerticalGroup(
            AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminPanelLayout.createSequentialGroup()
                .addGroup(AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdminPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(AdminConfigPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AdminPanelLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(AdminLoginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(2430, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Admin", AdminPanel);

        PathName.setText("Aktueller Pfad:");

        Path.setText("Pfad");

        FolderChooser.setText("Ordner wählen");
        FolderChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FolderChooserMouseClicked(evt);
            }
        });
        FolderChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FolderChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ConfigPanelLayout = new javax.swing.GroupLayout(ConfigPanel);
        ConfigPanel.setLayout(ConfigPanelLayout);
        ConfigPanelLayout.setHorizontalGroup(
            ConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConfigPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(ConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FolderChooser)
                    .addGroup(ConfigPanelLayout.createSequentialGroup()
                        .addComponent(PathName)
                        .addGap(18, 18, 18)
                        .addComponent(Path)))
                .addContainerGap(2969, Short.MAX_VALUE))
        );
        ConfigPanelLayout.setVerticalGroup(
            ConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConfigPanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(ConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PathName)
                    .addComponent(Path))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FolderChooser)
                .addContainerGap(2697, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Config", ConfigPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 3129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 2804, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane5MouseClicked
        // TODO add your handling code here:

        ActiveTabIndex = jTabbedPane5.getSelectedIndex();

        out.print("Panelindex: " + ActiveTabIndex);

        switch(ActiveTabIndex)
        {
            case 0:
            break;
            case 1: //admin         = new Admin();
            break;
            case 2: //Config       = new Config();

            //ExplorerPanel.setVisible(true);
            System.out.println("case 2");
            break;

        }
        
        out.print("test");
        
        

    }//GEN-LAST:event_jTabbedPane5MouseClicked

    private void FolderChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FolderChooserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FolderChooserActionPerformed

    private void FolderChooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FolderChooserMouseClicked
        /*
        *   Erstellt ordner auswahl
        *  speichert Pfad in config.properties
        */

        JFileChooser jfc = new javax.swing.JFileChooser(".");
        jfc.setApproveButtonText("Auswählen");
        jfc.setDialogTitle("Bitte Verzeichnis auswählen");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int auswahl = jfc.showOpenDialog(new JFrame());
        if (auswahl == JFileChooser.APPROVE_OPTION)
        {
            String getPath = jfc.getSelectedFile().getPath();
            Path.setText(getPath);
            //out.print(Path);

            Properties prop = new Properties();
            OutputStream output = null;

            try {

                output = new FileOutputStream("config.properties");

                // set the properties value
                prop.setProperty("ROOT_DIR", getPath);

                // save properties to project root folder
                prop.store(output, null);

            } catch (IOException io) {

            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }//GEN-LAST:event_FolderChooserMouseClicked

    private void AdminLoginSentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminLoginSentActionPerformed
        //Switcht zwischen dem Login und Admin Panel

        if(Admin.Login(AdminUsernameField.getText(), AdminPasswordField.getText()))
        {
            AdminLoginPanel.setVisible(false);
            AdminConfigPanel.setVisible(true);
        }

    }//GEN-LAST:event_AdminLoginSentActionPerformed

    private void AdminLoginSentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdminLoginSentMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_AdminLoginSentMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdminConfigPanel;
    private javax.swing.JLabel AdminLoginLabel;
    private javax.swing.JPanel AdminLoginPanel;
    private javax.swing.JButton AdminLoginSent;
    private javax.swing.JPanel AdminPanel;
    private javax.swing.JPasswordField AdminPasswordField;
    private javax.swing.JTextField AdminUsernameField;
    private javax.swing.JPanel ConfigPanel;
    private javax.swing.JButton FolderChooser;
    private javax.swing.JLabel Path;
    private javax.swing.JLabel PathName;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTabbedPane jTabbedPane5;
    // End of variables declaration//GEN-END:variables
    
    
  
}
