/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package substructure;

import fileSystem.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Gibt OS Name zurueck und gibt Pfad zu Dateien und Ordnern im System ordner OS formatiert zurueck
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class PathHelper {

    static GUIOutput out = GUIOutput.getInstance();
    private static String SysDir = System.getProperty("user.dir") + File.separator + "System" + File.separator;

    /**
     *
     * @return
     */
    public static String getOSName() {
        String os = System.getProperty("os.name");

        if (os.contains("Linux")) {
            return "Linux";
        } else if (os.contains("Mac")) {

            return "Mac";
        } else if (os.contains("Windows")) {
            return "Windows";
        }

        return null;
    }

    private static String setPath(String Node) {
        if (getOSName().equals("Linux")) {
            return SysDir + Node;
        } else if (getOSName().equals("Mac")) {

            return SysDir + Node;
        } else if (getOSName().equals("Windows")) {
            return SysDir + Node;
        }
        return null;
    }

    /**
     *
     * @param file
     * @return
     * @throws fileSystemException
     */
    public static String getFile(String file) throws fileSystemException {
        Path fPath;

        if (file.length() > 0) {
            fPath = Paths.get(setPath(file) + File.separator);
        } else {
            fPath = Paths.get(setPath(file));
        }

        if (Files.exists(fPath)) {
            return setPath(file);
        } else {
            throw new fileSystemException("File not Found. Path:" + file);
        }

    }

    /**
     *
     * @param folder
     * @return
     * @throws fileSystemException
     */
    public static String getFolder(String folder) throws fileSystemException {
        Path fPath;

        if (folder.length() > 0) {
            fPath = Paths.get(setPath(folder) + File.separator);
        } else {
            fPath = Paths.get(setPath(folder));
        }

        if (Files.exists(fPath)) {
            return setPath(folder) + File.separator;
        } else {
            throw new fileSystemException("File not Found. Path:" + folder);
        }
    }

}
