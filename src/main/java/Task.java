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

    public abstract Task mark();
    public abstract Task unmark();

    public String toString() {
        return (isMarked ? "[X] " : "[ ] ") + this.task;
    }
}
