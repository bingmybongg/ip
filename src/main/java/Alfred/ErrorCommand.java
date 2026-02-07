package Alfred;

public class ErrorCommand extends Command {
    private final String error;

    ErrorCommand(String error) {
        this.error = error;
    }

    public String execute(TaskList tasks) {
        return this.error;
    }

}
