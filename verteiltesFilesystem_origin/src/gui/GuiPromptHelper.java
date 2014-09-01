/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;
import javax.swing.*;



/**
 *
 * @author Kevin Bonner
 */
public class GuiPromptHelper
{
  
    final public static int showInformation     = 1;
    final public static int showWarning         = 2;
    final public static int showError           = 3;
    final public static int showQuestion        = 4;
    final public static int showInput        = 5;
    private String input="";
    
    public GuiPromptHelper() {
         //default constructor
     }
    
    public GuiPromptHelper(int mode, String msg) {
        
         switch(mode)
        {
            case 0: 
                    break;
            case 1: showInformation(msg);
                    break;
            case 2: showWarning(msg);
                    break;
            case 3: showError(msg);
                    break;
            case 4: showQuestion(msg);
                    break;
            case 5: showInput(msg);
                    break;

        }
        
    }
    public void show(int mode, String msg) {
        
         switch(mode)
        {
            case 0: 
                    break;
            case 1: showInformation(msg);
                    break;
            case 2: showWarning(msg);
                    break;
            case 3: showError(msg);
                    break;
            case 4: showQuestion(msg);
                    break;
            case 5: showInput(msg);
                    break;

        }
       
    }
     private void showInformation(String msg)
    {
        JOptionPane.showMessageDialog(null, msg);
    }
    
    private void showWarning(String msg)
    {
        JOptionPane.showMessageDialog(null, msg, "Inane warning", JOptionPane.WARNING_MESSAGE);
    }
    
    private void showError(String msg)
    {
        JOptionPane.showMessageDialog(null, msg, "Inane error", JOptionPane.ERROR_MESSAGE);
    }
    
   private int showQuestion(String msg)
   {
        return showQuestion(msg, null);
   }
   
   private int showQuestion(String msg, String title)
   {
        Object[] options = {"Yes, please", "No, thanks"};
      
        int n = JOptionPane.showOptionDialog(null, msg, title,
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null, options, options[0]);
    
        return n;
   }
   
   private void showInput(String msg)
   {
       input = JOptionPane.showInputDialog(null, msg);
   }
   
   @Override
   public String toString()
   {
       return input;
   }
}

           
     
