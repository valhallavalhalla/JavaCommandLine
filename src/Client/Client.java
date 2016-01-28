package Client;

import java.net.*;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Created by andrii on 22.10.15.
 */
public class Client {

    private static ServerSocket serverSocket;
    private static Socket commandLineSocket;
    private static PrintWriter commandLinePrintWriter;
    private static PrintWriter localPrintWriter = new PrintWriter(System.out, true);
    private static Scanner commandLineInputScanner;
    private static Scanner keyboardScanner = new Scanner(System.in);
    private static final String SYSTEM_WAY_SEPARATOR = System.getProperty("file.separator");
    private static File destinationDirectory = new File("downloads");
    private static Thread outputTread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (commandLineSocket.isConnected()) {
                String output = keyboardScanner.nextLine();
                commandLinePrintWriter.println(output);
            }
        }
    });
    private static Thread inputThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (commandLineSocket.isConnected()) {
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
            //Подключение к командной строке
            serverSocket = new ServerSocket(7442);
            commandLineSocket = serverSocket.accept();
            commandLineInputScanner = new Scanner(commandLineSocket.getInputStream());
            commandLinePrintWriter = new PrintWriter(commandLineSocket.getOutputStream(), true);
            //Создание папки в которую будут копироваться файлы
            if (!destinationDirectory.canRead()) {
                destinationDirectory.mkdir();
            }
            //Запуск потока для чтения результата выполнения команд
            inputThread.start();
            //Запуск потока для отправки команд
            outputTread.start();
        } catch (IOException e) {
            localPrintWriter.println("Server offline.");
        }

    }

    //Метод для копирования отдельных файлов, папок, или же для копирования древа директорий и файлов
    private static void readFile() {
        try {
            //Открытие нового сокета для копирования файла
            Socket downloadsSocket = serverSocket.accept();
            //Имя передаваемого файла
            String fileName = new DataInputStream(downloadsSocket.getInputStream()).readUTF();
            //Имя папки в которой лежит файл
            String directoryName = new DataInputStream(downloadsSocket.getInputStream()).readUTF();
            //Проверка на то, является ли передаваемый файл папкой
            boolean isDirectory = new DataInputStream(downloadsSocket.getInputStream()).readBoolean();

            if (isDirectory) {
                new File(destinationDirectory.getName() + SYSTEM_WAY_SEPARATOR + directoryName
                        + SYSTEM_WAY_SEPARATOR + fileName).mkdir();
            } else if (directoryName.equals("")) { //
                Files.copy(downloadsSocket.getInputStream(), new File(destinationDirectory.getName()
                        + SYSTEM_WAY_SEPARATOR + fileName).toPath());
            } else { //
                Files.copy(downloadsSocket.getInputStream(), new File(destinationDirectory.getName()
                        + SYSTEM_WAY_SEPARATOR + directoryName + SYSTEM_WAY_SEPARATOR
                        + fileName).toPath());
            }
            downloadsSocket.close();
        } catch (FileAlreadyExistsException e) {
            localPrintWriter.println("File already exist.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
