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
    public Task unmark() { return new Todo(this.task, false); }
    /**
     * This method returns the task type
     * @return The task type meant for saving the tasks for the user.
     */
    public String type() { return "todo"; }
    /**
     * This method checks if the object passed into to this method is equal to this task
     * @param other object
     * @return True if the object is equal to this or False if object is not equal
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof Todo todo) {
            return todo.task.equals(this.task);
        }

        return false;
    }

    public String toString() {
        return "[T]" + super.toString();
    }
}
