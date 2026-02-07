package alfred.command;

import alfred.task.Task;
import alfred.task.TaskList;

public class UnmarkCommand extends Command {
    private final Task task;

    public UnmarkCommand(Task task) {
        this.task = task;
    }

    public String execute(TaskList tasks) {
        Task newTask = tasks.unmark(this.task);
        return this + "   " + newTask + "\n";
    }

    @Override
    public String toString() {
        return """
                Marking task for you Sir
                
                I have successfully unmarked the task for you Sir:
                """;
    }
}
