package alfred.task;

/**
 * A {@code Todo} represents a task without any date/time attached.
 * <p>
 * Instances are plain data holders that delegate most behavior to the superclass
 * and provide type information used for storage and display.
 */
public final class Todo extends Task {
    /**
     * Creates a new Todo with the given task description.
     *
     * @param task the non\-null, non\-empty description of the todo
     */
    public Todo(String task) {
        super(task);
    }

    /**
     * Internal constructor used to create a Todo with an explicit marked state.
     *
     * @param task the task description
     * @param isMarked whether the task is marked as done
     */
    private Todo(String task, boolean isMarked) {
        super(task, isMarked);
    }

    /**
     * Returns a new {@code Task} representing this todo marked as done.
     *
     * @return a new {@code Todo} instance with the marked state set to {@code true}
     */
    public Task mark() {
        return new Todo(this.task, true);
    }

    /**
     * Returns a new {@code Task} representing this todo unmarked (not done).
     *
     * @return a new {@code Todo} instance with the marked state set to {@code false}
     */
    public Task unmark() {
        return new Todo(this.task, false);
    }

    @Override
    public String type() {
        return "todo";
    }

    /**
     * Compares this todo to another object for equality.
     * <p>
     * Two todos are equal when they are both {@code Todo} instances and have the
     * same description and marked state.
     *
     * @param other the object to compare with
     * @return {@code true} if the other object is equal to this todo, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (other instanceof Todo todo) {
            return todo.task.equals(this.task) && this.isMarked == todo.isMarked;
        }

        return false;
    }

    /**
     * Returns a string representation of this todo suitable for display.
     *
     * @return a display string beginning with the task type indicator
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
