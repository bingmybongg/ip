package alfred.task;

/**
 * Represents an event task that occurs during a specific time period.
 * <p>
 * An event has a start time ({@code from}) and end time ({@code to}) in addition
 * to the task description and marked state inherited from {@link Task}.
 * Instances are immutable - marking or unmarking returns a new event instance.
 */
public final class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates a new unmarked event with the given description and time range.
     *
     * @param task the event description (must not be null or blank)
     * @param from the start date/time (must not be null or blank)
     * @param to the end date/time (must not be null or blank)
     * @throws NullPointerException if task, from, or to is null
     * @throws IllegalArgumentException if task, from, or to is blank
     */
    public Event(String task, String from, String to) {
        super(task);
        if (from == null) {
            throw new NullPointerException("From date/time cannot be null");
        }
        if (from.isBlank()) {
            throw new IllegalArgumentException("From date/time cannot be blank");
        }
        if (to == null) {
            throw new NullPointerException("To date/time cannot be null");
        }
        if (to.isBlank()) {
            throw new IllegalArgumentException("To date/time cannot be blank");
        }
        this.from = from;
        this.to = to;
    }

    /**
     * Internal constructor used to create an event with an explicit marked state.
     * <p>
     * This is typically called by {@link #mark()} and {@link #unmark()} to create
     * new instances while preserving immutability. All fields from the current
     * event are copied except for the marked state.
     *
     * @param curr the current event to copy fields from
     * @param isMarked whether the event is marked as done
     */
    private Event(Event curr, boolean isMarked) {
        super(curr.task, isMarked);

        this.from = curr.from;
        this.to = curr.to;
    }

    /**
     * Returns the storage type identifier for this task.
     *
     * @return the string {@code "event"} used when saving/loading tasks
     */
    @Override
    public String type() {
        return "event";
    }

    /**
     * Returns the start date/time of this event.
     *
     * @return the start date/time string
     */
    public String getFrom() {
        return this.from;
    }

    /**
     * Returns the end date/time of this event.
     *
     * @return the end date/time string
     */
    public String getTo() {
        return this.to;
    }

    /**
     * Returns a new {@code Event} representing this event marked as done.
     * <p>
     * This method does not modify the current event instance. Instead, it creates
     * and returns a new {@code Event} with the same description and time range but
     * with the marked state set to {@code true}.
     *
     * @return a new {@code Event} instance with the marked state set to {@code true}
     */
    @Override
    public Task mark() {
        return new Event(this, true);
    }

    /**
     * Returns a new {@code Event} representing this event unmarked (not done).
     * <p>
     * This method does not modify the current event instance. Instead, it creates
     * and returns a new {@code Event} with the same description and time range but
     * with the marked state set to {@code false}.
     *
     * @return a new {@code Event} instance with the marked state set to {@code false}
     */
    @Override
    public Task unmark() {
        return new Event(this, false);
    }

    /**
     * Compares this event to another object for equality.
     * <p>
     * Two events are equal when they are both {@code Event} instances and have the
     * same description, time range, and marked state.
     *
     * @param other the object to compare with
     * @return {@code true} if the other object is equal to this event, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof Event event) {
            return (event.task.equals(this.task)) &&
                   (event.from.equals(this.from)) &&
                   (event.to.equals(this.to)) &&
                    this.isMarked == event.isMarked;
        }

        return false;
    }

    /**
     * Returns a string representation of this event suitable for display.
     * <p>
     * The format includes a checkbox indicator showing completion status, the event
     * description, and the time range in the format: {@code "[E][X] description (from: start to: end)"}.
     *
     * @return a display string with completion status, description, and time range
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + "\n to: " + this.to + ")";
    }
}
