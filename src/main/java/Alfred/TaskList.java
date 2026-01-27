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

    private TaskList(FileManager fm, ArrayList<Task> tasks) {
        this.fm = fm;
        this.tasks = tasks;
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public void delete(Task task) {
        this.tasks.remove(task);
    }

    public Task mark(Task task) {
        Task newTask = task.mark();
        this.tasks.set(this.tasks.indexOf(task), newTask);
        return newTask;
    }

    public Task unmark(Task task) {
        Task newTask = task.unmark();
        this.tasks.set(this.tasks.indexOf(task), newTask);
        return newTask;
    }

    public Task get(int i) {
        return this.tasks.get(i);
    }

    public void unknown(String error) {
        System.out.println(error);
    }

    public void save() throws IOException {
        fm.save(this.tasks);
    }

    public int size() { return this.tasks.size(); }

    public TaskList find(String keyword) {
        ArrayList<Task> newList = new ArrayList<>();

        for (Task task : this.tasks) {
            if (task.contains(keyword)) {
                newList.add(task);
            }
        }
        return new TaskList(this.fm, newList);
    }

    public String toString() {
        String res = "";

        for (Task task : tasks) {
            res += (tasks.indexOf(task) + 1) + ". " + task + "\n";
        }

        res += "\n";

        return res;
    }
}
