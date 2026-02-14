package alfred.ui;

import java.io.IOException;

public class Ui {
    private static final String DEFAULT_ERROR_MSG = "I'm not sure what you're saying sir\n" +
                                                    "Type 'help' if you need it\n";
    private static final String MISSING_TASK_ERROR_MSG = "You're missing your task Sir\n";

    private static final String UNREADABLE_DATE_ERROR_MSG = """
                                                            I can't read your date Sir
                                                            (Eg: 1999-02-26 1801)
                                                            """;

    private static final String DEFAULT_DEADLINE_ERROR_MSG = """
                                                             I didn't get your deadline Sir
                                                             (Eg: deadline CLEAN THE BATMOBILE /by yyyy-MM-dd HHmm)
                                                             """;

    private static final String DEFAULT_EVENT_ERROR_MSG = """
                                        I didn't get your event timing Sir
                                        (Eg: event CLEAN THE BATMOBILE /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm)
                                        """;

    private static final String DEFAULT_MARK_ERROR_MSG = """
                                                         Check the list to choose which task to mark Sir
                                                         (Eg: mark #)
                                                         """;

    private static final String DEFAULT_UNMARK_ERROR_MSG = """
                                                           Check the list to choose which task to unmark Sir
                                                           (Eg: unmark #)
                                                           """;

    private static final String DEFAULT_FIND_ERROR_MSG = """
                                                        You need to tell me a single keyword you want to find Sir
                                                        (Eg: find batmobile)
                                                        """;

    private static final String DEFAULT_DELETE_ERROR_MSG = """
                                                           Check the list to choose which task to delete Sir
                                                           (Eg: delete #)
                                                           """;

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

    public String getUnreadableDateErrorMsg() {
        return UNREADABLE_DATE_ERROR_MSG;
    }

    public String getDefaultDeadlineErrorMsg() {
        return DEFAULT_DEADLINE_ERROR_MSG;
    }

    public String getDefaultEventErrorMsg() {
        return DEFAULT_EVENT_ERROR_MSG;
    }

    public String getDefaultMarkErrorMsg() {
        return DEFAULT_MARK_ERROR_MSG;
    }

    public String getDefaultUnmarkErrorMsg() {
        return DEFAULT_UNMARK_ERROR_MSG;
    }

    public String getDefaultFindErrorMsg() {
        return DEFAULT_FIND_ERROR_MSG;
    }

    public String getDefaultDeleteErrorMsg() {
        return DEFAULT_DELETE_ERROR_MSG;
    }

    @Override
    public String toString() {
        return "Good morning Master!\nWhat do you need from me?\n";
    }
}
