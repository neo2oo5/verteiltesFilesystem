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
public class Network
{
    public static void main(String[] args) throws InterruptedException
    {
        boolean startProgram = Interfaces.inerfaceStartProgram();
        int interfaceFileTransfer = Interfaces.interfaceFileTransfer("192.168.178.161", "C:/VerteilteSysteme/test/", "C:/VerteilteSysteme/test/VS/tester/", "ersterclient.zip");
        System.out.println("ttt");
    }
}