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

    public String type() {
        return "event";
    }

    public String getFrom() { return this.from; }

    public String getTo() { return this.to; }

    public Task mark() {
        return new Event(this, true);
    }

    public Task unmark() {
        return new Event(this, false);
    }

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
