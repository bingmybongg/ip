package alfred.parser;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import alfred.command.AddCommand;
import alfred.command.Command;
import alfred.command.DeleteCommand;
import alfred.command.ErrorCommand;
import alfred.command.ExitCommand;
import alfred.command.FindCommand;
import alfred.command.HelpCommand;
import alfred.command.ListCommand;
import alfred.command.MarkCommand;
import alfred.command.UnmarkCommand;
import alfred.task.Deadline;
import alfred.task.Event;
import alfred.task.Task;
import alfred.task.TaskList;
import alfred.task.Todo;
import alfred.ui.Ui;

public class Parser {
    private final Ui ui;
    
    public Parser(Ui ui) {
        this.ui = ui;
    }
    
    /**
     * This method will take in a String input and return pair of values so
     * that the Ui could easily understand what the user wants to do
     * @param input containing the instruction and required inputs
     * @param tasks for methods like mark, delete
     * @return Pair, first value being the instruction, second value being the task you want to use it on
     */
    
    
    public Command parse(String input, TaskList tasks) {

        if (input.isBlank()) {
            return new ErrorCommand(this.ui.getDefaultErrorMsg());
        }

        List<String> task = Arrays.asList(input.split(" "));
        DateTimeFormatter presentable = DateTimeFormatter.ofPattern("d MMM yyyy h:mma");
        DateTimeFormatter readableDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        switch (task.get(0)) {
        case ("todo"): {
            String todo = String.join(" ", task.subList(1, task.size()));

            if (todo.isBlank()) {
                return new ErrorCommand(this.ui.getMissingTaskErrorMsg());
            }

            return new AddCommand(new Todo(todo));
        }
        case ("deadline"): {
            try {
                int i = task.indexOf("/by");
                if (i < 0 || task.size() - 1 == i) {
                    return new ErrorCommand("""
                                I didn't get your deadline Sir
                                (Eg: deadline CLEAN THE BATMOBILE /by yyyy-MM-dd HHmm)
                                """);
                }

                String deadline = String.join(" ", task.subList(i + 1, task.size()));

                LocalDateTime deadlineAccurate = LocalDateTime.parse(deadline, readableDate);

                deadline = deadlineAccurate.format(presentable);

                String deadlineTask = String.join(" ", task.subList(1, i));

                if (deadlineTask.isBlank()) {
                    return new ErrorCommand(this.ui.getMissingTaskErrorMsg());
                }

                return new AddCommand(new Deadline(deadlineTask, deadline));
            }
            catch (DateTimeException d) {
                return new ErrorCommand("""
                                  I can't read your deadline Sir
                                  (Eg: 1999-02-26 1801)
                                  """);
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
                    return new ErrorCommand("""
                                I didn't get your event timing Sir
                                (Eg: event CLEAN THE BATMOBILE /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm)
                                """);
                }
                String eventTask = String.join(" ", task.subList(1, indexFrom));
                String from = String.join(" ", task.subList(indexFrom + 1, indexTo));
                String to = String.join(" ", task.subList(indexTo + 1, task.size()));

                LocalDateTime accurateFrom = LocalDateTime.parse(from, readableDate);

                LocalDateTime accurateTo = LocalDateTime.parse(to, readableDate);

                from = accurateFrom.format(presentable);
                to = accurateTo.format(presentable);

                if (eventTask.isBlank()) {
                    return new ErrorCommand(this.ui.getMissingTaskErrorMsg());
                }

                return new AddCommand(new Event(eventTask, from, to));
            }
            catch (DateTimeException d) {
                return new ErrorCommand("""
                                I can't read your deadline Sir
                                (Eg: 1999-02-26 1801)
                                """);
            }
        }
        case ("list"): {
            if (task.size() > 1) {
                return new ErrorCommand(this.ui.getDefaultErrorMsg());
            }

            return new ListCommand();
        }
        case ("help"): {
            if (task.size() > 1) {
                return new ErrorCommand(this.ui.getDefaultErrorMsg());
            }

            return new HelpCommand();
        }
        case ("mark"): {
            String markError = """
                                 Check the list to choose which task to mark Sir
                                 (Eg: mark #)
                                 """;

            if (task.size() != 2) {
                return new ErrorCommand(markError);
            }

            try {
                Task markTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
                return new MarkCommand(markTask);
            }
            catch (NumberFormatException | IndexOutOfBoundsException e) {
                return new ErrorCommand(markError);
            }
        }
        case ("unmark"): {
            String unmarkError = """
                                 Check the list to choose which task to unmark Sir
                                 (Eg: unmark #)
                                 """;

            if (task.size() != 2) {
                return new ErrorCommand(unmarkError);
            }

            try {
                Task unmarkTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
                return new UnmarkCommand(unmarkTask);
            }
            catch (NumberFormatException | IndexOutOfBoundsException e) {
                return new ErrorCommand(unmarkError);
            }
        }
        case ("delete"): {
            String deleteError = """
                            Check the list to choose which task to delete Sir
                            (Eg: delete #)
                            """;
            if (task.size() != 2) {
                return new ErrorCommand(deleteError);
            }

            try {
                Task deleteTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
                return new DeleteCommand(deleteTask);
            }
            catch (NumberFormatException | IndexOutOfBoundsException e) {
                return new ErrorCommand(deleteError);
            }
        }
        case ("find"): {
            String findError = """
                    You need to tell me a single keyword you want to find Sir
                    (Eg: find batmobile)
                    """;
            if (task.size() != 2) {
                return new ErrorCommand(findError);
            }

            return new FindCommand(task.get(1));
        }
        case ("bye"): {
            if (task.size() != 1) {
                return new ErrorCommand(this.ui.getDefaultErrorMsg()); }

            return new ExitCommand();
        }

        default: { 
            return new ErrorCommand(this.ui.getDefaultErrorMsg()); 
        }
        }
    }
}
