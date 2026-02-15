package alfred.command;

import alfred.task.TaskList;

/**
 * Represents a command to find tasks matching a keyword.
 * <p>
 * When executed, this command searches the task list for tasks whose
 * descriptions contain the specified keyword and returns the matching tasks.
 */
public final class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a new FindCommand with the specified search keyword.
     *
     * @param keyword the keyword to search for (must not be {@code null} or blank)
     * @throws NullPointerException if keyword is null
     * @throws IllegalArgumentException if keyword is blank
     */
    public FindCommand(String keyword) {
        if (keyword == null) {
            throw new NullPointerException("Keyword cannot be null");
        }
        if (keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword cannot be blank");
        }
        this.keyword = keyword;
    }

    /**
     * Executes the find command on the given task list.
     * <p>
     * Searches for tasks containing the keyword in their descriptions
     * and returns a formatted string with the results.
     *
     * @param tasks the task list to search (must not be {@code null})
     * @return a formatted string with the header and matching tasks
     * @throws NullPointerException if tasks is null
     */
    @Override
    public String execute(TaskList tasks) {
        if (tasks == null) {
            throw new NullPointerException("TaskList cannot be null");
        }
        TaskList foundTasks = tasks.find(this.keyword);
        return formatSearchResults(foundTasks);
    }

    /**
     * Formats the search results with the header message.
     *
     * @param foundTasks the task list containing matching tasks
     * @return the formatted search results string
     */
    private String formatSearchResults(TaskList foundTasks) {
        return this + foundTasks.toString() + "\n";
    }

    /**
     * Returns the header message for the find command.
     * <p>
     * Includes the search keyword being used.
     *
     * @return a header message indicating the search keyword and results section
     */
    @Override
    public String toString() {
        return "Finding the task matching " + this.keyword + " for you Sir\n\n" +
                "Here are the matching tasks in your list Sir:\n";
    }

    /**
     * Compares this find command to another object for equality.
     * <p>
     * Two find commands are equal if they search for the same keyword.
     *
     * @param other the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand cmd = (FindCommand) other;
        return this.keyword.equals(cmd.keyword);
    }
}