/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 *
 * @author Cyox
 */
public class AusLagerungIntern
{   
    /**
     * 
     * @return 
     */
   public String getAbsolutePfad()
    {
      File file = new File("E:/workspace/Project_SQL_14/test.txt");
      String adresse = file.getAbsolutePath();
      return adresse;
    }
 
    /**
     *
     * @return
     */
    public String getFileNameWithoutExtension()
   {
      File file = new File("E:/workspace/Project_SQL_14/test.txt");
      String fileName = file.getName();
      String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
      return fileNameWithoutExtension;
   }
   
    /**
     *
     * @return
     */
    public String getFileExtension()
   {
       File file = new File("E:/workspace/Project_SQL_14/test.txt");
       String fileName = file.getName();
       String extension = "";
       int i = fileName.lastIndexOf('.');
       if(i>0)
       {
          extension = fileName.substring(i+1);
       }
       return extension;
   }
   
    /**
     *
     * @return
     */
    public String lastModified()
   {
       File file = new File("E:/workspace/Project_SQL_14/test.txt");
       SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
       String timeStamp = sdf.format(file.lastModified());
       return timeStamp;
   }
   
   /**
    * 0 plan bis jetzt warum dass nicht funzt -.-
    * @return 
    */
   public long getFileSize()
   {
       long size = 0;
       File file = new File("E:/workspace/Project_SQL_14/1.docx");
       size = file.length();
       System.out.println(file.length());
       return size;
   }
   
}
