package Alfred;

public class FindCommand extends Command {
    private final String keyword;

    FindCommand(String keyword) {
        this.keyword = keyword;
    }

    public String execute(TaskList tasks) {
        TaskList foundTasks = tasks.find(this.keyword);
        return this + foundTasks.toString() + "\n";
    }

    @Override
    public String toString() {
        return "Finding the task matching " + this.keyword + " for you Sir\n\n" +
               "Here are the matching tasks in your list Sir:\n";
    }
}
