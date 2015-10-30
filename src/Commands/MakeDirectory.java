package Commands;

import CommandLine.*;
import java.io.File;

/**
 * Created by andrii on 23.10.15.
 */
public class MakeDirectory {

    public static void makeDirectory(String[] command, String currentDirectory) {
        boolean result = new File(currentDirectory + command[1]).mkdir();
        System.out.println(result);
    }

}
