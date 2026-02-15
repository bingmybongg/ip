package alfred.parser;

import alfred.command.*;
import alfred.task.TaskList;
import alfred.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @TempDir
    Path tempDir;

    private Parser parser;
    private TaskList taskList;
    private Ui ui;

    @BeforeEach
    void setUp() throws IOException {
        ui = new Ui();
        parser = new Parser(ui);
        taskList = new TaskList(tempDir.toString());
    }

    @Test
    void parse_todoCommand_returnsAddCommand() {
        Command command = parser.parse("todo Buy milk", taskList);
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void parse_deadlineCommand_returnsAddCommand() {
        Command command = parser.parse("deadline Submit report /by 2026-03-15 1800", taskList);
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void parse_eventCommand_returnsAddCommand() {
        Command command = parser.parse("event Meeting /from 2026-03-20 1400 /to 2026-03-20 1600", taskList);
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void parse_listCommand_returnsListCommand() {
        Command command = parser.parse("list", taskList);
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void parse_findCommand_returnsFindCommand() {
        Command command = parser.parse("find milk", taskList);
        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    void parse_markCommand_returnsMarkCommand() {
        taskList.add(new alfred.task.Todo("Buy milk"));
        Command command = parser.parse("mark 1", taskList);
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    void parse_deleteCommand_returnsDeleteCommand() {
        taskList.add(new alfred.task.Todo("Buy milk"));
        Command command = parser.parse("delete 1", taskList);
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void parse_byeCommand_returnsExitCommand() {
        Command command = parser.parse("bye", taskList);
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    void parse_helpCommand_returnsHelpCommand() {
        Command command = parser.parse("help", taskList);
        assertInstanceOf(HelpCommand.class, command);
    }

    @Test
    void parse_blankInput_returnsErrorCommand() {
        Command command = parser.parse("   ", taskList);
        assertInstanceOf(ErrorCommand.class, command);
    }

    @Test
    void parse_unknownCommand_returnsErrorCommand() {
        Command command = parser.parse("unknown", taskList);
        assertInstanceOf(ErrorCommand.class, command);
    }

    @Test
    void parse_todoWithoutDescription_returnsErrorCommand() {
        Command command = parser.parse("todo", taskList);
        assertInstanceOf(ErrorCommand.class, command);
    }

    @Test
    void parse_invalidDateFormat_returnsErrorCommand() {
        Command command = parser.parse("deadline Submit /by tomorrow", taskList);
        assertInstanceOf(ErrorCommand.class, command);
    }

    @Test
    void parse_eventWithInvalidDateOrder_returnsErrorCommand() {
        Command command = parser.parse("event Meeting /from 2026-03-20 1600 /to 2026-03-20 1400", taskList);
        assertInstanceOf(ErrorCommand.class, command);
    }
}