public class Todo extends Task {
    Todo(String task) {
        super(task);
    }

    private Todo(String task, boolean isMarked) {
        super(task, isMarked);
    }

    public Task mark() {
        return new Todo(this.task, true);
    }

    public Task unmark() { return new Todo(this.task, false); }

    public String type() { return "todo"; }

    public String toString() {
        return "[T]" + super.toString();
    }
}
