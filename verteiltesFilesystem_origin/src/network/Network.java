/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Spliterator;

/**
 *
 * @author Lamparari
 */
public class Network
{

    public static void main(String[] args) throws InterruptedException, IOException
    {
        
        ArrayList<String> IPListe = new ArrayList<String>();
        IPListe = getIPv4Address.getIPv4Address();
        
        System.out.println("Liste: " + IPListe.toString());
        
        System.out.println("0: " + IPListe.get(0).toString());
        System.out.println("0: " + IPListe.get(1).toString());
        
        Spliterator<String> spliterator = IPListe.spliterator();
        System.out.println("1: " + spliterator.toString());
    }
}
