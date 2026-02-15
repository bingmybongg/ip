package alfred.command;

import alfred.task.TaskList;

/**
 * Represents a command to display help information.
 * <p>
 * When executed, this command displays a list of all available commands
 * and their purposes to help users understand how to use the application.
 */
public final class HelpCommand extends Command {

    /**
     * Creates a new HelpCommand.
     * <p>
     * This command requires no parameters as it simply displays help information.
     */
    public HelpCommand() {
    }

    /**
     * Executes the help command.
     * <p>
     * Returns the help message listing all available commands. The task list
     * parameter is not used but is required by the Command interface.
     *
     * @param tasks the task list (not used by this command)
     * @return a formatted help message listing all available commands
     */
    @Override
    public String execute(TaskList tasks) {
        return this.toString();
    }

    /**
     * Returns the help message listing all available commands.
     * <p>
     * The message includes:
     * <ul>
     *   <li>todo - Add a simple task</li>
     *   <li>deadline - Add a task with a deadline</li>
     *   <li>event - Add a task with start and end times</li>
     *   <li>mark - Mark a task as done</li>
     *   <li>unmark - Mark a task as not done</li>
     *   <li>delete - Delete a task</li>
     *   <li>list - List all tasks</li>
     *   <li>find - Find tasks by keyword</li>
     *   <li>bye - Exit the application</li>
     * </ul>
     *
     * @return the help message string
     */
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

    /**
     * Compares this help command to another object for equality.
     * <p>
     * All help commands are considered equal since they have no state.
     *
     * @param other the object to compare with
     * @return {@code true} if the other object is a HelpCommand, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof HelpCommand;
    }
}