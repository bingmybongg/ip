package alfred.task;

import java.io.IOException;
import java.util.ArrayList;

import alfred.storage.FileManager;

public class TaskList {
    private final FileManager fm;
    private ArrayList<Task> tasks;

    public TaskList(String path) throws IOException {
        this.fm = new FileManager(path);
        this.tasks = this.fm.load();
    }

    private TaskList(FileManager fm, ArrayList<Task> tasks) {
        this.fm = fm;
        this.tasks = tasks;
    }

    /**
     * This method adds the task into the whole list of tasks
     * @param task
     */
    public void add(Task task) {
        int i = this.tasks.size();
        this.tasks.add(task);
        assert this.tasks.size() == i + 1 : "The task list should increase by 1 only at this point";
    }

    /**
     * This method deletes the task from the list of tasks
     * @param task
     */
    public void delete(Task task) {
        int i = this.tasks.size();
        this.tasks.remove(task);
        assert this.tasks.size() == i - 1 : "The task list should decrease by 1 only at this point";

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
     * This method saves the tasks to a file for the next time the program runs
     */
    public void save() throws IOException {
        fm.save(this.tasks);
    }

    /**
     * This method returns the number of tasks
     * @return number of tasks
     */
    public int size() {
        return this.tasks.size();
    }

    public TaskList find(String keyword) {
        ArrayList<Task> newList = new ArrayList<>();

        for (Task task : this.tasks) {
            if (task.contains(keyword)) {
                newList.add(task);
            }
        }
        return new TaskList(this.fm, newList);
    }

    @Override
    public String toString() {
        String res = "";
        int i = 1;

        for (Task task : tasks) {
            res += i + ". " + task + "\n";
            i++;
        }

        return res;
    }
}
