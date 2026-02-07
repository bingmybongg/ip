package Alfred;

public class UnmarkCommand extends Command {
    private final Task task;

    UnmarkCommand(Task task) {
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
