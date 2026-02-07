package alfred.command;

import alfred.task.Task;
import alfred.task.TaskList;

public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    public String execute(TaskList tasks) {
        tasks.add(task);
        return this + "You currently have " + tasks.size() + " task(s) at the moment.\n";
    }

    @Override
    public String toString() {
        return "Adding task for you Sir\n\n" +
                "I have successfully added the task for you Sir:\n" +
                "  " + this.task + "\n";
    }
}
