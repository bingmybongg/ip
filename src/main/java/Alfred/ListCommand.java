package Alfred;

public class ListCommand extends Command {
    ListCommand() {
    }

    public String execute(TaskList tasks) {
        return this + tasks.toString();
    }

    public String toString() {
        return "Here's your list Sir\n";
    }
}
