package alfred.command;

import alfred.task.TaskList;

/**
 * Represents a command to list all tasks.
 * <p>
 * When executed, this command displays all tasks in the task list
 * with a header message.
 */
public final class ListCommand extends Command {

    /**
     * Creates a new ListCommand.
     * <p>
     * This command requires no parameters as it simply displays all tasks.
     */
    public ListCommand() {
    }

    /**
     * Executes the list command on the given task list.
     * <p>
     * Returns a formatted string containing the header message followed
     * by all tasks in the list.
     *
     * @param tasks the task list to display (must not be {@code null})
     * @return a formatted string with the header and all tasks
     * @throws NullPointerException if tasks is null
     */
    @Override
    public String execute(TaskList tasks) {
        if (tasks == null) {
            throw new NullPointerException("TaskList cannot be null");
        }
        return formatTaskList(tasks);
    }

    /**
     * Formats the task list with the header message.
     *
     * @param tasks the task list to format
     * @return the formatted string
     */
    private String formatTaskList(TaskList tasks) {
        return this + tasks.toString();
    }

    /**
     * Returns the header message for the list command.
     *
     * @return a header message for displaying the task list
     */
    @Override
    public String toString() {
        return "Here's your list Sir\n";
    }

    /**
     * Compares this list command to another object for equality.
     * <p>
     * All list commands are considered equal since they have no state.
     *
     * @param other the object to compare with
     * @return {@code true} if the other object is a ListCommand, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof ListCommand;
    }
}