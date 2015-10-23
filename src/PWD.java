import java.io.IOException;

/**
 * Created by andrii on 16.10.15.
 */
public class PWD {

    public static void printWayDirectory() throws IOException {
        CommandLine.getPrintWriter().println(CommandLine.getCurrentDirectory());
    }

}
