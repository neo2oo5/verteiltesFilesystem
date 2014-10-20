/**
 * Package
 */
package network;

/**
 * Imports
 */
import java.io.File;

/**
 * Klasse Delete
 * 
 * Klasse zum lokalen Löschen einer Datei
 * 
 * @author David Lampa
 * @version 1.0
 */
public class Delete
{

    /**
     * Funktion deleteFile
     * 
     * Diese Funktion dient zum lokalen löschen einer Datei
     * 
     * @param path // Pfad wo die Datei gelöscht werden soll
     * @param name // Name der zu löschenden Datei
     * @return boolean  // true = löschen erfolgreich
     */
    public static boolean deleteFile(String path, String name)
    {
        /**
         * Initialisierung der Datei
         */
        File file = new File(path + name);
        
        /**
         * Löschen der Datei und erfolgreich oder nicht in successful schreiben
         */
        boolean successful = file.delete();
        
        /**
         * Rückgabe ob erfolgreich gelöscht oder nicht
         */
        return successful;
    }
}
