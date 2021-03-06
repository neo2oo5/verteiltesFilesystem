package gui;

import fileSystem.fileSystem;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import substructure.GUIOutput;
import javax.swing.*;
import network.IPList;
import network.Interfaces;

/**
 * Klasse zur Verwaltung der Netzwerkteilnehmer
 * 
 * @author Kevin Bonner  - kevin.bonner@gmx.de
 */
public class AdminControlPanel extends javax.swing.JPanel
{

    private static GUIOutput out = GUIOutput.getInstance();
    private static fileSystem c = fileSystem.getInstance();
    private static JLabel[] usersL = new JLabel[100];
    private static JButton[] usersB = new JButton[100];
    private static String logoutCMD = "logout";
    private static JPanel adminControlPanel, adminloginPanel;

    /**
     * Erstellt AdminConfigPanel
     *
     * @param panel
     */
    public AdminControlPanel(JPanel panel)
    {

        adminControlPanel = this;
        adminloginPanel = panel;

        adminloginPanel.setVisible(false);
        initComponents();

        createUserlist();

        logout.setActionCommand(logoutCMD);
        logout.addActionListener(new AdminControlListener());

        setVisible(true);
        setSize(new Dimension(500, 500));
        revalidate();
        repaint();

    }

    /**
     * Aktualisiert die Netzwerk Liste
     */
    public void refreshUserlist()
    {
        createUserlist();

        revalidate();
        repaint();

    }
    /*
     *   Erstellt die Buttons und Labels auf dem UserPanel
     *   
     */

    private void createUserlist()
    {
        List<String> ips = IPList.getIPList();

        int x = 0;
        int y = 5;
        int width = 200;
        int height = 20;
        for (int i = 0; i < ips.size(); i++)
        {
            String ip = ips.get(i);

            usersL[i] = new JLabel();
            usersL[i].setText("user" + i + ": " + ip);
            usersL[i].setBounds(x, y += 20, width, height);
            usersL[i].setVisible(true);
            userlist.add(usersL[i]);

            usersB[i] = new JButton();
            usersB[i].setText("Aus Netzwerk entfernen");
            usersB[i].setBounds(x, y += 20, width, height);
            usersB[i].setActionCommand(ip);
            usersB[i].addActionListener(new AdminControlListener());
            usersB[i].setVisible(true);
            userlist.add(usersB[i]);

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        logout = new javax.swing.JButton();
        userlist = new javax.swing.JPanel();

        jLabel1.setText("Willkommen");

        logout.setText("Abmelden");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout userlistLayout = new javax.swing.GroupLayout(userlist);
        userlist.setLayout(userlistLayout);
        userlistLayout.setHorizontalGroup(
            userlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        userlistLayout.setVerticalGroup(
            userlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(logout)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(500, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(userlist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logout))
                .addGap(18, 18, 18)
                .addComponent(userlist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton logout;
    private static javax.swing.JPanel userlist;
    // End of variables declaration//GEN-END:variables

    /**
     * ActionListener
     */
    private static class AdminControlListener implements ActionListener
    {

        public AdminControlListener()
        {
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            String cmd = e.getActionCommand();

            List<String> ips = IPList.getIPList();

            for (int i = 0; i < ips.size(); i++)
            {
                String ip = ips.get(i);
                if (cmd.equals(Config.getCurrentIp()))
                {
                    new GuiPromptHelper(GuiPromptHelper.showError, "Sie können ihren eigenen Client nicht aus dem Netzwerk entfernen!");
                } else
                {
                    if (cmd.equals(ip))
                    {
                        Interfaces.interfaceAdminKickUser(ip);
                        new GuiPromptHelper(GuiPromptHelper.showInformation, "Client mit der IP: " + ip + " wurde aus dem Netzwerk entfernt");
                        userlist.remove(usersL[i]);
                        userlist.remove(usersB[i]);

                    }
                }

            }

            if (logoutCMD.equals(cmd))
            {
                try
                {
                    Interfaces.interfaceAdminLogout();
                    AdminControlPanel.adminControlPanel.setVisible(false);
                    AdminControlPanel.adminloginPanel.setVisible(true);
                } catch (UnknownHostException ex)
                {
                    Logger.getLogger(AdminControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            AdminControlPanel.userlist.revalidate();
            AdminControlPanel.userlist.repaint();
        }
    }
}
