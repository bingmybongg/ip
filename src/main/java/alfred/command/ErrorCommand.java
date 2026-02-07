package alfred.command;

import alfred.task.TaskList;

public class ErrorCommand extends Command {
    private final String error;

    public ErrorCommand(String error) {
        this.error = error;
    }

    public String execute(TaskList tasks) {
        return this.error;
    }

}
