package Client;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * Created by andrii on 22.10.15.
 */
public class Client {

    private static final String SERVER_IP = "192.168.0.104";
    private static final int SERVER_PORT = 1488;
    private static Socket socket;
    private static PrintWriter commandLinePrintWriter;
    private static PrintWriter localPrintWriter = new PrintWriter(System.out, true);
    private static Scanner commandLineInputScanner;
    private static Scanner keyboardScanner = new Scanner(System.in);
    private static Thread outputTread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (socket.isConnected()) {
                String output = keyboardScanner.nextLine();
                commandLinePrintWriter.println(output);
            }
        }
    });
    private static Thread inputThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (socket.isConnected()) {
                String input = commandLineInputScanner.nextLine();
                localPrintWriter.println(input);
            }
        }
    });

    public static void startClient() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            commandLineInputScanner = new Scanner(socket.getInputStream());
            commandLinePrintWriter = new PrintWriter(socket.getOutputStream(), true);
            inputThread.start();
            outputTread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
