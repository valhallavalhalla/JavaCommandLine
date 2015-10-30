package Commands;

import java.io.IOException;
import CommandLine.*;

/**
 * Created by andrii on 16.10.15.
 */
public class PWD {

    public static void printWayDirectory() throws IOException {
        CommandLine.getPrintWriter().println(CommandLine.getCurrentDirectory());
    }

}
