package Alfred;

public class Todo extends Task {
    public Todo(String task) {
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
