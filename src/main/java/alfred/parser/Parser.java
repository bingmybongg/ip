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

/**
 * Parses user input commands and converts them into executable Command objects.
 * <p>
 * The parser supports the following commands:
 * <ul>
 *   <li>{@code todo DESCRIPTION} - Creates a simple task</li>
 *   <li>{@code deadline DESCRIPTION /by DATE} - Creates a task with a deadline</li>
 *   <li>{@code event DESCRIPTION /from DATE /to DATE} - Creates a task with start and end times</li>
 *   <li>{@code list} - Lists all tasks</li>
 *   <li>{@code find KEYWORD} - Searches for tasks by keyword</li>
 *   <li>{@code mark INDEX} - Marks a task as done</li>
 *   <li>{@code unmark INDEX} - Marks a task as undone</li>
 *   <li>{@code delete INDEX} - Deletes a task</li>
 *   <li>{@code help} - Shows help information</li>
 *   <li>{@code bye} - Exits the application</li>
 * </ul>
 * <p>
 * Date format for input: {@code yyyy-MM-dd HHmm} (e.g., "2025-02-15 1400")<br>
 * Date format for output: {@code d MMM yyyy h:mma} (e.g., "15 Feb 2025 2:00PM")
 */
public class Parser {
    // Command names
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_LIST = "list";
    private static final String CMD_HELP = "help";
    private static final String CMD_FIND = "find";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_BYE = "bye";

    // Date format patterns
    private static final String INPUT_DATE_PATTERN = "yyyy-MM-dd HHmm";
    private static final String OUTPUT_DATE_PATTERN = "d MMM yyyy h:mma";

    // Command argument counts
    private static final int SINGLE_COMMAND_SIZE = 1;
    private static final int TWO_ARGUMENT_COMMAND_SIZE = 2;

    private final Ui ui;

    /**
     * Creates a new Parser with the specified UI for error message generation.
     *
     * @param ui the UI component used to generate error messages
     */
    public Parser(Ui ui) {
        this.ui = ui;
    }

