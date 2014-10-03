/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author David Lampa
 *
 */
public class FileTransfer
{

    /**
     * @param args
     */
    public static boolean FT(String[] args)
    {

        String datei = args[0] + args[2];
        File file = new File(datei);
        FileProvider fileProvider = new FileProvider(file, 1718);

        FileFetcher fileFetcher = new FileFetcher(args[3], 1718, args);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(fileProvider);
        executorService.execute(fileFetcher);

        executorService.shutdown();
        return true;
    }

    static class FileProvider implements Runnable
    {

        final File file;

        final int port;

        public FileProvider(File file, int port)
        {
            this.file = file;
            this.port = port;
        }

        public void run()
        {
            try
            {

                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(port));
                SocketChannel socketChannel = serverSocketChannel.accept();
                Socket socket = socketChannel.socket();

                FileInputStream fileInputStream = new FileInputStream(this.file);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(file.getName());
                dataOutputStream.writeLong(file.length());

                dataOutputStream.flush();

                FileChannel fileChannel = fileInputStream.getChannel();

                transfer(fileChannel, socketChannel, file.length(), 1024 * 1024 * 16, true, true);

                fileInputStream.close();

                socket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    static class FileFetcher implements Runnable
    {

        String serverName;

        final String[] args;

        int port;

        public FileFetcher(String serverName, int port, String[] args)
        {
            this.serverName = serverName;
            this.port = port;
            this.args = args;
        }

        public void run()
        {
            try
            {
                SocketChannel socketChannel = SocketChannel
                        .open(new InetSocketAddress(serverName, port));
                Socket socket = socketChannel.socket();

                DataInputStream dataInputStream = new DataInputStream(socket
                        .getInputStream());
                String fileName = dataInputStream.readUTF();
                long sizeInBytes = dataInputStream.readLong();

                File file = new File(args[1], fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                FileChannel fileChannel = fileOutputStream.getChannel();

                transfer(fileChannel, socketChannel, sizeInBytes,
                        1024 * 1024 * 16, true, false);

                fileOutputStream.close();
                socket.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void transfer(FileChannel fileChannel,
            SocketChannel socketChannel, long lengthInBytes,
            long chunckSizeInBytes, boolean verbose, boolean fromFileToSocket)
            throws IOException
    {

        long overallBytesTransfered = 0L;
        long time = -System.currentTimeMillis();
        while (overallBytesTransfered < lengthInBytes)
        {

            long bytesTransfered = 0L;

            if (fromFileToSocket)
            {
                bytesTransfered = fileChannel.transferTo(overallBytesTransfered, Math.min(
                        chunckSizeInBytes, lengthInBytes
                        - overallBytesTransfered), socketChannel);
            } else
            {
                bytesTransfered = fileChannel.transferFrom(socketChannel,
                        overallBytesTransfered, Math.min(chunckSizeInBytes,
                                lengthInBytes - overallBytesTransfered));
            }

            overallBytesTransfered += bytesTransfered;

            if (verbose)
            {
                System.out.printf(
                        "overall bytes transfered: %s progress %s%%\n",
                        overallBytesTransfered, Math
                        .round(overallBytesTransfered
                                / ((double) lengthInBytes) * 100.0));
            }

        }
        time += System.currentTimeMillis();

        if (verbose)
        {
            System.out.printf("Transfered: %s bytes in: %s s -> %s kbytes/s",
                    overallBytesTransfered, time / 1000,
                    (overallBytesTransfered / 1024.0) / (time / 1000.0));
        }
    }

}
