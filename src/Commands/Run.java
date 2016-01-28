package Commands;

import java.io.IOException;
import CommandLine.*;

/**
 * Created by andrii on 21.10.15.
 */
public class Run {

    public static void executeCommand(String[] command) {
        if (command.length < 1) {
            CommandLine.getPrintWriter().println("Empty argument. Write name of executable file");
        } else {
            runProgram(FileNameBuilder.buildFileName(command));
        }
    }

    public static void runProgram(String programName) {
        try {
            if (CommandLine.getStartedDirectory().equals("C:\\")) {
                new ProcessBuilder(CommandLine.getCurrentDirectory() + programName).start();
            } else {
                new ProcessBuilder(programName).start();
            }
        } catch (IOException e) {
            CommandLine.getPrintWriter().println("Can't find " + programName);
        }
    }

}
