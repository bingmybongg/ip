package alfred;

import java.io.File;
import java.io.IOException;

import alfred.command.Command;
import alfred.parser.Parser;
import alfred.task.TaskList;
import alfred.ui.Ui;

public class Alfred {
    private static final String DEFAULT_DATA_FOLDER = "data";

    private final Ui ui;
    private final Parser parser;
    private final TaskList tasks;

    /**
     * Creates an Alfred instance with a custom data path.
     *
     * @param path the path to store task data
     * @throws IOException if task data cannot be loaded
     */
    public Alfred(String path) throws IOException {
        this.ui = new Ui();
        this.tasks = new TaskList(path);
        this.parser = new Parser(this.ui);
    }

    /**
     * Creates an Alfred instance with default data path in user home directory.
     *
     * @throws IOException if task data cannot be loaded
     */
    public Alfred() throws IOException {
        this(System.getProperty("user.home") + File.separator + DEFAULT_DATA_FOLDER);
    }

    /**
     * Processes user input and returns a response.
     *
     * @param input the user's command input
     * @return the response from executing the command
     * @throws IOException if an I/O error occurs
     */
    public String getResponse(String input) throws IOException {
        Command c = this.parser.parse(input, this.tasks);
        String response = c.execute(this.tasks);
        return this.ui.getResponse(response);
    }

    @Override
    public String toString() {
        return this.ui.toString();
    }
}