package alfred.command;

import alfred.task.Task;
import alfred.task.TaskList;

public class MarkCommand extends Command {
    private final Task task;

    public MarkCommand(Task task) {
        this.task = task;
    }

    public String execute(TaskList tasks) {
        Task newTask = tasks.mark(this.task);
        return this + "   " + newTask + "\n";
    }

    @Override
    public String toString() {
        return """
                Marking task for you Sir
                
                I have successfully marked the task for you Sir:
                """;
    }
}
