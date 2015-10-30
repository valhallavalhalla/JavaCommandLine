package Commands;

/**
 * Created by andrii on 25.10.15.
 */
public class FileNameBuilder {

    public static String buildFileName(String[] command) {
        String wantedDirectory = command[1];
        for (int i = 2; i < command.length; i++) {
            wantedDirectory = wantedDirectory + " " + command[i];
        }
        return wantedDirectory;
    }

    public static String buildFileName(String[] command, String argumentSplitter) {
        String wantedDirectory = command[1];
        for (int i = 2; i < command.length; i++) {
            if (command[i].equals(argumentSplitter)) {
                break;
            }
            wantedDirectory = wantedDirectory + " " + command[i];
        }
        return wantedDirectory;
    }

}