package alfred.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;

class TaskListTest {

    @TempDir
    Path tempDir;

    private TaskList taskList;

    @BeforeEach
    void setUp() throws IOException {
        taskList = new TaskList(tempDir.toString());
    }

    @Test
    void add_validTask_increasesSize() {
        Todo todo = new Todo("Buy milk");
        taskList.add(todo);
        assertEquals(1, taskList.size());
    }

    @Test
    void add_multipleTasks_correctSize() {
        taskList.add(new Todo("Buy milk"));
        taskList.add(new Todo("Buy eggs"));
        taskList.add(new Deadline("Submit report", "15 Mar 2026 6:00PM"));
        assertEquals(3, taskList.size());
    }

    @Test
    void delete_existingTask_decreasesSize() {
        Todo todo = new Todo("Buy milk");
        taskList.add(todo);
        taskList.delete(todo);
        assertEquals(0, taskList.size());
    }

    @Test
    void get_validIndex_returnsCorrectTask() {
        Todo todo = new Todo("Buy milk");
        taskList.add(todo);
        assertEquals(todo, taskList.get(0));
    }

    @Test
    void mark_existingTask_marksTask() {
        Todo todo = new Todo("Buy milk");
        taskList.add(todo);
        Task marked = taskList.mark(todo);

        assertTrue(marked.isMarked());
    }

    @Test
    void unmark_markedTask_unmarksTask() {
        Todo todo = new Todo("Buy milk");
        taskList.add(todo);
        Task marked = taskList.mark(todo);
        Task unmarked = taskList.unmark(marked);

        assertFalse(unmarked.isMarked());
    }

    @Test
    void find_matchingKeyword_returnsMatchingTasks() {
        taskList.add(new Todo("Buy milk"));
        taskList.add(new Todo("Buy eggs"));
        taskList.add(new Todo("Sell milk"));

        TaskList found = taskList.find("milk");
        assertEquals(2, found.size());
    }

    @Test
    void find_noMatch_returnsEmptyList() {
        taskList.add(new Todo("Buy milk"));
        TaskList found = taskList.find("xyz");
        assertEquals(0, found.size());
    }

    @Test
    void save_tasksAdded_savesSuccessfully() throws IOException {
        taskList.add(new Todo("Buy milk"));
        assertDoesNotThrow(() -> taskList.save());
    }

    @Test
    void toString_multipleTasks_correctFormat() {
        taskList.add(new Todo("Buy milk"));
        taskList.add(new Deadline("Submit report", "15 Mar 2026 6:00PM"));

        String result = taskList.toString();
        assertTrue(result.contains("1. [T][ ] Buy milk"));
        assertTrue(result.contains("2. [D][ ] Submit report"));
    }
}