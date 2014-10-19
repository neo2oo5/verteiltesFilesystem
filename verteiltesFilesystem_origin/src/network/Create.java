package network;

/**
 * Used Libraries
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import substructure.GUIOutput;

/**
 * Class to create a File on a path wich the user can select by himself *
 */
public class Create
{

    static GUIOutput out = GUIOutput.getInstance();

    /**
     *
     * @param path
     * @param name
     * @return
     */
    public static boolean createFile(String path, String name)
    {
        /**
         * Creates the new File at the selected path
         */
        File dir = new File(path);
        dir.mkdirs();
        File file = new File(path + name);
        try
        {
            /**
             * creates a File - if the file already exists, overwrite it
             */
            while (file.exists())
            {
                String[] sname = name.split(Pattern.quote("."));
                /**
                 * splits the name at every "."
                 */
                int anz = sname.length;
                /**
                 * get the length of the string
                 */
                name = sname[0] + "-neueDatei-";
                /**
                 * rename the file *
                 */
                for (int i = 1; i < anz; i++)
                {
                    name += "." + sname[i];
                }
                file = new File(path + name);
            }
            FileWriter writer = new FileWriter(file, false);
        } catch (IOException ex)
        {
            out.print("(Create - createFile) : " + ex.toString(), 2);
            return false;
        }
        /**
         * return if the file was created successfully
         */
        return true;
    }
}
