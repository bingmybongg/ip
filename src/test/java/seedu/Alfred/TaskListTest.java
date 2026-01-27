package seedu.Alfred;

import Alfred.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void addTest() throws IOException {
        TaskList tempList = new TaskList(System.getProperty("user.home") + File.separator + "data");
        Task tempTask = new Todo("Clean the batmobile");
        tempList.add(tempTask);

        assertEquals("""
                Here's your list Sir
                1. [T][ ] Clean the batmobile
                
                """, tempList.toString());

    }

    @Test
    public void markTest() throws IOException {
        TaskList tempList = new TaskList(System.getProperty("user.home") + File.separator + "data");
        Task tempTask = new Todo("Clean the batmobile");
        tempList.add(tempTask);
        tempList.mark(tempTask);

        assertEquals("""
                Here's your list Sir
                1. [T][X] Clean the batmobile
                
                """, tempList.toString());
    }
}
