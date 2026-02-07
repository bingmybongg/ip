package alfred.command;

import alfred.task.TaskList;

public class HelpCommand extends Command {
    public HelpCommand() {
    }

    public String execute(TaskList tasks) {
        return this.toString();
    }

    public String toString() {
        return """
                Here's a list of commands that can get you started Sir:
                1. todo (Adds a TODO task)
                2. deadline (Adds a DEADLINE task)
                3. event (Adds an EVENT task)
                4. mark (Marks a task)
                5. unmark (Unmarks a task)
                6. delete (Deletes a task)
                7. list (Lists all tasks)
                8. find (Finds a task based on a keyword)
                9. bye (Exits the program)
                """;
    }
}
