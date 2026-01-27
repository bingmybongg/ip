package Alfred;

import java.io.IOException;
import java.util.ArrayList;

public class TaskList {
    private final FileManager fm;
    private ArrayList<Task> tasks;

    public TaskList(String path) throws IOException {
        this.fm = new FileManager(path);
        this.tasks = this.fm.load();
    }

    /**
     * This method adds the task into the whole list of tasks
     * @param task
     */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /**
     * This method deletes the task from the list of tasks
     * @param task
     */
    public void delete(Task task) {
        this.tasks.remove(task);
    }
    /**
     * This method marks the task
     * @param task
     * @return task that has been marked
     */
    public Task mark(Task task) {
        Task newTask = task.mark();
        this.tasks.set(this.tasks.indexOf(task), newTask);
        return newTask;
    }
    /**
     * This method unmarks the task
     * @param task
     * @return task that has been unmarked
     */
    public Task unmark(Task task) {
        Task newTask = task.unmark();
        this.tasks.set(this.tasks.indexOf(task), newTask);
        return newTask;
    }

    /**
     * This method gets the task of index i
     * @param i
     * @return the task that is in that index
     */
    public Task get(int i) {
        return this.tasks.get(i);
    }

    /**
     * This method prints the error faced when a user inputs an unknown command
     * @param error
     */
    public void unknown(String error) {
        System.out.println(error);
    }

    /**
     * This method saves the tasks to a file for the next time the program runs
     */
    public void save() throws IOException {
        fm.save(this.tasks);
    }

    /**
     * This method returns the number of tasks
     * @return number of tasks
     */
    public int size() { return this.tasks.size(); }

    public String toString() {
        String res = "Here's your list Sir\n";

        for (Task task : tasks) {
            res += (tasks.indexOf(task) + 1) + ". " + task + "\n";
        }

        res += "\n";

        return res;
    }
}
