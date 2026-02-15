package alfred.command;

import alfred.task.Task;
import alfred.task.TaskList;

/**
 * Represents a command to unmark a task (set it as not done).
 * <p>
 * When executed, this command unmarks the specified task in the task list
 * and returns a success message.
 */
public final class UnmarkCommand extends Command {
    private final Task task;

    /**
     * Creates a new UnmarkCommand for the specified task.
     *
     * @param task the task to unmark (must not be {@code null})
     * @throws NullPointerException if task is null
     */
    public UnmarkCommand(Task task) {
        if (task == null) {
            throw new NullPointerException("Task cannot be null");
        }
        this.task = task;
    }

    /**
     * Executes the unmark command on the given task list.
     * <p>
     * Unmarks the task in the list and returns a formatted success message
     * including the task details.
     *
     * @param tasks the task list to modify (must not be {@code null})
     * @return a formatted success message with the unmarked task details
     * @throws NullPointerException if tasks is null
     */
    @Override
    public String execute(TaskList tasks) {
        if (tasks == null) {
            throw new NullPointerException("TaskList cannot be null");
        }
        Task newTask = tasks.unmark(this.task);
        return formatSuccessMessage(newTask);
    }

    /**
     * Formats the success message for unmarking a task.
     *
     * @param task the unmarked task
     * @return the formatted success message
     */
    private String formatSuccessMessage(Task task) {
        return this + "   " + task + "\n";
    }

    /**
     * Returns the header message for the unmark command.
     *
     * @return a message indicating the task has been unmarked
     */
    @Override
    public String toString() {
        return """
                Unmarking task for you Sir
                
                I have successfully unmarked the task for you Sir:
                """;
    }

    /**
     * Compares this unmark command to another object for equality.
     * <p>
     * Two unmark commands are equal if they unmark the same task.
     *
     * @param other the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnmarkCommand)) {
            return false;
        }

        UnmarkCommand cmd = (UnmarkCommand) other;
        return this.task.equals(cmd.task);
    }
}