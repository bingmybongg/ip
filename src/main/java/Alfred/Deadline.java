package Alfred;

public class Deadline extends Task {
    private final String deadline;

    Deadline(String task, String deadline) {
        super(task);
        this.deadline = deadline;
    }

    private Deadline(Deadline curr, boolean isMarked) {
        super(curr.task, isMarked);
        this.deadline = curr.deadline;
    }

    /**
     * This method returns the task type
     * @return The task type meant for saving the tasks for the user.
     */
    public String type() { return "deadline"; }

    /**
     * This method returns the deadline of this task
     * @return The deadline of this task
     */
    public String getDeadline() { return this.deadline; }

    /**
     * This method marks the task
     * @return The task that has just been marked
     */
    public Task mark() { return new Deadline(this, true); }

    /**
     * This method unmarks the task
     * @return The task that has just been unmarked
     */
    public Task unmark() {
        return new Deadline(this, false);
    }

    /**
     * This method checks if the object passed into to this method is equal to this task
     * @param other object
     * @return True if the object is equal to this or False if object is not equal
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof Deadline otherDeadline) {
            return (otherDeadline.task.equals(this.task)) &&
                   (otherDeadline.deadline.equals(this.deadline)) ;
        }

        return false;
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }
}
