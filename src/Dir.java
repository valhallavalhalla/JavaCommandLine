import java.io.File;

/**
 * Created by andrii on 16.10.15.
 */
public class Dir {

    public static void directory(File currentDirectoryFile) {
        for (String s : currentDirectoryFile.list()) {
            System.out.println(s);
        }
    }

}
