package alfred.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void constructor_validInputs_success() {
        Event event = new Event("Meeting", "20 Mar 2026 2:00PM", "20 Mar 2026 4:00PM");
        assertEquals("Meeting", event.getTask());
        assertEquals("20 Mar 2026 2:00PM", event.getFrom());
        assertEquals("20 Mar 2026 4:00PM", event.getTo());
    }

    @Test
    void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new Event(null, "20 Mar 2026 2:00PM", "20 Mar 2026 4:00PM"));
    }

    @Test
    void constructor_nullFrom_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new Event("Meeting", null, "20 Mar 2026 4:00PM"));
    }

    @Test
    void constructor_blankTo_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Event("Meeting", "20 Mar 2026 2:00PM", "   "));
    }

    @Test
    void mark_event_returnsMarkedEvent() {
        Event event = new Event("Meeting", "20 Mar 2026 2:00PM", "20 Mar 2026 4:00PM");
        Task marked = event.mark();

        assertTrue(marked.isMarked());
        assertEquals("Meeting", marked.getTask());
    }

    @Test
    void type_event_returnsEvent() {
        Event event = new Event("Meeting", "20 Mar 2026 2:00PM", "20 Mar 2026 4:00PM");
        assertEquals("event", event.type());
    }

    @Test
    void toString_event_correctFormat() {
        Event event = new Event("Meeting", "20 Mar 2026 2:00PM", "20 Mar 2026 4:00PM");
        String expected = "[E][ ] Meeting (from: 20 Mar 2026 2:00PM\n to: 20 Mar 2026 4:00PM)";
        assertEquals(expected, event.toString());
    }

    @Test
    void equals_sameEvent_returnsTrue() {
        Event e1 = new Event("Meeting", "20 Mar 2026 2:00PM", "20 Mar 2026 4:00PM");
        Event e2 = new Event("Meeting", "20 Mar 2026 2:00PM", "20 Mar 2026 4:00PM");
        assertEquals(e1, e2);
    }

    @Test
    void equals_differentTime_returnsFalse() {
        Event e1 = new Event("Meeting", "20 Mar 2026 2:00PM", "20 Mar 2026 4:00PM");
        Event e2 = new Event("Meeting", "20 Mar 2026 3:00PM", "20 Mar 2026 4:00PM");
        assertNotEquals(e1, e2);
    }
}