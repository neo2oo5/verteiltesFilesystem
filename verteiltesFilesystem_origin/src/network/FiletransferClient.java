/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;
    import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import substructure.PathHelper;

/**
 *
 * @author Lamparari
 */
public class FiletransferClient
{

  public static void FileTransferClient(String[] args) throws Exception {
    Socket sock = new Socket(args[0], 123456);
    byte[] mybytearray = new byte[1024];
    InputStream is = sock.getInputStream();
    String outputdatei = substructure.PathHelper.getFile(args[1]);
    FileOutputStream fos = new FileOutputStream(args[1]);
    BufferedOutputStream bos = new BufferedOutputStream(fos);
    int bytesRead = is.read(mybytearray, 0, mybytearray.length);
    bos.write(mybytearray, 0, bytesRead);
    bos.close();
    sock.close();
  }
}

