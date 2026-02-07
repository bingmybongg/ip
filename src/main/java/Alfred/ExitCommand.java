package Alfred;

import java.io.IOException;

public class ExitCommand extends Command {
    ExitCommand() {
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
