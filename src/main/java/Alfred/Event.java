package Alfred;

public class Event extends Task {
    private final String from;
    private final String to;

    Event(String task, String from, String to) {
        super(task);
        this.from = from;
        this.to = to;
    }

    private Event(Event curr, boolean isMarked) {
        super(curr.task, isMarked);
        this.from = curr.from;
        this.to = curr.to;
    }

    /**
     * This method returns the task type
     * @return The task type meant for saving the tasks for the user.
     */
    public String type() {
        return "event";
    }
    /**
     * This method returns the date and time for from
     * @return The date and time for from
     */
    public String getFrom() { return this.from; }
    /**
     * This method returns the date and time for to
     * @return The date and time for to
     */
    public String getTo() { return this.to; }
    /**
     * This method marks the task
     * @return The task that has just been marked
     */
    public Task mark() {
        return new Event(this, true);
    }
    /**
     * This method unmarks the task
     * @return The task that has just been unmarked
     */
    public Task unmark() {
        return new Event(this, false);
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

        if (other instanceof Event event) {
            return (event.task.equals(this.task)) &&
                   (event.from.equals(this.from)) &&
                   (event.to.equals(this.to));
        }

        return false;
    }

    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
