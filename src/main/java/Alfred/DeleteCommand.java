package Alfred;

public class DeleteCommand extends Command {
    private final Task task;

    DeleteCommand(Task task) {
        this.task = task;
    }

    public String execute(TaskList tasks) {
        tasks.delete(task);
        return this + "You currently have " + tasks.size() + " task(s) at the moment\n\n";
    }

    @Override
    public String toString() {
        return "Deleting task for you Sir\n\n" +
               "I have successfully deleted the task for you Sir:\n" +
               "   " + this.task + "\n";
    }
}
