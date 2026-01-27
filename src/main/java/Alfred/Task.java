package Alfred;

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
    /**
     * This method checks if the task is marked, mainly meant for saving the
     * leftover tasks for the next time the program runs
     * @return "1" if the task is marked else "0"
     */
    public String getMark() { return this.isMarked ? "1" : "0"; }
    /**
     * This method returns the task
     * @return the task
     */
    public String getTask() { return this.task; }

    public abstract Task mark();
    public abstract Task unmark();
    public abstract String type();
    public abstract boolean equals(Object other);

    public String toString() {
        return (isMarked ? "[X] " : "[ ] ") + this.task;
    }
}
