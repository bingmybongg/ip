package alfred.command;

import java.io.IOException;

import alfred.task.TaskList;

public abstract class Command {
    /**
     * Executes the command on the given tasks on the tasklist.
     *
     * @param tasks TaskList to operate on.
     */
    public abstract String execute(TaskList tasks) throws IOException;
}
