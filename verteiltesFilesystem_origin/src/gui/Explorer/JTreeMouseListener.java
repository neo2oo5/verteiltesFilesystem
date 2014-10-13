/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Explorer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class JTreeMouseListener implements MouseListener {
    
    //open Popupmenu
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getButton() == 3)
        {
            showMenu(e);
        }
        
    }

    /**
     * Create Rightclick Menu
     * @param e
     */
    public void showMenu(MouseEvent e)
    {
      new RightClickMenu(e);
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
