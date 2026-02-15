package alfred.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    @Test
    void constructor_validDescription_success() {
        Todo todo = new Todo("Buy milk");
        assertEquals("Buy milk", todo.getTask());
        assertFalse(todo.isMarked());
    }

    @Test
    void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Todo(null));
    }

    @Test
    void constructor_blankDescription_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Todo("   "));
    }

    @Test
    void mark_todoTask_returnsMarkedTodo() {
        Todo todo = new Todo("Buy milk");
        Task markedTodo = todo.mark();

        assertTrue(markedTodo.isMarked());
        assertEquals("Buy milk", markedTodo.getTask());
        assertFalse(todo.isMarked()); // Original unchanged (immutability)
    }

    @Test
    void unmark_markedTodo_returnsUnmarkedTodo() {
        Todo todo = new Todo("Buy milk");
        Task markedTodo = todo.mark();
        Task unmarkedTodo = markedTodo.unmark();

        assertFalse(unmarkedTodo.isMarked());
        assertEquals("Buy milk", unmarkedTodo.getTask());
    }

    @Test
    void type_todoTask_returnsTodo() {
        Todo todo = new Todo("Buy milk");
        assertEquals("todo", todo.type());
    }

    @Test
    void toString_unmarkedTodo_correctFormat() {
        Todo todo = new Todo("Buy milk");
        assertEquals("[T][ ] Buy milk", todo.toString());
    }

    @Test
    void toString_markedTodo_correctFormat() {
        Todo todo = new Todo("Buy milk");
        Task marked = todo.mark();
        assertEquals("[T][X] Buy milk", marked.toString());
    }

    @Test
    void equals_sameTodo_returnsTrue() {
        Todo todo1 = new Todo("Buy milk");
        Todo todo2 = new Todo("Buy milk");
        assertEquals(todo1, todo2);
    }

    @Test
    void equals_differentDescription_returnsFalse() {
        Todo todo1 = new Todo("Buy milk");
        Todo todo2 = new Todo("Buy eggs");
        assertNotEquals(todo1, todo2);
    }

    @Test
    void equals_differentMarkedState_returnsFalse() {
        Todo todo1 = new Todo("Buy milk");
        Todo todo2 = (Todo) new Todo("Buy milk").mark();
        assertNotEquals(todo1, todo2);
    }
}