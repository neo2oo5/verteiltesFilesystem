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
     *
     * @param path
     * @param name
     * @return
     */
    /**
     * Class to delete a file in a chosen directory
     */
    public static boolean deleteFile(String path, String name)
    {
        /**
         * Search the selected file. If it was found, delete it!
         */
        File file = new File(path + name);

        boolean successful = file.delete();
        /**
         * return if the file was successfully deleted
         */
        return successful;
    }
}
