package Commands;

import java.io.File;
import java.io.IOException;
import CommandLine.*;

/**
 * Created by andrii on 16.10.15.
 */
public class Dir {

    public static void directory(String currentDirectory) throws IOException {
        for (String s : new File(currentDirectory).list()) {
            CommandLine.getPrintWriter().println(s);
        }
    }

}
