package alfred.command;

import alfred.task.TaskList;
import alfred.task.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class AddCommandTest {

    @TempDir
    Path tempDir;

    private TaskList taskList;

    @BeforeEach
    void setUp() throws IOException {
        taskList = new TaskList(tempDir.toString());
    }

    @Test
    void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    void execute_validTask_addsToList() {
        Todo todo = new Todo("Buy milk");
        AddCommand command = new AddCommand(todo);

        command.execute(taskList);

        assertEquals(1, taskList.size());
        assertEquals(todo, taskList.get(0));
    }

    @Test
    void execute_validTask_returnsSuccessMessage() {
        Todo todo = new Todo("Buy milk");
        AddCommand command = new AddCommand(todo);

        String result = command.execute(taskList);

        assertTrue(result.contains("Adding task"));
        assertTrue(result.contains("Buy milk"));
        assertTrue(result.contains("1 task(s)"));
    }

    @Test
    void equals_sameTask_returnsTrue() {
        Todo todo = new Todo("Buy milk");
        AddCommand cmd1 = new AddCommand(todo);
        AddCommand cmd2 = new AddCommand(todo);
        assertEquals(cmd1, cmd2);
    }

    @Test
    void equals_differentTask_returnsFalse() {
        AddCommand cmd1 = new AddCommand(new Todo("Buy milk"));
        AddCommand cmd2 = new AddCommand(new Todo("Buy eggs"));
        assertNotEquals(cmd1, cmd2);
    }
}