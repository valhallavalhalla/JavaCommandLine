package CommandLine;

import Commands.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by andrii on 16.10.15.
 */

public class CommandLine {

    private static final String WINDOWS_WAY_SEPARATOR = "\\";
    private static final String LINUX_WAY_SEPARATOR = "/";
    private static String systemWaySeparator;
    private static String startedDirectory;
    private static Scanner scanner = new Scanner(System.in);
    private static PrintWriter printWriter;
    private static String[] command;
    private static String currentDirectory;
    private static final String CLIENT_IP = "176.104.59.7";
    private static final int CLIENT_PORT = 7442;
    private static final int UPLOAD_PORT = 21212;

    public static void setCurrentDirectory(String currentDirectory) {
        CommandLine.currentDirectory = currentDirectory;
    }

    public static String getStartedDirectory() {
        return startedDirectory;
    }

    public static String getCurrentDirectory() {
        return currentDirectory;
    }

    public static String getSystemWaySeparator() {
        return systemWaySeparator;
    }

    public static PrintWriter getPrintWriter() {
        return printWriter;
    }

    public static String getClientIp() {
        return CLIENT_IP;
    }

    public static int getClientPort() {
        return CLIENT_PORT;
    }

    public static int getUploadPort() {
        return UPLOAD_PORT;
    }

    private static void readAndRunCommand() throws IOException {
        currentDirectory = startedDirectory;
        while (true) {
            printWriter.print(currentDirectory + " ->");
            command = scanner.nextLine().split(" ");
            recognizeCommand(command);
        }
    }

    private static void recognizeCommand(String[] command) throws IOException {
        switch (command[0]) {
            case "cd":
                if (command.length > 1) {
                    CD.changeDirectory(command);
                }
                break;
            case "dir":
                Dir.directory(currentDirectory);
                break;
            case "pwd":
                PWD.printWayDirectory();
                break;
            case "md":
                MakeDirectory.makeDirectory(command);
                break;
            case "copy":
                Copy.copy(command, currentDirectory);
                break;
            case "run":
                Run.executeCommand(command);
                break;
            case "help":
                Help.printHelp();
                break;
            case "exit":
                Exit.exitCommandLine();
                break;
            case "":
                break;
            default:
                printWarning();
                break;
        }
    }

    private static void printGreetings() {
        System.out.println("Java Command Line. Created by Valhalla.");
        System.out.println("Type 'help' to get command list.");
    }

    private static void printWarning() {
        printWriter.println("Command '" + command[0] + "' not found. Type 'help' to get command list.");
    }

    private static void localOrNetVersion() throws Exception {
        System.out.println("Input 'l' for local, or 'n' for network version of JavaCommandLine.");
        System.out.println("Network version will build automatically in 10 sec");
        while (true) {
            String input;

            input = scanner.nextLine();
            if (input.equals("l")) {
                buildLocalVersion();
                break;
            } else if (input.equals("n")) {
                System.out.println("Waiting for user to connect...");
                buildNetworkVersion();
                break;
            } else {
                System.out.println("Wrong input.");
            }
        }
    }

    private static void chooseSystem() {
        if (new File ("C:\\").canRead()) {
            startedDirectory = "C:\\";
            systemWaySeparator = WINDOWS_WAY_SEPARATOR;
        } else if (new File("/").canRead()) {
            startedDirectory = "/";
            systemWaySeparator = LINUX_WAY_SEPARATOR;
        } else {
            System.out.println("Unknown OS");
            Exit.exitCommandLine();
        }
    }

    private static void buildNetworkVersion() {
        try {
            Socket socket = new Socket(CLIENT_IP, CLIENT_PORT);
            scanner = new Scanner(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("User connected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buildLocalVersion() {
        printWriter = new PrintWriter(System.out, true);
    }

    public static void startCommandLine() {
        try {
            chooseSystem();
            //localOrNetVersion();
            buildNetworkVersion();
            printGreetings();
            readAndRunCommand();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
