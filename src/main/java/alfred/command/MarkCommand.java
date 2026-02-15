package alfred.command;

import alfred.task.Task;
import alfred.task.TaskList;

/**
 * Represents a command to mark a task as done.
 * <p>
 * When executed, this command marks the specified task in the task list
 * and returns a success message.
 */
public final class MarkCommand extends Command {
    private final Task task;

    /**
     * Creates a new MarkCommand for the specified task.
     *
     * @param task the task to mark (must not be {@code null})
     * @throws NullPointerException if task is null
     */
    public MarkCommand(Task task) {
        if (task == null) {
            throw new NullPointerException("Task cannot be null");
        }
        this.task = task;
    }

    /**
     * Executes the mark command on the given task list.
     * <p>
     * Marks the task in the list and returns a formatted success message
     * including the task details.
     *
     * @param tasks the task list to modify (must not be {@code null})
     * @return a formatted success message with the marked task details
     * @throws NullPointerException if tasks is null
     */
    @Override
    public String execute(TaskList tasks) {
        if (tasks == null) {
            throw new NullPointerException("TaskList cannot be null");
        }
        Task newTask = tasks.mark(this.task);
        return formatSuccessMessage(newTask);
    }

    /**
     * Formats the success message for marking a task.
     *
     * @param task the marked task
     * @return the formatted success message
     */
    private String formatSuccessMessage(Task task) {
        return this + "   " + task + "\n";
    }

    /**
     * Returns the header message for the mark command.
     *
     * @return a message indicating the task has been marked as done
     */
    @Override
    public String toString() {
        return """
                Marking task for you Sir
                
                I have successfully marked the task for you Sir:
                """;
    }

    /**
     * Compares this mark command to another object for equality.
     * <p>
     * Two mark commands are equal if they mark the same task.
     *
     * @param other the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand cmd = (MarkCommand) other;
        return this.task.equals(cmd.task);
    }
}