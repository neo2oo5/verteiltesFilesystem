/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import gui.Config;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;


/**
 *
 * @author Lamparari
 */
public class Network
{

    public static void main(String[] args) throws InterruptedException, IOException
    {

        StartClientServer.startServer();
        System.out.println("Start des tests");
        System.out.println("Ende des tests");
    }
}
