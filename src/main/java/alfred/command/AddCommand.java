package alfred.command;

import alfred.task.Task;
import alfred.task.TaskList;

/**
 * Represents a command to add a new task to the task list.
 * <p>
 * When executed, this command adds the specified task to the task list
 * and returns a success message showing the added task and the total
 * task count.
 */
public final class AddCommand extends Command {
    private final Task task;

    /**
     * Creates a new AddCommand for the specified task.
     *
     * @param task the task to add (must not be {@code null})
     * @throws NullPointerException if task is null
     */
    public AddCommand(Task task) {
        if (task == null) {
            throw new NullPointerException("Task cannot be null");
        }
        this.task = task;
    }

    /**
     * Executes the add command on the given task list.
     * <p>
     * Adds the task to the list and returns a formatted success message
     * including the added task details and the total task count.
     *
     * @param tasks the task list to modify (must not be {@code null})
     * @return a formatted success message with addition details and total count
     * @throws NullPointerException if tasks is null
     */
    @Override
    public String execute(TaskList tasks) {
        if (tasks == null) {
            throw new NullPointerException("TaskList cannot be null");
        }
        tasks.add(task);
        return formatSuccessMessage(tasks);
    }

    /**
     * Formats the success message for adding a task.
     *
     * @param tasks the task list to get the total count from
     * @return the formatted success message
     */
    private String formatSuccessMessage(TaskList tasks) {
        return this + "You currently have " + tasks.size() + " task(s) at the moment.\n";
    }

    /**
     * Returns the header message for the add command.
     * <p>
     * Includes the task that was added.
     *
     * @return a message indicating the task has been added, including task details
     */
    @Override
    public String toString() {
        return "Adding task for you Sir\n\n" +
                "I have successfully added the task for you Sir:\n" +
                "  " + this.task + "\n";
    }

    /**
     * Compares this add command to another object for equality.
     * <p>
     * Two add commands are equal if they add the same task.
     *
     * @param other the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand cmd = (AddCommand) other;
        return this.task.equals(cmd.task);
    }
}