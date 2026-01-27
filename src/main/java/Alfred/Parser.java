package Alfred;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public interface Parser {
    /**
     * This method will take in a String input and return pair of values so
     * that the Ui could easily understand what the user wants to do
     * @param input containing the instruction and required inputs
     * @param tasks for methods like mark, delete
     * @return Pair, first value being the instruction, second value being the task you want to use it on
     */
    static Pair<String, Task> parse(String input, TaskList tasks) {
        String defaultError = "I'm not sure what you're saying sir\n";

        if (input.isBlank()) {
            return new Pair<>(defaultError, null);
        }

        List<String> task = Arrays.asList(input.split(" "));
        DateTimeFormatter presentable = DateTimeFormatter.ofPattern("d MMM yyyy h:mma");
        DateTimeFormatter readableDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        switch (task.get(0)) {
        case ("todo"): {
            String todo = String.join(" ", task.subList(1, task.size()));

            if (todo.isBlank()) { return new Pair<>("You're missing your task Sir\n", null); }

            return new Pair<>("add", new Todo(todo));
        }
        case ("deadline"): {
            try {
                int i = task.indexOf("/by");
                if (i < 0 || task.size() - 1 == i) {
                    return new Pair<>("""
                                I didn't get your deadline Sir
                                (Eg: deadline CLEAN THE BATMOBILE /by yyyy-MM-dd HHmm)
                                """, null);
                }

                String deadline = String.join(" ", task.subList(i + 1, task.size()));

                LocalDateTime deadlineAccurate = LocalDateTime.parse(deadline, readableDate);

                deadline = deadlineAccurate.format(presentable);

                String deadlineTask = String.join(" ", task.subList(1, i));

                if (deadlineTask.isBlank()) { return new Pair<>("You're missing your task Sir\n", null); }

                return new Pair<>("add", new Deadline(deadlineTask, deadline));
            }
            catch (DateTimeException d) {
                return new Pair<>("""
                                  I can't read your deadline Sir
                                  (Eg: 1999-02-26 1801)
                                  """, null);
            }
        }
        case ("event"): {
            try {
                int indexFrom = task.indexOf("/from");
                int indexTo = task.indexOf("/to");

                if (indexFrom + 1 == indexTo ||
                    indexTo == task.size() - 1 ||
                    indexTo < indexFrom ||
                    indexFrom < 0) {
                    return new Pair<>("""
                                I didn't get your event timing Sir
                                (Eg: event CLEAN THE BATMOBILE /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm)
                                """, null);
                }
                String eventTask = String.join(" ", task.subList(1, indexFrom));
                String from = String.join(" ", task.subList(indexFrom + 1, indexTo));
                String to = String.join(" ", task.subList(indexTo + 1, task.size()));

                LocalDateTime accurateFrom = LocalDateTime.parse(from, readableDate);

                LocalDateTime accurateTo = LocalDateTime.parse(to, readableDate);

                from = accurateFrom.format(presentable);
                to = accurateTo.format(presentable);

                if (eventTask.isBlank()) {
                    return new Pair<>("You're missing your task Sir\n", null);
                }

                return new Pair<>("add", new Event(eventTask, from, to));
            }
            catch (DateTimeException d) {
                return new Pair<>("""
                                I can't read your deadline Sir
                                (Eg: 1999-02-26 1801)
                                """, null);
            }
        }
        case ("list"): {
            if (task.size() > 1) { return new Pair<>(defaultError, null); }

            return new Pair<>("read", null);
        }
        case ("mark"): {
            String markError = """
                                 Check the list to choose which task to mark Sir
                                 (Eg: mark #)
                                 """;

            if (task.size() != 2) { return new Pair<>(markError, null); }

            try {
                Task markTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
                return new Pair<>("mark", markTask);
            }
            catch (NumberFormatException | IndexOutOfBoundsException e) {
                return new Pair<>(markError, null);
            }
        }
        case ("unmark"): {
            String unmarkError = """
                                 Check the list to choose which task to unmark Sir
                                 (Eg: unmark #)
                                 """;

            if (task.size() != 2) { return new Pair<>(unmarkError, null); }

            try {
                Task unmarkTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
                return new Pair<>("unmark",unmarkTask);
            }
            catch (NumberFormatException | IndexOutOfBoundsException e) {
                return new Pair<>(unmarkError, null);
            }
        }
        case ("delete"): {
            String deleteError = """
                            Check the list to choose which task to delete Sir
                            (Eg: delete #)
                            """;
            if (task.size() != 2) { return new Pair<>(deleteError, null); }

            try {
                Task deleteTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
                return new Pair<>("delete", deleteTask);
            }
            catch (NumberFormatException | IndexOutOfBoundsException e) {
                return new Pair<>(deleteError, null);
            }
        }
        case ("find"): {
            String findError = """
                    You need to tell me a single keyword you want to find Sir
                    (Eg: find batmobile)
                    """;
            if (task.size() != 2) { return new Pair<>(findError, null); }
            return new Pair<>("find", new Todo(task.get(1)));
        }
        case ("bye"): {
            if (task.size() != 1) { return new Pair<>(defaultError, null); }

            return new Pair<>("exit", null);
        }

        default: { return new Pair<>(defaultError, null); }
        }
    }
}
