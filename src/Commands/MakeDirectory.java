package Commands;

import CommandLine.*;
import java.io.File;

/**
 * Created by andrii on 23.10.15.
 */
public class MakeDirectory {

    public static void makeDirectory(String[] command) {
        boolean result;
        if (command[1].startsWith(CommandLine.getStartedDirectory())) {
            result = new File(command[1]).mkdir();
        } else {
            result = new File(CommandLine.getCurrentDirectory() + command[1]).mkdir();
        }
        if (!result) {
            System.out.println("Error. Access denied or directory already exist.");
        }
    }

}
