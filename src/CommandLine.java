import java.io.File;
import java.net.ServerSocket;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by andrii on 16.10.15.
 */
public class CommandLine {

    private static final String startedDirectory = "/";
    private static String[] command;
    private static Scanner scanner = new Scanner(System.in);
    private static String currentDirectory = startedDirectory;
    private static File currentDirectoryFile = new File(currentDirectory);

    private static Thread commandLineThread = new Thread(new Runnable() {
        @Override
        public void run() {
            printGreetings();
            readAndRunCommand();
        }
    });

    public static void setCurrentDirectory(String currentDirectory) {
        CommandLine.currentDirectory = currentDirectory;
    }

    public static String getCurrentDirectory() {
        return currentDirectory;
    }

    public static File getCurrentDirectoryFile() {
        return currentDirectoryFile;
    }

    public static void setCurrentDirectoryFile(File currentDirectoryFile) {
        CommandLine.currentDirectoryFile = currentDirectoryFile;
    }

    private static void readAndRunCommand() {
        while (true) {
            System.out.print(currentDirectory + "->");
            command = scanner.nextLine().split(" ");
            switch (command[0]) {
                case "cd":
                    if (command.length > 1) {
                        CD.changeDirectory(command[1]);
                    }
                    break;
                case "dir":
                    Dir.directory(currentDirectoryFile);
                    break;
                case "pwd":
                    PWD.printWayDirectory(currentDirectory);
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
        System.out.println("Command '" + command[0] + "' not found. Type 'help' to get command list.");
    }

    public static void startCommandLine() {
        commandLineThread.run();
    }

}
