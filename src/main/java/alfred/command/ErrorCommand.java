package alfred.command;

import alfred.task.TaskList;

/**
 * Represents a command that encapsulates an error message.
 * <p>
 * This command is returned when user input cannot be parsed or when
 * validation fails. When executed, it simply returns the error message
 * without modifying the task list.
 */
public final class ErrorCommand extends Command {
    private final String error;

    /**
     * Creates a new ErrorCommand with the specified error message.
     *
     * @param error the error message to display (must not be {@code null} or blank)
     * @throws NullPointerException if error is null
     * @throws IllegalArgumentException if error is blank
     */
    public ErrorCommand(String error) {
        if (error == null) {
            throw new NullPointerException("Error message cannot be null");
        }
        if (error.isBlank()) {
            throw new IllegalArgumentException("Error message cannot be blank");
        }
        this.error = error;
    }

    /**
     * Executes the error command on the given task list.
     * <p>
     * This command does not modify the task list and simply returns
     * the error message. The tasks parameter may be null since it is
     * not used.
     *
     * @param tasks the task list (not used by this command, may be {@code null})
     * @return the error message
     */
    @Override
    public String execute(TaskList tasks) {
        return this.error;
    }

    /**
     * Returns the error message.
     * <p>
     * This method is provided for consistency with other Command classes,
     * though the error message is returned directly by {@link #execute(TaskList)}.
     *
     * @return the error message
     */
    @Override
    public String toString() {
        return this.error;
    }

    /**
     * Compares this error command to another object for equality.
     * <p>
     * Two error commands are equal if they contain the same error message.
     *
     * @param other the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ErrorCommand)) {
            return false;
        }

        ErrorCommand cmd = (ErrorCommand) other;
        return this.error.equals(cmd.error);
    }
}