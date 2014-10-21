package substructure;

import fileSystem.fileSystemException;
import gui.GuiPromptHelper;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ein wenig Farbe fuer den Terminal
 *
 * @author Kevin Bonner  - kevin.bonner@gmx.de
 */
public class Output
{

    /**
     *
     */
    protected String Logfile = "log.txt";

    /**
     *
     */
    protected PrintStream orig = System.out;

    /**
     *
     */
    protected static final String ANSI_BLACK = "\u001B[30m";

    /**
     *
     */
    protected static final String ANSI_WHITE = "\u001B[37m";

    /**
     *
     */
    protected static final String ANSI_RED = "\u001B[31m";

    /**
     *
     */
    protected static final String ANSI_GREEN = "\u001B[32m";

    /**
     *
     */
    protected static final String ANSI_YELLOW = "\u001B[33m";

    /**
     *
     */
    public Output()
    {

    }

    /**
     *
     * @param msg
     */
    public Output(Object msg)
    {
        setLogfile();
        print(msg);

        setStdout();
        print(msg);
    }

    /**
     *
     * @param msg
     * @param modelevel
     */
    public Output(Object msg, int modelevel)
    {
        setLogfile();
        print(msg, modelevel);

        setStdout();
        print(msg, modelevel);
    }

    /**
     *
     */
    protected void setStdout()
    {
        System.setOut(orig);
    }

    /**
     *
     */
    protected void setLogfile()
    {
        try
        {
            System.setOut(new PrintStream(substructure.PathHelper.getFile(Logfile)));
        } catch (Exception ex)
        {
            new GuiPromptHelper(GuiPromptHelper.showError, ex.toString());
        }
    }

    /**
     *
     * @param msg
     */
    public void print(Object msg)
    {
        print(msg, 1);
    }

    /**
     *
     * @param msg
     * @param modelevel
     */
    public void print(Object msg, int modelevel)
    {
        int debug = 0;
        Properties prop = new Properties();
        InputStream input = null;

        /*Liest aus Datei den ModeLevel
         * Level: 
         * 0) None
         * 1) Information (Default)
         * 2) Warning
         * 3) Error
         */
        try
        {

            try
            {
                // load a properties file
                prop.load(new FileReader(substructure.PathHelper.getFile("debug.properties")));
            } catch (fileSystemException ex)
            {
                Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
            }

            // get the property value and print it out
            try
            {
                debug = Integer.parseInt(prop.getProperty("MODE"));
            } catch (Exception e)
            {
                new GuiPromptHelper(GuiPromptHelper.showWarning, "Output: Modelevel konnte nicht gelesen werden " + e);
            }

        } catch (FileNotFoundException e)
        {
            new GuiPromptHelper(GuiPromptHelper.showWarning, "Output: Debug File konnte nicht geladen werden " + e);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }

        out(msg, modelevel, debug);

    }

    /**
     *
     * @param msg
     * @param mode
     * @param debug
     */
    public void out(Object msg, int mode, int debug)
    {
        String print = "";

        if (debug >= 3 && mode == 3)
        {
            print = ANSI_RED + "ERROR: ";
        } else if (debug >= 2 && mode == 2)
        {
            print = ANSI_YELLOW + "WARNING: ";
        } else if (debug >= 1 && mode == 1)
        {
            print = ANSI_GREEN + "INFORMATION: ";
        }

        if (mode != 0)
        {
            System.out.println(print + msg);
        }
    }

}
