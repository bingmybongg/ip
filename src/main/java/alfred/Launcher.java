package alfred;

import java.io.IOException;

import alfred.ui.Main;
import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues with JavaFX.
 * Handles application initialization and error reporting.
 */
public class Launcher {
    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments to pass to the application
     */
    public static void main(String[] args) {
        try {
            Application.launch(Main.class, args);
        } catch (Exception e) {
            System.err.println("Failed to launch application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}