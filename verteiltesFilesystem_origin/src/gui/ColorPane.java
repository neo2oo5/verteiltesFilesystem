/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 * Extended JTextPane to colors
 */
import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class ColorPane extends JTextPane {

    /**
     *
     * @param c
     * @param s
     */
    public void appendNaive(Color c, String s) { // naive implementation
        // bad: instiantiates a new AttributeSet object on each call
        SimpleAttributeSet aset = new SimpleAttributeSet();
        StyleConstants.setForeground(aset, c);

        int len = getText().length();
        setCaretPosition(len); // place caret at the end (with no selection)
        setCharacterAttributes(aset, false);
        replaceSelection(s); // there is no selection, so inserts at caret
    }

    /**
     *
     * @param c
     * @param s
     */
    public void append(Color c, String s) { // better implementation--uses
        // StyleContext
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, c);

        int len = getDocument().getLength(); // same value as
        // getText().length();
        setCaretPosition(len); // place caret at the end (with no selection)
        setCharacterAttributes(aset, false);
        replaceSelection(s); // there is no selection, so inserts at caret
    }

}
