/**
 * Package
 */
package network;

/**
 * Imports
 */
import java.io.File;
import java.util.regex.Pattern;

/**
 * Klasse Rename
 * 
 * Diese Klasse dient zum Lokalen umbenennung des Dateinamens
 * - fügt dem Dateinamen ein "-neueDatei-" hinzu
 *   falls der gewünschte dateiname schon existiert
 * 
 * @author David Lampa
 * @version 1.0
 * 
 */
public class Rename
{
    /**
     * Funktion renameFile
     * 
     * Diese Funktion ändert den Dateinamen
     *
     *
     * @param path String // Pfad in dem sich die Datei befindet
     * @param oldName String // Originaler / Alter Name der Datei
     * @param newName String // Neuer Name der Datei
     * @return boolean // true = umbenennen war Erfolgreich
     */
    public static boolean renameFile(String path, String oldName, String newName)
    {
        /**
         * Initialisierung der Neuen und Originalen/Alten Datei
         */
        File fileOld = new File(path + oldName);
        File fileNew = new File(path + newName);
        
        /**
         * Falls die Originale/Alte Datei nicht Existiert gebe false zurück
         * Aktion kann nicht durchgeführt werden
         */
        if (!fileOld.exists())
        {
            return false;
        }
        
        /**
         * Solange eine datei mit diesem namen Existiert
         * wird der Dateiname um "-neueDatei-" erweitert
         */
        while (fileNew.exists())
        {
            /**
             * Splitten des neuen Dateinamens um vor der Datei Endung den
             * Dateinamen erweitern zu können
             * Hole Anzahl gesplitterter teile, falls Datei Endung aus mehreren .endungen besteht 
             */
            String[] sname = newName.split(Pattern.quote("."));
            int anz = sname.length;
            
            /**
             * Erweitern des Dateinamen
             */
            newName = sname[0] + "-neueDatei-";
            
            /**
             * Füge die Datei Endungen hinzu
             */
            for (int i = 1; i < anz; i++)
            {
                newName += "." + sname[i];
            }
            
            /**
             * Initialisiere die Neue Datei
             */
            fileNew = new File(path + newName);
        }
        
        /**
         * Bennene die Originale/Alte datei um
         * Gebe zurück, ob das umbenennen er Datei erfolgreich war
         */
        return fileOld.renameTo(fileNew);
    }
}
