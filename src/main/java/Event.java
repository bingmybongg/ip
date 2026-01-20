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

    public Task mark() {
        return new Event(this, true);
    }

    public Task unmark() {
        return new Event(this, false);
    }

    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
