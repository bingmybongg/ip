package seedu.Alfred;

import Alfred.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void addTest() throws IOException {
        TaskList tempList = new TaskList(System.getProperty("user.home") + File.separator + "data");
        Pair<String, Task> add = Parser.parse("todo Clean Batmobile", tempList);
        assertEquals(new Pair<String, Task>("add", new Todo("Clean Batmobile")), add);
    }

    @Test
    public void wrongInputTest() throws IOException {
        TaskList tempList = new TaskList(System.getProperty("user.home") + File.separator + "data");
        Pair<String, Task> wrong = Parser.parse("Clean Batmobile", tempList);
        assertEquals(new Pair<String, Task>("I'm not sure what you're saying sir\n",null), wrong);
    }
}
