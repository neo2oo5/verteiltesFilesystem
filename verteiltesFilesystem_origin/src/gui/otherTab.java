package gui;

import fileSystem.fileSystemException;
import java.io.*;
import javax.swing.JTextArea;
import substructure.GUIOutput;

/**
 * Klasse zu Erstellung der Tabs Über Uns und Info
 * Liest die entsprechenden Dateien aus dem Sytem Ordner
 * @author Kevin Bonner  - kevin.bonner@gmx.de
 */
public class otherTab
{

    private JTextArea aboutus;
    private JTextArea info;
    static GUIOutput out = GUIOutput.getInstance();

    /**
     * Added die TextAreas zu den Tabs
     * @param TabbedPane
     */
    public otherTab(javax.swing.JTabbedPane TabbedPane)
    {
        aboutus = new JTextArea(loadTabContent("ueberuns.txt"));
        aboutus.setEditable(false);

        info = new JTextArea(loadTabContent("info.txt"));
        info.setEditable(false);

        TabbedPane.addTab("Über Uns", aboutus);
        TabbedPane.addTab("Info", info);
    }

    private String loadTabContent(String file)
    {
        String content = "";
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(substructure.PathHelper.getFile(file)));
            String line = null;
            while ((line = br.readLine()) != null)
            {
                content += line + "\n";
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (fileSystemException ex)
        {
            out.print(ex.toString());
        } finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return content;

    }
}
