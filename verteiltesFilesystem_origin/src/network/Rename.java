package network;

/**
 * Used Libraries
 */
import java.io.File;
import java.util.regex.Pattern;

/**
 *
 * @author Lamparari
 */
public class Rename
{

    /**
     *
     * @param path
     * @param oldName
     * @param newName
     * @return
     */

    /**
     * class to rename an existing file
     */
    public static boolean renameFile(String path, String oldName, String newName)
    {
        /**
         * First, get the name of the old file(or directory) wich you want to
         * rename. Then choose the new name
         */
        File fileOld = new File(path + oldName);
        File fileNew = new File(path + newName);
        /**
         * return false if the file not exists wich should be renamed
         */
        if (!fileOld.exists())
        {
            return false;
        }
        /**
         * if the file exists, it gets a new name now
         */
        while (fileNew.exists())
        {
            String[] sname = newName.split(Pattern.quote("."));
            /**
             * splits the name at every "."
             */
            int anz = sname.length;
            /**
             * get the length of the string
             */
            newName = sname[0] + "-neueDatei-";
            /**
             * rename the file *
             */
            for (int i = 1; i < anz; i++)
            {
                newName += "." + sname[i];
            }
            fileNew = new File(path + newName);
        }
        /**
         * return true if the file has get a new name
         */
        boolean success = fileOld.renameTo(fileNew);
        return success;
    }
}
