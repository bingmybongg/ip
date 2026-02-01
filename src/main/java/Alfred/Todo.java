package Alfred;

public class Todo extends Task {
    public Todo(String task) {
        super(task);
    }

    private Todo(String task, boolean isMarked) {
        super(task, isMarked);
    }
    /**
     * This method marks the task
     * @return The task that has just been marked
     */
    public Task mark() {
        return new Todo(this.task, true);
    }
    /**
     * This method unmarks the task
     * @return The task that has just been unmarked
     */
    public Task unmark() {
        return new Todo(this.task, false);
    }
    /**
     * This method returns the task type
     * @return The task type meant for saving the tasks for the user.
     */
    public String type() { return "todo"; }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof Todo todo) {
            return todo.task.equals(this.task) && this.isMarked.equals(todo.isMarked);
        }

        return false;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
