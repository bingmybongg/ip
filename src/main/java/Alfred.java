import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;


public class Alfred {
    private static boolean addTask(ArrayList<Task> list, List<String> taskList, String type) {
        try {
            DateTimeFormatter presentable = DateTimeFormatter.ofPattern("d MMM yyyy h:mma");

            switch (type) {
                case "todo": {
                    System.out.println("Adding " + type.toUpperCase() + " task for you Sir\n");
                    if (taskList.isEmpty()) {
                        throw new RuntimeException(type.toUpperCase());
                    }
                    String task = String.join(" ", taskList);
                    Task todo = new Todo(task);
                    list.add(todo);
                    return true;
                }
                case "deadline": {
                    System.out.println("Adding " + type.toUpperCase() + " task for you Sir\n");
                    if (taskList.isEmpty()) {
                        throw new RuntimeException();
                    }
                    try {
                        int i = taskList.indexOf("/by");

                        if (i < 0 || taskList.size() - 1 == i) {
                            throw new IllegalArgumentException();
                        }

                        String deadline = String.join(" ",
                                taskList.subList(i + 1, taskList.size()));

                        LocalDateTime deadlineAccurate = LocalDateTime.parse(deadline,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

                        deadline = deadlineAccurate.format(presentable);

                        String task = String.join(" ",
                                taskList.subList(0, i));

                        list.add(new Deadline(task, deadline));
                        return true;
                    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                        System.out.println("I didn't get your deadline Sir");
                        System.out.println("(Eg: deadline CLEAN THE BATMOBILE /by yyyy-MM-dd HHmm)\n");
                        return false;
                    }
                }
                case "event": {
                    System.out.println("Adding " + type.toUpperCase() + " task for you Sir\n");
                    if (taskList.isEmpty()) {
                        throw new RuntimeException();
                    }
                    try {
                        int indexFrom = taskList.indexOf("/from");
                        int indexTo = taskList.indexOf("/to");

                        if (indexFrom + 1 == indexTo || indexTo == taskList.size() - 1) {
                            throw new IndexOutOfBoundsException();
                        }
                        String task = String.join(" ", taskList.subList(0, indexFrom));
                        String from = String.join(" ", taskList.subList(indexFrom + 1, indexTo));
                        String to = String.join(" ", taskList.subList(indexTo + 1, taskList.size()));

                        LocalDateTime deadlineAccurateFrom = LocalDateTime.parse(from,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

                        LocalDateTime deadlineAccurateTo = LocalDateTime.parse(to,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

                        from = deadlineAccurateFrom.format(presentable);
                        to = deadlineAccurateTo.format(presentable);

                        Task event = new Event(task, from, to);
                        list.add(event);
                        return true;
                    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                        System.out.println("I didn't get your event timing Sir\n" +
                                "(Eg: event CLEAN THE BATMOBILE /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm)\n");
                        return false;
                    }
                }
                default: {
                    System.out.println("I don't quite get what you're saying Sir\n");
                    return false;
                }
            }
        }
        catch (DateTimeException d) {
            System.out.println("Remember that your time must be in yyyy-MM-dd HHmm format Sir:\n" +
                    "(Eg: 1999-02-26 1801)\n");
            return false;
        }
        catch (RuntimeException e) {
            System.out.println("You're missing your task Sir\n");
            return false;
        }
    }

    private static void printList(ArrayList<Task> list) {
        System.out.println("Here's your list Sir");
        list.forEach(x -> System.out.println(list.indexOf(x) + 1 + ". " + x));

        System.out.print("\n");

    }

    private static void loadFile(Path file, ArrayList<Task> list) throws IOException {
        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                List<String> lineList = Arrays.asList(line.split(","));

                switch (lineList.get(0)) {
                    case ("todo"): {
                        String task = lineList.get(1);
                        String isMarked = lineList.get(2);
                        Task newTask = new Todo(task);

                        if (isMarked.equals("1")) {
                            newTask = newTask.mark();
                        }

                        list.add(newTask);
                        break;
                    }
                    case ("deadline"): {
                        String task = lineList.get(1);
                        String isMarked = lineList.get(2);
                        String by = lineList.get(3);

                        Task newTask = new Deadline(task, by);

                        if (isMarked.equals("1")) {
                            newTask = newTask.mark();
                        }

                        list.add(newTask);
                        break;
                    }
                    case ("event"): {
                        String task = lineList.get(1);
                        String isMarked = lineList.get(2);
                        String from = lineList.get(3);
                        String to = lineList.get(4);

                        Task newTask = new Event(task, from, to);

                        if (isMarked.equals("1")) {
                            newTask = newTask.mark();
                        }

                        list.add(newTask);
                        break;
                    }
                    default: {
                        System.out.println("Part of your file has been corrupted\n");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("The file has been corrupted\n");
        }
    }

    private static void saveFile(ArrayList<Task> list, Path file) throws IOException {
        FileWriter fw = new FileWriter(file.toString(), true);

        for (Task task : list) {
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

    public static void main(String[] args) throws IOException {
        String initialGreeting = "Good morning Master!\nWhat do you need from me?\n";
        String endChat = "Good day Sir!\n";
        String separator = "What else do you need?\n";

        System.out.println(initialGreeting);
        ArrayList<Task> list = new ArrayList<Task>();
        Scanner reader = new Scanner(System.in);

        String path = System.getProperty("user.home") + File.separator + "data" + File.separator + "alfred.csv";
        Path file = Paths.get(path);

        Files.createDirectories(Paths.get(System.getProperty("user.home") + File.separator + "data"));

        if (Files.exists(file)) {
            loadFile(file, list);
            Files.delete(file);
            Files.createFile(file);
        } else {
            Files.createFile(file);
        }

        while (reader.hasNextLine()) {
            String readInput = reader.nextLine().trim();
            if (readInput.isBlank()) {
                System.out.println("Waiting for your command Sir\n");
                continue;
            }

            List<String> task = Arrays.asList(readInput.split(" ")); // if not in list itll be -1

            if (task.indexOf("bye") == 0) {
                reader.close();
                break;
            }

            if (task.indexOf("list") == 0) {
                printList(list);
            } else if (task.indexOf("mark") == 0 || task.indexOf("unmark") == 0) {
                try {
                    if (task.size() != 2) {
                        throw new RuntimeException(task.get(0));
                    }

                    int i = Integer.parseInt(task.get(1)) - 1;

                    switch (task.get(0)) {
                        case "mark": {
                            list.set(i, list.get(i).mark());
                            System.out.println("I have successfully marked the task for you Sir:");
                            System.out.println("   " + list.get(i) + "\n");
                            break;
                        }
                        case "unmark": {
                            list.set(i, list.get(i).unmark());
                            System.out.println("I have successfully unmarked the task for you Sir:");
                            System.out.println("   " + list.get(i) + "\n");
                            break;
                        }
                        default: {
                            throw new RuntimeException();
                        }
                    }
                }
                catch (NumberFormatException c) {
                    System.out.println("That's an invalid index Sir\n");
                }
                catch (IndexOutOfBoundsException i) {
                    System.out.println("There isn't a task with that number Sir\n");
                }
                catch (RuntimeException e) {
                    System.out.println("Please enter the task you want to " +
                                        e.getMessage() +
                                        " (Eg: " + e.getMessage() + " 1)\n");
                }
            } else if (task.indexOf("delete") == 0) {
                try {
                    int i = Integer.parseInt(task.get(1)) - 1;

                    if (task.size() != 2) {
                        throw new RuntimeException();
                    }

                    System.out.println("Deleting your task now Sir\n");

                    Task currTask = list.get(i);
                    list.remove(i);

                    System.out.println("I have successfully deleted the task for you Sir:");
                    System.out.println("   " + currTask + "\n");
                }
                catch (NumberFormatException c) {
                    System.out.println("That's an invalid index Sir\n");
                }
                catch (IndexOutOfBoundsException i) {
                    System.out.println("There isn't a task with that number Sir\n");
                }
                catch (RuntimeException e) {
                    System.out.println("Which task do you want to delete Sir?\n" +
                                       "(Eg: delete 1)\n");
                }
            }
            else {
                boolean succ = addTask(list, task.subList(1, task.size()), task.get(0));

                if (succ) {
                    int i = list.size();
                    System.out.println("I have successfully added the task for you Sir:");
                    System.out.println("   " + list.get(i - 1));
                    System.out.println("You currently have " + i + " task(s) at the moment\n");
                }
            }
            System.out.println(separator);
        }

        saveFile(list, file);
        System.out.println(endChat);
    }
}
