/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikatorklientprojekt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Bucior
 */
public class KomunikatorKlientProjekt {

    // The client socket
    private static Socket clientSocket = null;
    // The output stream
    private static PrintStream os = null;
    // The input stream
    private static DataInputStream is = null;

    private static BufferedReader inputLine = null;
    private static boolean closed = false;

    public static void main(String[] args) {

        // The default port.
        int portNumber = 2222;
        // The default host.
        String host = "localhost";

         //Otworz soket na okreslonym porcie i zaiinicjalizuj streamy
        try {
            clientSocket = new Socket(host, portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Nieznany host: " + host);
        } catch (IOException e) {
            System.err.println("Nie mozna polaczyc sie z host: "
                    + host);
        }

        //Jezeli wszystko zostalo zainicjalizowane poprawnie zacznij dzialac
        if (clientSocket != null && os != null && is != null) {
            try {

                //Stwórz wątek aby czytac z serwera
                new Thread(new MultiThreadChatClient(is, closed)).start();
                while (!closed) {
                    os.println(inputLine.readLine().trim());
                }
                //Zamknij wszystko
                os.close();
                is.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }

}
