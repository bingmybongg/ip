package Alfred;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final Path file;

    FileManager(String path) throws IOException {
        Files.createDirectories(Paths.get(path));
        this.file = Paths.get(path + File.separator + "alfred.csv");
    }

    public ArrayList<Task> load() throws IOException {
        if (Files.exists(this.file)) {
            ArrayList<Task> tasks = new ArrayList<>();
            List<String> lines = Files.readAllLines(this.file);

            for (String line : lines) {
                String[] task = line.split(",");
                switch (task[0]) {
                    case ("todo"): {
                        Task curr = new Todo(task[1]);

                        if (task[2].equals("1")) {
                            curr = curr.mark();
                        }

                        tasks.add(curr);
                        break;
                    }
                    case ("deadline"): {
                        Task curr = new Deadline(task[1], task[3]);

                        if (task[2].equals("1")) {
                            curr = curr.mark();
                        }

                        tasks.add(curr);
                        break;
                    }
                    case ("event"): {
                        Task curr = new Event(task[1], task[3], task[4]);

                        if (task[2].equals("1")) {
                            curr = curr.mark();
                        }

                        tasks.add(curr);
                        break;
                    }
                    default:
                        break;
                }
            }
            Files.delete(file);
            return tasks;
        } else {
            return new ArrayList<>();
        }
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        Files.createFile(this.file);
        FileWriter fw = new FileWriter(this.file.toString(), true);

        for (Task task : tasks) {
            String type = task.type();

            switch (type) {
                case ("todo"): {
                    String t = type + "," +
                            task.getTask() + "," +
                            task.getMark();
                    fw.write(t + "\n");
                    break;
                }
                case ("deadline"): {
                    String t = type + "," +
                            task.getTask() + "," +
                            task.getMark() + "," +
                            ((Deadline) task).getDeadline();
                    fw.write(t + "\n");
                    break;
                }
                case ("event"): {
                    String t = type + "," +
                            task.getTask() + "," +
                            task.getMark() + "," +
                            ((Event) task).getFrom() + "," +
                            ((Event) task).getTo();
                    fw.write(t + "\n");
                    break;
                }
            }
        }

        fw.close();
    }
}
