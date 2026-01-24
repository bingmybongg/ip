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

    public String type() { return "deadline"; }

    public String getDeadline() { return this.deadline; }

    public Task mark() { return new Deadline(this, true); }

    public Task unmark() {
        return new Deadline(this, false);
    }

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
