package Client;

import java.net.*;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by andrii on 22.10.15.
 */
public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 1488;
    private static Socket socket;
    private static PrintWriter commandLinePrintWriter;
    private static PrintWriter localPrintWriter = new PrintWriter(System.out, true);
    private static Scanner commandLineInputScanner;
    private static Scanner keyboardScanner = new Scanner(System.in);
    private static File destinationDirectory = new File("downloads");
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
                try {
                    String input = commandLineInputScanner.nextLine();
                    localPrintWriter.println(input);
                    if (input.equals("Copying...")) {
                        readFile();
                    }
                } catch (Exception e) {
                    inputThread.stop();
                }
            }
        }
    });

    public static void startClient() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            commandLineInputScanner = new Scanner(socket.getInputStream());
            commandLinePrintWriter = new PrintWriter(socket.getOutputStream(), true);
            if (!destinationDirectory.canRead()) {
                destinationDirectory.mkdir();
            }
            inputThread.start();
            outputTread.start();
        } catch (IOException e) {
            localPrintWriter.println("Server offline.");
        }

    }

    private static void readFile() {
        try {
            Socket socket = new Socket(SERVER_IP, 21212);
            String fileName = new DataInputStream(socket.getInputStream()).readUTF();
            String directoryName = new DataInputStream(socket.getInputStream()).readUTF();
            if (directoryName.equals("")) {
                Files.copy(socket.getInputStream(), new File(destinationDirectory.getName() + "/" + fileName).toPath());
            } else {
                if (!new File(destinationDirectory.getName() + "/" + directoryName).canRead()) {
                    new File(destinationDirectory.getName() + "/" + directoryName).mkdir();
                }
                Files.copy(socket.getInputStream(), new File(destinationDirectory.getName() + "/" + directoryName + "/" + fileName).toPath());
            }
            socket.close();
        } catch (FileAlreadyExistsException e) {
            localPrintWriter.println("File already exist.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
