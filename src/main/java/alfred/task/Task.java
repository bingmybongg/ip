package alfred.task;

/**
 * Represents an abstract task with a description and completion status.
 * <p>
 * Subclasses must define how marking/unmarking works and provide a type
 * identifier for persistence. All tasks are immutable - marking or unmarking
 * returns a new task instance rather than modifying the existing one.
 */
public abstract class Task {
    protected final String task;
    protected final Boolean isMarked;

    /**
     * Creates a new unmarked task with the given description.
     *
     * @param task the task description (must not be null or blank)
     * @throws NullPointerException if task is null
     * @throws IllegalArgumentException if task is blank
     */
    protected Task(String task) {
        if (task == null) {
            throw new NullPointerException("Task description cannot be null");
        }
        if (task.isBlank()) {
            throw new IllegalArgumentException("Task description cannot be blank");
        }

        this.task = task;
        this.isMarked = false;
    }

    /**
     * Creates a new task with the given description and marked state.
     * <p>
     * This constructor is typically used internally by subclasses when creating
     * new instances via {@link #mark()} or {@link #unmark()}.
     *
     * @param task the task description (must not be null or blank)
     * @param isMarked whether the task is marked as done
     * @throws NullPointerException if task is null
     * @throws IllegalArgumentException if task is blank
     */
    protected Task(String task, boolean isMarked) {
        if (task == null) {
            throw new NullPointerException("Task description cannot be null");
        }
        if (task.isBlank()) {
            throw new IllegalArgumentException("Task description cannot be blank");
        }

        this.task = task;
        this.isMarked = isMarked;
    }

    /**
     * Returns the marked state as a string for persistence.
     * <p>
     * This method is used when saving tasks to storage.
     *
     * @return {@code "1"} if the task is marked as done, {@code "0"} otherwise
     */
    public String getMark() {
        return this.isMarked ? "1" : "0";
    }

    /**
     * Returns the task description.
     *
     * @return the task description string
     */
    public String getTask() {
        return this.task;
    }

    /**
     * Checks if the task description contains the specified keyword.
     * <p>
     * The search is case-sensitive and matches any substring within the task description.
     *
     * @param keyword the substring to search for
     * @return {@code true} if the task description contains the keyword, {@code false} otherwise
     * @throws NullPointerException if keyword is null (from {@link String#contains(CharSequence)})
     */
    public boolean contains(String keyword) {
        String upperTask = this.task.toUpperCase();
        String lowerTask = this.task.toLowerCase();

        return upperTask.contains(keyword.toUpperCase()) || lowerTask.contains(keyword.toLowerCase());
    }

    /**
     * Returns a new task instance representing this task marked as done.
     * <p>
     * This method does not modify the current task instance. Subclasses must
     * implement this to return a new instance of the appropriate type with
     * the marked state set to {@code true}.
     *
     * @return a new marked task instance
     */
    public abstract Task mark();

    /**
     * Returns a new task instance representing this task unmarked (not done).
     * <p>
     * This method does not modify the current task instance. Subclasses must
     * implement this to return a new instance of the appropriate type with
     * the marked state set to {@code false}.
     *
     * @return a new unmarked task instance
     */
    public abstract Task unmark();

    /**
     * Returns the type identifier used for storage and display.
     * <p>
     * The type identifier is used when persisting tasks to storage and helps
     * distinguish between different task types (e.g., "todo", "deadline", "event").
     *
     * @return the task type string
     */
    public abstract String type();

    /**
     * Compares this task to another object for equality.
     * <p>
     * Subclasses must implement this method to define their equality semantics.
     * Implementations should typically compare the task description and marked state,
     * and may also compare type-specific fields (e.g., dates for deadlines).
     * <p>
     * Note: Implementations must be consistent with {@link #hashCode()}.
     *
     * @param other the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    public abstract boolean equals(Object other);

    /**
     * Returns a string representation of this task suitable for display.
     * <p>
     * The format includes a checkbox indicator showing completion status
     * followed by the task description: {@code "[X] "} for marked tasks
     * or {@code "[ ] "} for unmarked tasks.
     *
     * @return a display string with completion status and task description
     */
    @Override
    public String toString() {
        return (isMarked ? "[X] " : "[ ] ") + this.task;
    }
}
