/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikatorklientprojekt;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Projekt PW - Temat 2: Komunikator sieciowy
 * Wojciech Bałchanowski i Kacper Dutkiewicz
 * IJO1
 * Poniedziałek 9:45
 */
public class MultiThreadChatClient implements Runnable {
    
    // The input stream
    private static DataInputStream is;
    private static boolean closed;

    public MultiThreadChatClient(DataInputStream is, boolean closed) {
        this.is = is;
        this.closed = closed;
    }

    public void run() {
        //Czytaj dane z soketu az nie otrzymasz "*** Pa pa"
        //Wtedy wykonaj break;
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                System.out.println(responseLine);
                if (responseLine.indexOf("*** Pa pa") != -1) {
                    break;
                }
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}
