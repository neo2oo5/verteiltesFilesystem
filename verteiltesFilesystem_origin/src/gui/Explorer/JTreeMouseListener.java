package gui.Explorer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Fängt den Rechtsklick im Explorer ab um das Popup erstellen zu können
 *
 * @author Kevin Bonner  - kevin.bonner@gmx.de
 */
public class JTreeMouseListener implements MouseListener
{

    //open Popupmenu
    @Override
    public void mouseClicked(MouseEvent e)
    {

        if (e.getButton() == 3)
        {
            showMenu(e);
        }

    }

    /**
     * Erstellt das Rechtsklick Menü
     *
     * @param e
     */
    public void showMenu(MouseEvent e)
    {
        new RightClickMenu(e);

    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }
}
