package Alfred;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;

public class Alfred {
    private final Ui ui;
    private final TaskList tasks;

    Alfred(String path) throws IOException {
        this.ui = new Ui();
        this.tasks = new TaskList(path);
    }

    Alfred() throws IOException {
        String path = System.getProperty("user.home") + File.separator + "data";
        this.ui = new Ui();
        this.tasks = new TaskList(path);
    }

    public String getResponse(String input) throws IOException {
        Command c = Parser.parse(input, this.tasks);
        String response = c.execute(this.tasks);
        return this.ui.getResponse(response);
    }

    public String toString() {
        return this.ui.toString();
    }

    public static void main(String[] args) throws IOException {
        Application.launch(Main.class, args);
    }
}
