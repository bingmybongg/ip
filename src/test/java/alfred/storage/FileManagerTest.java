package alfred.storage;

import alfred.task.Deadline;
import alfred.task.Event;
import alfred.task.Task;
import alfred.task.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void constructor_validPath_createsDirectory() throws IOException {
        FileManager fm = new FileManager(tempDir.toString());
        assertNotNull(fm);
    }

    @Test
    void constructor_nullPath_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new FileManager(null));
    }

    @Test
    void load_noFile_returnsEmptyList() throws IOException {
        FileManager fm = new FileManager(tempDir.toString());
        ArrayList<Task> tasks = fm.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void save_validTasks_savesSuccessfully() throws IOException {
        FileManager fm = new FileManager(tempDir.toString());
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Buy milk"));
        tasks.add(new Deadline("Submit report", "15 Mar 2026 6:00PM"));

        assertDoesNotThrow(() -> fm.save(tasks));
    }

    @Test
    void saveAndLoad_multipleTasks_preservesData() throws IOException {
        FileManager fm = new FileManager(tempDir.toString());

        // Save tasks
        ArrayList<Task> original = new ArrayList<>();
        original.add(new Todo("Buy milk"));
        original.add(new Deadline("Submit report", "15 Mar 2026 6:00PM"));
        original.add(new Event("Meeting", "20 Mar 2026 2:00PM", "20 Mar 2026 4:00PM"));
        fm.save(original);

        // Load tasks
        ArrayList<Task> loaded = fm.load();

        assertEquals(3, loaded.size());
        assertEquals("Buy milk", loaded.get(0).getTask());
        assertEquals("Submit report", loaded.get(1).getTask());
        assertEquals("Meeting", loaded.get(2).getTask());
    }

    @Test
    void saveAndLoad_markedTasks_preservesMarkedState() throws IOException {
        FileManager fm = new FileManager(tempDir.toString());

        // Save marked task
        ArrayList<Task> original = new ArrayList<>();
        Task markedTodo = new Todo("Buy milk").mark();
        original.add(markedTodo);
        fm.save(original);

        // Load and verify
        ArrayList<Task> loaded = fm.load();
        assertTrue(loaded.get(0).isMarked());
    }

    @Test
    void save_taskWithCommas_handlesCorrectly() throws IOException {
        FileManager fm = new FileManager(tempDir.toString());

        ArrayList<Task> original = new ArrayList<>();
        original.add(new Todo("Buy milk, eggs, and bread"));
        fm.save(original);

        ArrayList<Task> loaded = fm.load();
        assertEquals("Buy milk, eggs, and bread", loaded.get(0).getTask());
    }
}