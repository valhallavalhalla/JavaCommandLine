import java.io.File;

/**
 * Created by andrii on 16.10.15.
 */
public class CD {

    public static void changeDirectory(String wantedDirectory) {
        if (new File(CommandLine.getCurrentDirectory() + wantedDirectory).canRead()) {
            if (wantedDirectory.startsWith("/")) {
                CommandLine.setCurrentDirectory(wantedDirectory);
                CommandLine.setCurrentDirectoryFile(new File(CommandLine.getCurrentDirectory()));
            } else {
                CommandLine.setCurrentDirectory(CommandLine.getCurrentDirectory() + wantedDirectory + "/");
                CommandLine.setCurrentDirectoryFile(new File(CommandLine.getCurrentDirectory()));
            }
        } else {
            System.out.println("Cannot find " + wantedDirectory);
        }
    }

}
