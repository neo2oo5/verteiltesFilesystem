/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import static java.lang.Thread.sleep;

/**
 *
 * @author Lamparari
 */
public class Network
{

    public static void main(String[] args) throws InterruptedException, IOException
    {
        System.out.println("prg start" + getIPv4Address.getIPv4Address());
        CheckWhoIsOnline test = new CheckWhoIsOnline(getIPv4Address.getIPv4Address());
//boolean startProgram = Interfaces.inerfaceStartProgram();
        System.out.println("prg start");
        sleep(20000);
        System.out.println("sleep over - kick now");
        Interfaces.inerfaceNetworkOnline();
        System.out.println("fertig");
    }
}