    /**
     * Parses and formats a date/time string from input format to display format.
     * <p>
     * Converts from {@code yyyy-MM-dd HHmm} to {@code d MMM yyyy h:mma}.
     *
     * @param dateTime the date/time string in input format (e.g., "2025-02-15 1400")
     * @return the formatted date/time string (e.g., "15 Feb 2025 2:00PM")
     * @throws DateTimeException if the date/time string cannot be parsed
     */
    private String parseDateTime(String dateTime) {
        DateTimeFormatter presentable = DateTimeFormatter.ofPattern(OUTPUT_DATE_PATTERN);
        DateTimeFormatter readableDate = DateTimeFormatter.ofPattern(INPUT_DATE_PATTERN);

        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, readableDate);
        return parsedDateTime.format(presentable);
    }

    /**
     * Checks if the command is an add command (todo, deadline, or event).
     *
     * @param command the command string to check
     * @return {@code true} if the command is an add command, {@code false} otherwise
     */
    private boolean isAddCommand(String command) {
        return command.equals(CMD_TODO) ||
                command.equals(CMD_DEADLINE) ||
                command.equals(CMD_EVENT);
    }

    /**
     * Checks if the command is a todo command.
     *
     * @param todo the command string to check
     * @return {@code true} if the command is "todo", {@code false} otherwise
     */
    private boolean isToDo(String todo) {
        return todo.equals(CMD_TODO);
    }

    /**
     * Checks if the command is a deadline command.
     *
     * @param deadline the command string to check
     * @return {@code true} if the command is "deadline", {@code false} otherwise
     */
    private boolean isDeadline(String deadline) {
        return deadline.equals(CMD_DEADLINE);
    }

    /**
     * Processes a todo command and creates an AddCommand with a Todo task.
     * <p>
     * Expected format: {@code todo DESCRIPTION}
     *
     * @param task the parsed command tokens
     * @return an AddCommand with a Todo, or an ErrorCommand if validation fails
     */
    private Command processToDoCommand(List<String> task) {
        assert task.get(0).equals(CMD_TODO);

        String todo = String.join(" ", task.subList(1, task.size()));

        if (todo.isBlank()) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.MISSING_TASK));
        }

        return new AddCommand(new Todo(todo));
    }

    /**
     * Processes a deadline command and creates an AddCommand with a Deadline task.
     * <p>
     * Expected format: {@code deadline DESCRIPTION /by DATE}
     *
     * @param task the parsed command tokens
     * @return an AddCommand with a Deadline, or an ErrorCommand if validation or parsing fails
     */
    private Command processDeadlineCommand(List<String> task) {
        assert task.get(0).equals(CMD_DEADLINE);

        try {
            int i = task.indexOf("/by");
            if (i < 0 || task.size() - 1 == i) {
                return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.DEADLINE));
            }

            String rawDeadline = String.join(" ", task.subList(i + 1, task.size()));
            String readableDeadline = parseDateTime(rawDeadline);

            String deadlineTask = String.join(" ", task.subList(1, i));

            if (deadlineTask.isBlank()) {
                return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.MISSING_TASK));
            }

            return new AddCommand(new Deadline(deadlineTask, readableDeadline));
        } catch (DateTimeException d) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.UNREADABLE_DATE));
        }
    }

    /**
     * Validates that the from date/time comes before or equals the to date/time.
     *
     * @param rawFrom the raw from date string in format "yyyy-MM-dd HHmm"
     * @param rawTo the raw to date string in format "yyyy-MM-dd HHmm"
     * @return {@code true} if from is before or equal to to, {@code false} otherwise
     * @throws DateTimeException if either date string cannot be parsed
     */
    private boolean isValidDateOrder(String rawFrom, String rawTo) {
        DateTimeFormatter readableDate = DateTimeFormatter.ofPattern(INPUT_DATE_PATTERN);

        LocalDateTime fromDateTime = LocalDateTime.parse(rawFrom, readableDate);
        LocalDateTime toDateTime = LocalDateTime.parse(rawTo, readableDate);

        return fromDateTime.isBefore(toDateTime) || fromDateTime.equals(toDateTime);
    }

    /**
     * Processes an event command and creates an AddCommand with an Event task.
     * <p>
     * Expected format: {@code event DESCRIPTION /from DATE /to DATE}
     *
     * @param task the parsed command tokens
     * @return an AddCommand with an Event, or an ErrorCommand if validation or parsing fails
     */
    private Command processEventCommand(List<String> task) {
        assert task.get(0).equals(CMD_EVENT);

        try {
            int indexFrom = task.indexOf("/from");
            int indexTo = task.indexOf("/to");

            if (indexFrom + 1 == indexTo || indexTo == task.size() - 1 ||
                    indexTo < indexFrom || indexFrom < 0) {
                return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.EVENT));
            }
            String eventTask = String.join(" ", task.subList(1, indexFrom));
            String rawFrom = String.join(" ", task.subList(indexFrom + 1, indexTo));
            String rawTo = String.join(" ", task.subList(indexTo + 1, task.size()));

            String readableFrom = parseDateTime(rawFrom);
            String readableTo = parseDateTime(rawTo);

            if (!isValidDateOrder(rawFrom, rawTo)) {
                return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.INVALID_DATE_ORDER));
            }

            if (eventTask.isBlank()) {
                return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.MISSING_TASK));
            }

            return new AddCommand(new Event(eventTask, readableFrom, readableTo));
        } catch (DateTimeException d) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.UNREADABLE_DATE));
        }
    }

    /**
     * Processes an add command by delegating to the appropriate task-specific handler.
     *
     * @param task the parsed command tokens
     * @return the appropriate Command based on the task type
     */
    private Command processAddCommand(List<String> task) {
        assert task.get(0).equals(CMD_TODO) ||
                task.get(0).equals(CMD_DEADLINE) ||
                task.get(0).equals(CMD_EVENT);

        if (isToDo(task.get(0))) {
            return processToDoCommand(task);
        }

        if (isDeadline(task.get(0))) {
            return processDeadlineCommand(task);
        }

        return processEventCommand(task);
    }

    /**
     * Checks if the command is a list command.
     *
     * @param command the command string to check
     * @return {@code true} if the command is "list", {@code false} otherwise
     */
    private boolean isListCommand(String command) {
        return command.equals(CMD_LIST);
    }

    /**
     * Processes a list command and creates a ListCommand.
     * <p>
     * Expected format: {@code list} (no additional arguments)
     *
     * @param task the parsed command tokens
     * @return a ListCommand, or an ErrorCommand if extra arguments are provided
     */
    private Command processListCommand(List<String> task) {
        if (task.size() > SINGLE_COMMAND_SIZE) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.DEFAULT));
        }

        return new ListCommand();
    }

    /**
     * Processes a help command and creates a HelpCommand.
     * <p>
     * Expected format: {@code help} (no additional arguments)
     *
     * @param task the parsed command tokens
     * @return a HelpCommand, or an ErrorCommand if extra arguments are provided
     */
    private Command processHelpCommand(List<String> task) {
        if (task.size() > SINGLE_COMMAND_SIZE) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.DEFAULT));
        }

        return new HelpCommand();
    }

    /**
     * Checks if the command is an output command (list, help, or find).
     *
     * @param command the command string to check
     * @return {@code true} if the command is an output command, {@code false} otherwise
     */
    private boolean isOutputCommand(String command) {
        return command.equals(CMD_LIST) ||
                command.equals(CMD_HELP) ||
                command.equals(CMD_FIND);
    }

    /**
     * Checks if the command is a find command.
     *
     * @param command the command string to check
     * @return {@code true} if the command is "find", {@code false} otherwise
     */
    private boolean isFindCommand(String command) {
        return command.equals(CMD_FIND);
    }

    /**
     * Processes a find command and creates a FindCommand.
     * <p>
     * Expected format: {@code find KEYWORD}
     *
     * @param task the parsed command tokens
     * @return a FindCommand with the keyword, or an ErrorCommand if format is invalid
     */
    private Command processFindCommand(List<String> task) {
        assert task.get(0).equals(CMD_FIND);
        if (task.size() != TWO_ARGUMENT_COMMAND_SIZE) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.FIND));
        }

        return new FindCommand(task.get(1));
    }

    /**
     * Processes an output command by delegating to the appropriate handler.
     *
     * @param task the parsed command tokens
     * @return the appropriate Command based on the output type
     */
    private Command processOutputCommand(List<String> task) {
        assert task.get(0).equals(CMD_LIST) ||
                task.get(0).equals(CMD_HELP) ||
                task.get(0).equals(CMD_FIND);

        if (isListCommand(task.get(0))) {
            return processListCommand(task);
        }

        if (isFindCommand(task.get(0))) {
            return processFindCommand(task);
        }

        return processHelpCommand(task);
    }

    /**
     * Checks if the command is a mark command.
     *
     * @param command the command string to check
     * @return {@code true} if the command is "mark", {@code false} otherwise
     */
    private boolean isMarkCommand(String command) {
        return command.equals(CMD_MARK);
    }

    /**
     * Processes a mark command and creates a MarkCommand.
     * <p>
     * Expected format: {@code mark INDEX} where INDEX is a 1-based task number
     *
     * @param task the parsed command tokens
     * @param tasks the task list to retrieve the task from
     * @return a MarkCommand with the task, or an ErrorCommand if validation fails
     */
    private Command processMarkCommand(List<String> task, TaskList tasks) {
        if (task.size() != TWO_ARGUMENT_COMMAND_SIZE) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.MARK));
        }

        try {
            Task markTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
            return new MarkCommand(markTask);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.MARK));
        }
    }

    /**
     * Processes an unmark command and creates an UnmarkCommand.
     * <p>
     * Expected format: {@code unmark INDEX} where INDEX is a 1-based task number
     *
     * @param task the parsed command tokens
     * @param tasks the task list to retrieve the task from
     * @return an UnmarkCommand with the task, or an ErrorCommand if validation fails
     */
    private Command processUnmarkCommand(List<String> task, TaskList tasks) {
        if (task.size() != TWO_ARGUMENT_COMMAND_SIZE) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.UNMARK));
        }

        try {
            Task unmarkTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
            return new UnmarkCommand(unmarkTask);
        }catch (NumberFormatException | IndexOutOfBoundsException e) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.UNMARK));
        }
    }

    /**
     * Checks if the command is a marking command (mark or unmark).
     *
     * @param command the command string to check
     * @return {@code true} if the command is a marking command, {@code false} otherwise
     */
    private boolean isMarkingCommand(String command) {
        return command.equals(CMD_MARK) || command.equals(CMD_UNMARK);
    }

    /**
     * Processes a marking command by delegating to the appropriate handler.
     *
     * @param task the parsed command tokens
     * @param tasks the task list to retrieve the task from
     * @return the appropriate Command based on the marking type
     */
    private Command processMarkingCommand(List<String> task, TaskList tasks) {
        assert task.get(0).equals(CMD_MARK) || task.get(0).equals(CMD_UNMARK);

        if (isMarkCommand(task.get(0))) {
            return processMarkCommand(task, tasks);
        }

        return processUnmarkCommand(task, tasks);
    }

    /**
     * Checks if the command is a delete command.
     *
     * @param command the command string to check
     * @return {@code true} if the command is "delete", {@code false} otherwise
     */
    private boolean isDeleteCommand(String command) {
        return command.equals(CMD_DELETE);
    }

    /**
     * Processes a delete command and creates a DeleteCommand.
     * <p>
     * Expected format: {@code delete INDEX} where INDEX is a 1-based task number
     *
     * @param task the parsed command tokens
     * @param tasks the task list to retrieve the task from
     * @return a DeleteCommand with the task, or an ErrorCommand if validation fails
     */
    private Command processDeleteCommand(List<String> task, TaskList tasks) {
        if (task.size() != TWO_ARGUMENT_COMMAND_SIZE) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.DELETE));
        }

        try {
            Task deleteTask = tasks.get(Integer.parseInt(task.get(1)) - 1);
            return new DeleteCommand(deleteTask);
        }
        catch (NumberFormatException | IndexOutOfBoundsException e) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.DELETE));
        }
    }

    /**
     * Checks if the command is an exit command.
     *
     * @param command the command string to check
     * @return {@code true} if the command is "bye", {@code false} otherwise
     */
    private boolean isExitCommand(String command) {
        return command.equals(CMD_BYE);
    }

    /**
     * Processes an exit command and creates an ExitCommand.
     * <p>
     * Expected format: {@code bye} (no additional arguments)
     *
     * @param task the parsed command tokens
     * @return an ExitCommand, or an ErrorCommand if extra arguments are provided
     */
    private Command processExitCommand(List<String> task) {
        if (task.size() != SINGLE_COMMAND_SIZE) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.DEFAULT));
        }

        return new ExitCommand();
    }

    /**
     * Parses user input and returns the corresponding Command object.
     * <p>
     * This is the main entry point for parsing. It takes a raw user input string,
     * splits it into tokens, and delegates to appropriate processing methods based
     * on the command type.
     *
     * @param input the raw user input string
     * @param tasks the current task list (required for mark/unmark/delete commands)
     * @return the parsed Command object, or an ErrorCommand if the input is invalid
     */
    public Command parse(String input, TaskList tasks) {
        if (input.isBlank()) {
            return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.DEFAULT));
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

        return new ErrorCommand(this.ui.getErrorMsg(Ui.ErrorType.DEFAULT));
    }
}