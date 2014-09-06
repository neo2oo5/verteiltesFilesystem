/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.io.File;

/**
 *
 * @author Cyox
 */
public class EigehendOberflaeche
{
    /**
     * schreibschutz oder verwendung schuetzen vor loeschen
     * nur leere Verzeichnisee koennen geloescht werden deshalb zweite if Abfrage
     * sollte ein Fehler auftreten liefert delete() false zurueck
     * @param dir 
     */
    public void deleteDir(File dir)
    {
        File[] files = dir.listFiles();
        if (files != null)
        {
            for(int i = 0; i < files.length; i++)
            {
                if(files[i].isDirectory())
                {
                    deleteDir(files[i]);//Verzeichnis leeren und dann löschen
                }
                else
                {
                    files[i].delete(); //Datei löschen
                }
            }
            dir.delete(); //Ordner löschen
        }
    }
}
