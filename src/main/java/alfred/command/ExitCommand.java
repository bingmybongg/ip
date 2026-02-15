package alfred.command;

import java.io.IOException;

import alfred.task.TaskList;

/**
 * Represents a command to exit the application.
 * <p>
 * When executed, this command saves all tasks to persistent storage
 * and returns a farewell message. This is typically the last command
 * executed before the application terminates.
 */
public final class ExitCommand extends Command {

    /**
     * Creates a new ExitCommand.
     * <p>
     * This command requires no parameters as it simply saves tasks
     * and exits the application.
     */
    public ExitCommand() {
    }

    /**
     * Executes the exit command on the given task list.
     * <p>
     * Saves all tasks to persistent storage and returns a farewell message.
     * The application should terminate after this command executes successfully.
     *
     * @param tasks the task list to save (must not be {@code null})
     * @return a farewell message
     * @throws IOException if an I/O error occurs while saving tasks
     * @throws NullPointerException if tasks is null
     */
    @Override
    public String execute(TaskList tasks) throws IOException {
        if (tasks == null) {
            throw new NullPointerException("TaskList cannot be null");
        }
        tasks.save();
        return formatFarewellMessage();
    }

    /**
     * Formats the farewell message.
     *
     * @return the formatted farewell message
     */
    private String formatFarewellMessage() {
        return this.toString();
    }

    /**
     * Returns the farewell message for the exit command.
     *
     * @return a farewell message to display when exiting
     */
    @Override
    public String toString() {
        return "Goodbye Sir!\n";
    }

    /**
     * Compares this exit command to another object for equality.
     * <p>
     * All exit commands are considered equal since they have no state.
     *
     * @param other the object to compare with
     * @return {@code true} if the other object is an ExitCommand, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof ExitCommand;
    }
}