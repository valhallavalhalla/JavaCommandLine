import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andrii on 17.10.15.
 */
public class Help {

    private static String[] commands = {"dir", "cd [dir}", "pwd", "run"};
    public static void printHelp() {
        for (String s : commands) {
            CommandLine.getPrintWriter().println(s);
        }
    }
}
