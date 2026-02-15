package alfred.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {

    @Test
    void constructor_validInputs_success() {
        Deadline deadline = new Deadline("Submit report", "15 Mar 2026 6:00PM");
        assertEquals("Submit report", deadline.getTask());
        assertEquals("15 Mar 2026 6:00PM", deadline.getDeadline());
    }

    @Test
    void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new Deadline(null, "15 Mar 2026 6:00PM"));
    }

    @Test
    void constructor_nullDeadline_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new Deadline("Submit report", null));
    }

    @Test
    void constructor_blankDeadline_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Deadline("Submit report", "   "));
    }

    @Test
    void mark_deadline_returnsMarkedDeadline() {
        Deadline deadline = new Deadline("Submit report", "15 Mar 2026 6:00PM");
        Task marked = deadline.mark();

        assertTrue(marked.isMarked());
        assertEquals("Submit report", marked.getTask());
    }

    @Test
    void type_deadline_returnsDeadline() {
        Deadline deadline = new Deadline("Submit report", "15 Mar 2026 6:00PM");
        assertEquals("deadline", deadline.type());
    }

    @Test
    void toString_deadline_correctFormat() {
        Deadline deadline = new Deadline("Submit report", "15 Mar 2026 6:00PM");
        assertEquals("[D][ ] Submit report (by: 15 Mar 2026 6:00PM)", deadline.toString());
    }

    @Test
    void equals_sameDeadline_returnsTrue() {
        Deadline d1 = new Deadline("Submit report", "15 Mar 2026 6:00PM");
        Deadline d2 = new Deadline("Submit report", "15 Mar 2026 6:00PM");
        assertEquals(d1, d2);
    }

    @Test
    void equals_differentDeadlineTime_returnsFalse() {
        Deadline d1 = new Deadline("Submit report", "15 Mar 2026 6:00PM");
        Deadline d2 = new Deadline("Submit report", "16 Mar 2026 6:00PM");
        assertNotEquals(d1, d2);
    }
}