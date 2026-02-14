package alfred.command;

import alfred.task.TaskList;

public class HelpCommand extends Command {
    public HelpCommand() {
    }

    public String execute(TaskList tasks) {
        return this.toString();
    }

    @Override
    public String toString() {
        return """
               Here's a list of commands to get you started:
               1. todo (To add a TODO task)
               2. deadline (To add a DEADLINE task)
               3. event (To add an EVENT task)
               4. mark (To mark a task)
               5. unmark (To unmark a task)
               6. delete (To delete a task)
               7. list (To list all tasks)
               8. find (To find a task)
               9. bye (To quit the program)
               """;
    }
}
