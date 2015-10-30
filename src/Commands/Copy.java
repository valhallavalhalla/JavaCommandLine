package Commands;

import CommandLine.CommandLine;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

/**
 * Created by andrii on 24.10.15.
 */
public class Copy {

    private static String fileToCopyName;
    private static String wayToFileWithName;
    private static File fileToCopy;
    private static ServerSocket serverForCopy;
    private static final int SERVER_FOR_COPY = 21212;
    private static Socket clientSocket;
    public static void copy(String[] command, String currentDirectory) {
        fileToCopyName = FileNameBuilder.buildFileName(command);
        if (!fileToCopyName.startsWith(CommandLine.getStartedDirectory())) {
            wayToFileWithName = currentDirectory + fileToCopyName;
        } else {
            wayToFileWithName = fileToCopyName;
        }
        fileToCopy = new File(wayToFileWithName);
        if (fileToCopy.canRead()) {
            try {
                if (fileToCopy.isDirectory()) {
                    copyDirectory(fileToCopy);
                } else {
                    copyFile(fileToCopy, new File(""));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            CommandLine.getPrintWriter().println("File " + wayToFileWithName + " not found.");
        }
    }

    private static void copyDirectory(File directory) throws IOException {

        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                copyDirectory(file);
            } else {
                copyFile(file, directory);
            }
        }
    }

    private static void copyFile(File fileToCopy, File fileDirectory) throws IOException {
        serverForCopy = new ServerSocket(SERVER_FOR_COPY);
        CommandLine.getPrintWriter().println("Copying...");
        clientSocket = serverForCopy.accept();
        new DataOutputStream(clientSocket.getOutputStream()).writeUTF(fileToCopy.getName());
        new DataOutputStream(clientSocket.getOutputStream()).writeUTF(fileDirectory.getName());
        Files.copy(fileToCopy.toPath(), clientSocket.getOutputStream());
        CommandLine.getPrintWriter().println("Successful.");
        clientSocket.close();
        serverForCopy.close();
    }
}
