package alfred.ui;

import java.io.IOException;

public class Ui {
    private static final String DEFAULT_ERROR_MSG = "I'm not sure what you're saying sir\n" +
                                                    "Type 'help' if you need it\n";
    private static final String MISSING_TASK_ERROR_MSG = "You're missing your task Sir\n";

    public Ui() {
    }

    /**
     * This method will read the input from the user, parse the input into an actionable
     * command and run different methods based on the input. After which, it will return true
     * if the chatbot should continue running and false if the user wants to exit
     * @return True if the program should not exit False if the program should exit
     */
    public String getResponse(String action) throws IOException {
        return action;
    }

    public String getDefaultErrorMsg() {
        return DEFAULT_ERROR_MSG;
    }

    public String getMissingTaskErrorMsg() {
        return MISSING_TASK_ERROR_MSG;
    }

    @Override
    public String toString() {
        return "Good morning Master!\nWhat do you need from me?\n";
    }
}
