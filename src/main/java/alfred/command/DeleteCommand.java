package alfred.command;

import alfred.task.Task;
import alfred.task.TaskList;

/**
 * Represents a command to delete a task from the task list.
 * <p>
 * When executed, this command removes the specified task from the task list
 * and returns a success message showing the deleted task and the remaining
 * task count.
 */
public final class DeleteCommand extends Command {
    private final Task task;

    /**
     * Creates a new DeleteCommand for the specified task.
     *
     * @param task the task to delete (must not be {@code null})
     * @throws NullPointerException if task is null
     */
    public DeleteCommand(Task task) {
        if (task == null) {
            throw new NullPointerException("Task cannot be null");
        }
        this.task = task;
    }

    /**
     * Executes the delete command on the given task list.
     * <p>
     * Removes the task from the list and returns a formatted success message
     * including the deleted task details and the remaining task count.
     *
     * @param tasks the task list to modify (must not be {@code null})
     * @return a formatted success message with deletion details and remaining count
     * @throws NullPointerException if tasks is null
     */
    @Override
    public String execute(TaskList tasks) {
        if (tasks == null) {
            throw new NullPointerException("TaskList cannot be null");
        }
        tasks.delete(task);
        return formatSuccessMessage(tasks);
    }

    /**
     * Formats the success message for deleting a task.
     *
     * @param tasks the task list to get the remaining count from
     * @return the formatted success message
     */
    private String formatSuccessMessage(TaskList tasks) {
        return this + "You currently have " + tasks.size() + " task(s) at the moment\n\n";
    }

    /**
     * Returns the header message for the delete command.
     * <p>
     * Includes the task that was deleted.
     *
     * @return a message indicating the task has been deleted, including task details
     */
    @Override
    public String toString() {
        return "Deleting task for you Sir\n\n" +
                "I have successfully deleted the task for you Sir:\n" +
                "   " + this.task + "\n";
    }

    /**
     * Compares this delete command to another object for equality.
     * <p>
     * Two delete commands are equal if they delete the same task.
     *
     * @param other the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand cmd = (DeleteCommand) other;
        return this.task.equals(cmd.task);
    }
}