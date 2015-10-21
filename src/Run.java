import java.io.IOException;

/**
 * Created by andrii on 21.10.15.
 */
public class Run {

    public static void runProgram(String program) {
        try {
            Process process = new ProcessBuilder(program).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
