package Commands;

import CommandLine.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by andrii on 16.10.15.
 */
public class CD {

    public static void changeDirectory(String[] command) throws IOException {
        String wantedDirectory = FileNameBuilder.buildFileName(command);
        if (new File(CommandLine.getCurrentDirectory() + wantedDirectory).canRead() || new File(wantedDirectory).canRead()) {
            if (wantedDirectory.startsWith(CommandLine.getStartedDirectory())) {
                CommandLine.setCurrentDirectory(wantedDirectory);
            } else {
                CommandLine.setCurrentDirectory(CommandLine.getCurrentDirectory() + wantedDirectory + CommandLine.getSystemWaySeparator());
            }
        } else {
            CommandLine.getPrintWriter().println("Cannot find " + wantedDirectory);
        }
    }
}


