/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package network;

/**
 *
 * @author Lamparari
 */

/** main class wich starts the network and communication */
public class Network
{
    public static void main(String[] args)
    {   
        /** start the programm */
        boolean startProgram = Interfaces.inerfaceStartProgram();
        /** connections to a "special" client */
        int interfaceFileCreate = Interfaces.interfaceFileCreate("192.168.178.33", "C:/VerteilteSysteme/test/", "xyz.txt");
       // int interfaceFileRename = Interfaces.interfaceFileRename("192.168.178.161", "C:/VerteilteSysteme/test/", "xyz.txt", "abc.txt");
        //int interfaceFileDelete = Interfaces.interfaceFileDelete("192.168.178.161", "C:/VerteilteSysteme/test/", "abc.txt");
    }
}
