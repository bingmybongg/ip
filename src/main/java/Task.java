import java.util.ArrayList;

public abstract class Task {
    protected final String task;
    protected final Boolean isMarked;

    Task(String task) {
        if (task.isBlank()) {
            throw new RuntimeException();
        }
        this.task = task;
        this.isMarked = false;
    }

    protected Task(String task, Boolean isMarked) {
        if (task.isBlank()) {
            throw new RuntimeException();
        }
        this.task = task;
        this.isMarked = isMarked;
    }

    public String getMark() { return this.isMarked ? "1" : "0"; }

    public String getTask() { return this.task; }

    public abstract Task mark();
    public abstract Task unmark();
    public abstract String type();

    public String toString() {
        return (isMarked ? "[X] " : "[ ] ") + this.task;
    }
}
