package alfred.command;

import java.io.IOException;

import alfred.task.TaskList;

/**
 * Represents an abstract command that can be executed on a task list.
 * <p>
 * This is the base class for all commands in the application. Subclasses
 * must implement the {@link #execute(TaskList)} method to define specific
 * command behavior.
 * <p>
 * Common command types include:
 * <ul>
 *   <li>{@link AddCommand} - Adds a new task</li>
 *   <li>{@link DeleteCommand} - Deletes a task</li>
 *   <li>{@link MarkCommand} - Marks a task as done</li>
 *   <li>{@link UnmarkCommand} - Marks a task as not done</li>
 *   <li>{@link ListCommand} - Lists all tasks</li>
 *   <li>{@link FindCommand} - Finds tasks by keyword</li>
 *   <li>{@link ExitCommand} - Exits and saves tasks</li>
 *   <li>{@link ErrorCommand} - Displays error messages</li>
 * </ul>
 */
public abstract class Command {

    /**
     * Executes the command on the given task list.
     * <p>
     * Subclasses must implement this method to define their specific behavior.
     * The method may modify the task list (e.g., add, delete, mark tasks) or
     * simply query it (e.g., list, find tasks).
     *
     * @param tasks the task list to operate on (must not be {@code null} for most commands)
     * @return a formatted string message describing the result of the command execution
     * @throws IOException if an I/O error occurs (e.g., when saving tasks in {@link ExitCommand})
     */
    public abstract String execute(TaskList tasks) throws IOException;

    /**
     * Compares this command to another object for equality.
     * <p>
     * Subclasses must implement this method to define their equality semantics.
     * Implementations should typically compare command type and any state
     * (e.g., the task to mark, the keyword to search for).
     * <p>
     * Note: Implementations should also override {@link #hashCode()} to maintain
     * the general contract for the {@code Object.hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    public abstract boolean equals(Object obj);

    /**
     * Returns a string representation of this command.
     * <p>
     * Subclasses should override this method to provide meaningful string
     * representations, typically used as header messages in command output.
     *
     * @return a string representation of this command
     */
    @Override
    public abstract String toString();
}