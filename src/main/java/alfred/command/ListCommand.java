package alfred.command;

import alfred.task.TaskList;

public class ListCommand extends Command {
    public ListCommand() {
    }

    public String execute(TaskList tasks) {
        return this + tasks.toString();
    }

    public String toString() {
        return "Here's your list Sir\n";
    }
}
