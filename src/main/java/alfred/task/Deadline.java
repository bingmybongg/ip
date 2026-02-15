package alfred.task;

/**
 * Represents a task with a specific deadline or due date.
 * <p>
 * A deadline task has a due date/time in addition to the task description
 * and marked state inherited from {@link Task}. Instances are immutable -
 * marking or unmarking returns a new deadline instance.
 */
public final class Deadline extends Task {
    private final String deadline;

    /**
     * Creates a new unmarked deadline with the given description and due date.
     *
     * @param task the task description (must not be null or blank)
     * @param deadline the due date/time (must not be null or blank)
     * @throws NullPointerException if task or deadline is null
     * @throws IllegalArgumentException if task or deadline is blank
     */
    public Deadline(String task, String deadline) {
        super(task);
        if (deadline == null) {
            throw new NullPointerException("Deadline cannot be null");
        }
        if (deadline.isBlank()) {
            throw new IllegalArgumentException("Deadline cannot be blank");
        }
        this.deadline = deadline;
    }

    /**
     * Internal constructor used to create a deadline with an explicit marked state.
     * <p>
     * This is typically called by {@link #mark()} and {@link #unmark()} to create
     * new instances while preserving immutability. All fields from the current
     * deadline are copied except for the marked state.
     *
     * @param curr the current deadline to copy fields from
     * @param isMarked whether the deadline is marked as done
     */
    private Deadline(Deadline curr, boolean isMarked) {
        super(curr.task, isMarked);
        this.deadline = curr.deadline;
    }

    /**
     * Returns the storage type identifier for this task.
     *
     * @return the string {@code "deadline"} used when saving/loading tasks
     */
    @Override
    public String type() {
        return "deadline";
    }

    /**
     * Returns the due date/time of this deadline.
     *
     * @return the deadline date/time string
     */
    public String getDeadline() {
        return this.deadline;
    }

    /**
     * Returns a new {@code Deadline} representing this deadline marked as done.
     * <p>
     * This method does not modify the current deadline instance. Instead, it creates
     * and returns a new {@code Deadline} with the same description and due date but
     * with the marked state set to {@code true}.
     *
     * @return a new {@code Deadline} instance with the marked state set to {@code true}
     */
    @Override
    public Task mark() {
        return new Deadline(this, true);
    }

    /**
     * Returns a new {@code Deadline} representing this deadline unmarked (not done).
     * <p>
     * This method does not modify the current deadline instance. Instead, it creates
     * and returns a new {@code Deadline} with the same description and due date but
     * with the marked state set to {@code false}.
     *
     * @return a new {@code Deadline} instance with the marked state set to {@code false}
     */
    @Override
    public Task unmark() {
        return new Deadline(this, false);
    }

    /**
     * Compares this deadline to another object for equality.
     * <p>
     * Two deadlines are equal when they are both {@code Deadline} instances and have
     * the same description, due date, and marked state.
     *
     * @param other the object to compare with
     * @return {@code true} if the other object is equal to this deadline, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof Deadline otherDeadline) {
            return (otherDeadline.task.equals(this.task)) &&
                   (otherDeadline.deadline.equals(this.deadline)) &&
                   (this.isMarked == otherDeadline.isMarked);
        }

        return false;
    }

    /**
     * Returns a string representation of this deadline suitable for display.
     * <p>
     * The format includes a deadline type indicator {@code "[D]"}, followed by the
     * completion status and task description from the superclass, and the due date.
     * <p>
     * Example outputs:
     * <ul>
     *   <li>{@code "[D][ ] Submit report (by: Friday 5pm)"} - unmarked deadline</li>
     *   <li>{@code "[D][X] Submit report (by: Friday 5pm)"} - marked deadline</li>
     * </ul>
     *
     * @return a display string with deadline type, completion status, description, and due date
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }
}
