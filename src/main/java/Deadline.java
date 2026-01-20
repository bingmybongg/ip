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

    public Task mark() {
        return new Deadline(this, true);
    }

    public Task unmark() {
        return new Deadline(this, false);
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }
}
