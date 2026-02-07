package alfred.command;

import java.io.IOException;

import alfred.task.TaskList;

public class ExitCommand extends Command {
    public ExitCommand() {
    }

    public String execute(TaskList tasks) throws IOException {
        tasks.save();
        return this.toString();
    }

    @Override
    public String toString() {
        return "Goodbye Sir!\n";
    }
}
