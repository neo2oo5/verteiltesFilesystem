package network;

/**
 * Used Libraries
 */
import java.io.File;

/**
 *
 * @author Lamparari
 */
public class Delete
{

    /**
     * Class to delete a file in a chosen directory
     *
     * @param path
     * @param name
     * @return
     */
    public static boolean deleteFile(String path, String name)
    {
        /**
         * Search the selected file. If it was found, delete it!
         */
        File file = new File(path + name);

        boolean successful = file.delete();
        /**
         * return if the file was successfully deleted or not
         */
        return successful;
    }
}
