package Alfred;

import java.io.IOException;
import java.util.ArrayList;

public class TaskList {
    private final FileManager fm;
    private ArrayList<Task> tasks;

    TaskList(String path) throws IOException {
        this.fm = new FileManager(path);
        this.tasks = this.fm.load();
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public void delete(Task task) {
        this.tasks.remove(task);
    }

    public void mark(Task task) {
        this.tasks.set(this.tasks.indexOf(task), task.mark());
    }

    public void unmark(Task task) {
        this.tasks.set(this.tasks.indexOf(task), task.unmark());
    }

    public void read() {
        System.out.println("Here's your list Sir");
        tasks.forEach(x -> System.out.println(tasks.indexOf(x) + 1 + ". " + x));

        System.out.print("\n");
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
}
