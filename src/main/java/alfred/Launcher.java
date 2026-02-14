package alfred;

import java.io.IOException;

import alfred.ui.Main;
import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) throws IOException {
        Application.launch(Main.class, args);
    }
}
