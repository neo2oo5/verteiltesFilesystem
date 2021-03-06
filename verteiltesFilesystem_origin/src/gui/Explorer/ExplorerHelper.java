package gui.Explorer;

import fileSystem.fileSystem;
import fileSystem.fileSystemException;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import network.PingServer;
import substructure.GUIOutput;
import substructure.PathHelper;

/**
 * Enthält helfer Funktionen für den Explorer
 * @author Kevin Bonner  - kevin.bonner@gmx.de
 */
public class ExplorerHelper
{

    private static String downloadFolder = "Downloads";
    private static GUIOutput out = GUIOutput.getInstance();
    private static fileSystem c = fileSystem.getInstance();

    /**
     * Wandelt Node um in Vollstaendigen Pfad
     *
     * @param path
     * @return
     */
    public static String getPath(DefaultMutableTreeNode path)
    {
        String jTreeVarSelectedPath = "";
        Object[] paths = path.getPath();
        for (int i = 1; i < paths.length; i++)
        {
            jTreeVarSelectedPath += paths[i];
            if (i + 1 < paths.length)
            {
                jTreeVarSelectedPath += File.separator;
            }
        }

        return jTreeVarSelectedPath;
    }

    /**
     * Ermittelt aus Pfad IP, sourcePath, Filename fuer Netzwerkoperationen
     *
     * @param currentNode
     * @return
     */
    public static String[] getNetOperationData(DefaultMutableTreeNode currentNode)
    {
        try
        {
            return getNetOperationData(currentNode, substructure.PathHelper.getFolder(downloadFolder));
        } catch (fileSystemException ex)
        {
            out.print(ex.toString());
        }
        return null;
    }

    /**
     * Ermittelt aus Pfad IP, sourcePath, Filename fuer Netzwerkoperationen
     *
     * @param currentNode
     * @param targetPath
     * @return
     */
    public static String[] getNetOperationData(DefaultMutableTreeNode currentNode, String targetPath)
    {
        String path = getPath(currentNode);
        String result[] = new String[5];

        result[0] = path.substring(0, path.indexOf(File.separator)); //IP
        if (PingServer.PingServer(result[0]))
        {
            List<Path> fs = c.get(result[0]);
            result[1] = path.substring(path.lastIndexOf(File.separator) + 1, path.length()); //filename

            result[3] = targetPath; //targetPath

            for (Path pathfs : fs)
            {

                if (pathfs.toString().contains(result[1]))
                {
                    System.out.print("Path befor: " + pathfs.toString() + "\n");

                    result[2] = convertPath(pathfs.toString(), true);

                    System.out.print("Path after convert: " + result[2] + "\n");

                    //linux
                    int index = result[2].lastIndexOf("/");
                    String os = "linux";

                    //try if win
                    if (index == -1)
                    {
                        index = result[2].lastIndexOf("\\");
                        os = "win";
                    }

                    if (index != -1)
                    {
                        if (os.equals("linux"))
                        {
                            result[2] = result[2].substring(0, index) + "/";
                        } else if (os.equals("win"))
                        {
                            result[2] = result[2].substring(0, index) + "\\";
                        }
                    }

                    System.out.print("Path: " + result[2] + "\n");

                }
            }
            return result;
        }

        return null;

    }

    /**
     * Wandelt Pfade entsprechend dem OS um Windows, Linux, Mac
     * @param path
     * @return string  
     */
    public static String convertPath(String path)
    {
        return convertPath(path, false);
    }

    /**
     * Wandelt Pfade entsprechend dem OS um Windows, Linux, Mac
     *
     * @param path
     * @param forDownload
     * @return string
     */
    public static String convertPath(String path, boolean forDownload)
    {
        String tmps = "";

        if (path.length() > 0)
        {
            //windows pfade anpassen zu
            if (path.contains(":"))
            {
                if (forDownload)
                {
                    //fuer download
                    tmps = path.replace("/", "\\");

                } else
                {
                    //fuer jtree
                    if (PathHelper.getOSName() == "Linux" || PathHelper.getOSName() == "Mac")
                    {
                        tmps = path.replace("\\", "/");
                    } else
                    {
                        tmps = path.replace("/", "\\");
                    }
                }

            } //linux pfade anpassen zu
            else
            {
                if (forDownload)
                {
                    //fuer download
                    tmps = path.replace("\\", "/");

                } else
                {
                    if (PathHelper.getOSName() == "Windows")
                    {
                        path = path.substring(1, path.length());
                        tmps = path.replace("/", "\\");
                    } else
                    {
                        path = path.substring(1, path.length());
                        tmps = path.replace("\\", "/");
                    }
                }
            }

            return tmps;

        }

        return "";
    }
}
