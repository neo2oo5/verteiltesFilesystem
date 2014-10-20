/**
 * Package
 */
package network;

/**
 * Imports
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import substructure.GUIOutput;
import java.util.regex.Pattern;

/**
 * Klasse Create
 * 
 * Klasse zum lokalen erstellen einer Datei
 * - fügt dem Dateinamen ein "-neueDatei-" hinzu
 *   falls der gewünschte dateiname schon existiert
 * 
 * @author David Lampa
 * @version 1.0
 */
public class Create
{
    /**
     * Variablen Initialisieren
     */
    static GUIOutput outMsg = GUIOutput.getInstance();

    /**
     * Funktion createFile
     * 
     * Diese Funktion dient zum lokalen erstellen einer Datei
     * - fügt dem Dateinamen ein "-neueDatei-" hinzu
     *   falls der gewünschte dateiname schon existiert
     * 
     * @param path // Pfad wo die Datei erstellt werden soll
     * @param name // Name der zu erstellenden Datei
     * @return boolean  // true = erstellen erfolgreich
     */
    public static boolean createFile(String path, String name)            
    {
        /**
         * Initialisierung der Datei
         * - Erstellung des Pfades falls dieser nicht vorhanden ist
         */
        File dir = new File(path);
        dir.mkdirs();
        File file = new File(path + name);
        try
        {
            /**
             * Solange eine datei mit diesem namen Existiert
             * wird der Dateiname um "-neueDatei-" erweitert
             */
            while (file.exists())
            {
                /**
                 * Splitten des Dateinamens um vor der Datei Endung den
                 * Dateinamen erweitern zu können
                 * Hole Anzahl gesplitterter teile, falls Datei Endung aus mehreren .endungen besteht 
                 */
                String[] sname = name.split(Pattern.quote("."));
                int anz = sname.length;
                
                /**
                 * Erweitern des Dateinamen
                 */
                name = sname[0] + "-neueDatei-";
                
                /**
                 * Füge die Datei Endungen hinzu
                 */
                for (int i = 1; i < anz; i++)
                {
                    name += "." + sname[i];
                }
                
                /**
                 * Initialisiere die Datei
                 */
                file = new File(path + name);
            }
            
            /**
             * Erstelle die Datei
             */
            FileWriter writer = new FileWriter(file, false);
        } catch (IOException ex)
        {
            /**
             * Fehler abfangen, ausgeben und fehlgeschlagen zurückgeben
             */
            outMsg.print("(Create - createFile) : " + ex.toString(), 2);
            return false;
        }
        /**
         * Gebe zurück, ob das erstellen der Datei erfolgreich war
         */
        return true;
    }
}
