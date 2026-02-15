package alfred.task;

import java.io.IOException;
import java.util.ArrayList;

import alfred.storage.FileManager;

/**
 * Manages the collection of {@link Task} instances backed by a {@link FileManager}.
 * <p>
 * The list delegates loading and saving to the provided {@link FileManager}. Instances
 * expose mutation operations (add, delete, mark, unmark) and simple query operations
 * (get, size, find). Methods that persist data will propagate {@link IOException}.
 * <p>
 * This class is NOT thread-safe. External synchronization is required if
 * multiple threads access a TaskList instance concurrently.
 */
public class TaskList {
    private final FileManager fm;
    private final ArrayList<Task> tasks;

    /**
     * Creates a new {@code TaskList} by loading tasks from the given storage path.
     *
     * @param path the filesystem path used by {@link FileManager} to load tasks
     * @throws IOException if an I/O error occurs while loading tasks
     */
    public TaskList(String path) throws IOException {
        this.fm = new FileManager(path);
        this.tasks = this.fm.load();
    }

    /**
     * Internal constructor used to create a read-only view of tasks without file persistence.
     * <p>
     * This constructor is typically used by {@link #find(String)} to create filtered task lists
     * that represent search results. Since these filtered views are not backed by a file,
     * calling {@link #save()} on instances created with this constructor will result in a
     * {@link NullPointerException}.
     *
     * @param tasks the list of tasks to use as the backing collection (must not be {@code null})
     * @throws NullPointerException if {@code tasks} is {@code null}
     */
    private TaskList(ArrayList<Task> tasks) {
        this.fm = null;
        this.tasks = tasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        assert task != null : "Task should not be null.";

        this.tasks.add(task);
    }

    /**
     * Removes the first occurrence of the specified task from the list.
     *
     * @param task the task to remove
     */
    public void delete(Task task) {
        boolean isRemoved = this.tasks.remove(task);
        assert isRemoved : "Task should always exist in the list.";
    }

    /**
     * Marks the given task as done and replaces the stored instance with the marked one.
     *
     * @param task the task to mark
     * @return the new task instance that represents the marked task
     */
    public Task mark(Task task) {
        assert task != null : "Task should not be null.";
        assert this.tasks.contains(task) : "Task should always exist in the list.";

        Task newTask = task.mark();
        this.tasks.set(this.tasks.indexOf(task), newTask);
        return newTask;
    }

    /**
     * Unmarks the given task (sets it to not done) and replaces the stored instance.
     *
     * @param task the task to unmark
     * @return the new task instance that represents the unmarked task
     */
    public Task unmark(Task task) {
        assert task != null : "Task should not be null.";
        assert this.tasks.contains(task) : "Task should always exist in the list.";

        Task newTask = task.unmark();
        this.tasks.set(this.tasks.indexOf(task), newTask);
        return newTask;
    }

    /**
     * Returns the task at the specified index.
     *
     * @param i the index of the task to return (0-based)
     * @return the task at the specified position
     */
    public Task get(int i) {
        assert i >= 0 && i < this.tasks.size() : "Index should be within the bounds of the task list.";
        return this.tasks.get(i);
    }

    /**
     * Persists the current task list using the configured {@link FileManager}.
     *
     * @throws IOException if an I/O error occurs while saving
     */
    public void save() throws IOException {
        fm.save(this.tasks);
    }

    /**
     * Returns the number of tasks currently stored.
     *
     * @return the number of tasks
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Returns a new {@code TaskList} containing tasks whose descriptions contain the
     * specified keyword.
     *
     * @param keyword the substring to search for within task descriptions
     * @return a new {@code TaskList} containing matching tasks; never {@code null}
     */
    public TaskList find(String keyword) {
        ArrayList<Task> newList = new ArrayList<>();

        for (Task task : this.tasks) {
            if (task.contains(keyword)) {
                newList.add(task);
            }
        }
        return new TaskList(newList);
    }

    /**
     * Returns a human-readable listing of all tasks, one per line, prefixed with
     * their 1-based position in the list.
     *
     * @return the formatted list of tasks
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;

        for (Task task : tasks) {
            sb.append(i)
              .append(". ")
              .append(task)
              .append("\n");
            i++;
        }

        return sb.toString();
    }
}