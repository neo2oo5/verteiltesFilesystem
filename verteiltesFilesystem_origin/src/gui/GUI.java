/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import substructure.GUIOutput;


/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class GUI extends javax.swing.JFrame
{

    private GUIOutput out = GUIOutput.getInstance();
    private int ActiveTabIndex = 0;
    private Explorer explorer;
    private Admin admin = null;
    private KreisPanel state = new KreisPanel();
    private JLabel statel = new JLabel("Offline");

    /**
     * Creates new form GUI
     */
    public GUI()
    {

        initComponents();
        owninitComponents();
        
    }

    public void setOnOffState()
    {
        try
        {
            if (network.Interfaces.inerfaceNetworkOnline() == true)
            {
                state.setGreen();
                statel.setText("Online");
            } else
            {
                state.setRed();
                statel.setText("Offline");
            }
        } catch (UnknownHostException ex)
        {
            out.print("(GUI) - setOnOffState : " + ex.toString(), 2);
        }
    }

    private void owninitComponents()
    {

        /*Network status display*/
        state.setBounds(3, 7, 25, 25);
        state.setVisible(true);

        statel.setBounds(30, 7, 100, 25);
        statel.setVisible(true);

        add(state);
        add(statel);

        /*Set Admin Defaults*/
        AdminConfigPanel.setVisible(false);
        AdminLoginPanel.setVisible(true);
        admin = new Admin(AdminConfigPanel, AdminLoginPanel);

        /*Create required Tabs*/
        new otherTab(jTabbedPane5);

        new Config(jTabbedPane5);

        explorer = new Explorer(jTabbedPane5);

        /*Set Icon Image*/
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File(substructure.PathHelper.getFile("hdd.png")));
        } catch (IOException e)
        {
            out.print("(GUI) - owninitComponents : " + e.toString(), 2);
        } catch (fileSystemException ex)
        {
            out.print("(GUI) - owninitComponents : " + ex.toString(), 2);
        }
        setIconImage(image);

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
        AdminConfigPanel = new javax.swing.JPanel();
        AdminLoginPanel = new javax.swing.JPanel();
        AdminLoginSent = new javax.swing.JButton();
        AdminLoginLabel = new javax.swing.JLabel();
        AdminUsernameField = new javax.swing.JTextField();
        AdminPasswordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Verteiltes Filesystem");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTabbedPane5.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane5.setForeground(new java.awt.Color(255, 255, 255));
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

        AdminUsernameField.setText("Username");

        AdminPasswordField.setText("Password");

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

        javax.swing.GroupLayout AdminConfigPanelLayout = new javax.swing.GroupLayout(AdminConfigPanel);
        AdminConfigPanel.setLayout(AdminConfigPanelLayout);
        AdminConfigPanelLayout.setHorizontalGroup(
            AdminConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminConfigPanelLayout.createSequentialGroup()
                .addGap(339, 339, 339)
                .addComponent(AdminLoginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(559, Short.MAX_VALUE))
        );
        AdminConfigPanelLayout.setVerticalGroup(
            AdminConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminConfigPanelLayout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(AdminLoginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(365, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Admin", AdminConfigPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane5MouseClicked
        /*
         *  Performed at Tab change
         *
         *
         */
        if (!Config.isRootDir())
        {
            try
            {
                fileSystem fs = fileSystem.getInstance();
                fs.setnewFileSystem(Config.getCurrentIp(), Config.getRootDir());
            } catch (fileSystemException ex)
            {
                out.print("(GUI-Z227) lokales fileySystem konnte nicht eingelesen werden");
            }
            out.print("(GUI - TabChange) Lokales fileSystem wurde eingelesen");
        }

        try
        {
            /*check Network state*/
            if (network.Interfaces.inerfaceNetworkOnline() == true)
            {
                state.setGreen();
                statel.setText("Online");
            }
        } catch (UnknownHostException ex)
        {
            out.print("(GUI) - jTabbedPane5MouseClicked : " + ex.toString(), 2);
        }

        ActiveTabIndex = jTabbedPane5.getSelectedIndex();
        out.print("Panel: " + jTabbedPane5.getTitleAt(ActiveTabIndex) + " wurde geöffnet");

        switch (jTabbedPane5.getTitleAt(ActiveTabIndex))
        {
            case "Config":
                break;
            case "Explorer":
        {
            try {
                if (!Config.isRootDir() == true && network.Interfaces.inerfaceNetworkOnline() == true )
                {
                    explorer.addTab(jTabbedPane5, ActiveTabIndex);
                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
            case "Admin":
                if (admin != null)
                {
                    admin.refresh();
                }
                break;

        }

    }//GEN-LAST:event_jTabbedPane5MouseClicked

    private void AdminLoginSentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminLoginSentActionPerformed
        //Switcht between Login and Admin Panel

        if (Admin.Login(AdminUsernameField.getText(), AdminPasswordField.getText()))
        {
            admin = new Admin(AdminConfigPanel, AdminLoginPanel);
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
    private javax.swing.JPasswordField AdminPasswordField;
    private javax.swing.JTextField AdminUsernameField;
    private javax.swing.JTabbedPane jTabbedPane5;
    // End of variables declaration//GEN-END:variables

    /*
     *
     * Class to create the Online status Display
     *      
     */
    class KreisPanel extends JPanel
    {

        Graphics2D g2;
        Color color = Color.RED;

        @Override
        public void paint(Graphics g)
        {
            g2 = (Graphics2D) g;

            g2.setPaint(color);
            g2.fill(new Ellipse2D.Double(0, 0, 24, 24));

        }

        public void setRed()
        {
            this.color = Color.RED;
            repaint();
        }

        public void setGreen()
        {
            this.color = Color.GREEN;
            repaint();
        }
    }

}
