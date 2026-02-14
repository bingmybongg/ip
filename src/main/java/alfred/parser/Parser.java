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

    private String convertDateTime(String dateTime) {
        DateTimeFormatter presentable = DateTimeFormatter.ofPattern("d MMM yyyy h:mma");
        DateTimeFormatter readableDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, readableDate);
        return parsedDateTime.format(presentable);
    }

    private boolean isAddCommand(String command) {
        return command.equals("todo") || command.equals("deadline") || command.equals("event");
    }

    private boolean isToDo(String todo) {
        return todo.equals("todo");
    }

    private boolean isDeadline(String deadline) {
        return deadline.equals("deadline");
    }

    private Command processToDoCommand(List<String> task) {
        assert task.get(0).equals("todo");

        String todo = String.join(" ", task.subList(1, task.size()));

        if (todo.isBlank()) {
            return new ErrorCommand(this.ui.getMissingTaskErrorMsg());
        }

        return new AddCommand(new Todo(todo));
    }

    private Command processDeadlineCommand(List<String> task) {
        assert task.get(0).equals("deadline");

        try {
            int i = task.indexOf("/by");
            if (i < 0 || task.size() - 1 == i) {
                return new ErrorCommand(this.ui.getDefaultDeadlineErrorMsg());
            }

            String rawDeadline = String.join(" ", task.subList(i + 1, task.size()));
            String readableDeadline = convertDateTime(rawDeadline);

            String deadlineTask = String.join(" ", task.subList(1, i));

            if (deadlineTask.isBlank()) {
                return new ErrorCommand(this.ui.getMissingTaskErrorMsg());
            }

            return new AddCommand(new Deadline(deadlineTask, readableDeadline));
        } catch (DateTimeException d) {
            return new ErrorCommand(this.ui.getUnreadableDateErrorMsg());
        }
    }

    private Command processEventCommand(List<String> task) {
        assert task.get(0).equals("event");

        try {
            int indexFrom = task.indexOf("/from");
            int indexTo = task.indexOf("/to");

            if (indexFrom + 1 == indexTo ||
                    indexTo == task.size() - 1 ||
                    indexTo < indexFrom ||
                    indexFrom < 0) {
                return new ErrorCommand(this.ui.getDefaultEventErrorMsg());
            }
            String eventTask = String.join(" ", task.subList(1, indexFrom));
            String rawFrom = String.join(" ", task.subList(indexFrom + 1, indexTo));
            String rawTo = String.join(" ", task.subList(indexTo + 1, task.size()));

            String readableFrom = convertDateTime(rawFrom);
            String readableTo = convertDateTime(rawTo);

            if (eventTask.isBlank()) {
                return new ErrorCommand(this.ui.getMissingTaskErrorMsg());
            }

            return new AddCommand(new Event(eventTask, readableFrom, readableTo));
        } catch (DateTimeException d) {
            return new ErrorCommand(this.ui.getUnreadableDateErrorMsg());
        }
    }

    private Command processAddCommand(List<String> task) {
        assert task.get(0).equals("todo") || task.get(0).equals("deadline") || task.get(0).equals("event");

        if (isToDo(task.get(0))) {
            return processToDoCommand(task);
        }

        if (isDeadline(task.get(0))) {
            return processDeadlineCommand(task);
        }

        return processEventCommand(task);
    }

    private boolean isListCommand(String command) {
        return command.equals("list");
    }

    private Command processListCommand(List<String> task) {
        if (task.size() > 1) {
            return new ErrorCommand(this.ui.getDefaultErrorMsg());
        }

        return new ListCommand();
    }

    private Command processHelpCommand(List<String> task) {
        if (task.size() > 1) {
            return new ErrorCommand(this.ui.getDefaultErrorMsg());
        }

        return new HelpCommand();
    }

    private boolean isOutputCommand(String command) {
        return command.equals("list") || command.equals("help") || command.equals("find");
    }

    private boolean isFindCommand(String command) {
        return command.equals("find");
    }

    private Command processFindCommand(List<String> task) {
        assert task.get(0).equals("find");
        if (task.size() != 2) {
            return new ErrorCommand(this.ui.getDefaultFindErrorMsg());
        }

        return new FindCommand(task.get(1));
    }

    private Command processOutputCommand(List<String> task) {
        assert task.get(0).equals("list") || task.get(0).equals("help") || task.get(0).equals("find");

        if (isListCommand(task.get(0))) {
            return processListCommand(task);
        }

        if (isFindCommand(task.get(0))) {
            return processFindCommand(task);
        }

        return processHelpCommand(task);
    }

    private boolean isMarkCommand(String command) {
        return command.equals("mark");
    }

    private Command processMarkCommand(List<String> task, TaskList tasks) {
        if (task.size() != 2) {
            return new ErrorCommand(this.ui.getDefaultMarkErrorMsg());
        }

        try {
            Task markTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
            return new MarkCommand(markTask);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return new ErrorCommand(this.ui.getDefaultMarkErrorMsg());
        }
    }

    private boolean isUnmarkCommand(String command) {
        return command.equals("unmark");
    }

    private Command processUnmarkCommand(List<String> task, TaskList tasks) {
        if (task.size() != 2) {
            return new ErrorCommand(this.ui.getDefaultUnmarkErrorMsg());
        }

        try {
            Task unmarkTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
            return new UnmarkCommand(unmarkTask);
        }
        catch (NumberFormatException | IndexOutOfBoundsException e) {
            return new ErrorCommand(this.ui.getDefaultUnmarkErrorMsg());
        }
    }

    private boolean isMarkingCommand(String command) {
        return command.equals("mark") || command.equals("unmark");
    }

    private Command processMarkingCommand(List<String> task, TaskList tasks) {
        assert task.get(0).equals("mark") || task.get(0).equals("unmark");

        if (isMarkCommand(task.get(0))) {
            return processMarkCommand(task, tasks);
        }

        return processUnmarkCommand(task, tasks);
    }

    private boolean isDeleteCommand(String command) {
        return command.equals("delete");
    }

    private Command processDeleteCommand(List<String> task, TaskList tasks) {
        if (task.size() != 2) {
            return new ErrorCommand(this.ui.getDefaultDeleteErrorMsg());
        }

        try {
            Task deleteTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
            return new DeleteCommand(deleteTask);
        }
        catch (NumberFormatException | IndexOutOfBoundsException e) {
            return new ErrorCommand(this.ui.getDefaultDeleteErrorMsg());
        }
    }

    private boolean isExitCommand(String command) {
        return command.equals("bye");
    }

    private Command processExitCommand(List<String> task) {
        if (task.size() != 1) {
            return new ErrorCommand(this.ui.getDefaultErrorMsg()); }

        return new ExitCommand();
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

        if (isAddCommand(task.get(0))) {
            return processAddCommand(task);
        }

        if (isOutputCommand(task.get(0))) {
            return processOutputCommand(task);
        }

        if (isMarkingCommand(task.get(0))) {
            return processMarkingCommand(task, tasks);
        }

        if (isDeleteCommand(task.get(0))) {
            return processDeleteCommand(task, tasks);
        }

        if (isExitCommand(task.get(0))) {
            return processExitCommand(task);
        }

        return new ErrorCommand(this.ui.getDefaultErrorMsg());
    }
}
