import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by andrii on 16.10.15.
 */
public class CommandLine {

    private static final String WINDOWS_WAY_SEPARATOR = "\\";
    private static final String LINUX_WAY_SEPARATOR = "/";
    private static final int SERVER_PORT = 1488;
    private static String systemWaySeparator;
    private static String startedDirectory;
    private static Scanner scanner = new Scanner(System.in);
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static PrintWriter printWriter;
    private static String[] command;
    private static String currentDirectory;
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

    private static void readAndRunCommand() throws IOException {
        currentDirectory = startedDirectory;
        while (true) {
            printWriter.printf(currentDirectory + " ->");
            command = scanner.nextLine().split(" ");
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
                case "run":
                    if (command.length > 1) {
                        Run.runProgram(command[1]);
                    }
                    break;
                case "help":
                    Help.printHelp();
                    break;
                default:
                    printWarning();
                    break;
            }
        }
    }

    private static void printGreetings() {
        System.out.println("Java Command Line. Created by Valhalla.");
        System.out.println("Type 'help' to get command list.");
    }

    private static void printWarning() {
        printWriter.println("Command '" + command[0] + "' not found. Type 'help' to get command list.");
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
            System.exit(0);
        }
    }

    private static void localOrNetVersion() {
        System.out.println("Input 'l' for local, or 'n' for network version of JavaCommandLine");
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equals("l")) {
                printWriter = new PrintWriter(System.out, true);
                break;
            } else if (input.equals("n")) {
                System.out.println("Waiting for user to connect...");
                try {
                    serverSocket = new ServerSocket(SERVER_PORT);
                    socket = serverSocket.accept();
                    System.out.println("User connected.");
                    scanner = new Scanner(socket.getInputStream());
                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } else {
                System.out.println("Wrong input.");
            }
        }
    }

    public static void startCommandLine() {
        printGreetings();
        chooseSystem();
        localOrNetVersion();
        try {
            readAndRunCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
