package alfred.ui;

/**
 * Manages user interface interactions and message generation.
 * <p>
 * This class is responsible for providing greeting messages, error messages,
 * and formatting responses for display to the user. It centralizes all
 * user-facing text to make the application easier to maintain and localize.
 */
public class Ui {
    private static final String GREETING = "Good morning Master!\nWhat do you need from me?\n";

    /**
     * Represents the different types of errors that can occur in the application.
     * <p>
     * Each error type has an associated user-friendly message that explains
     * what went wrong and provides an example of correct usage.
     */
    public enum ErrorType {
        /** Generic error for unrecognized commands */
        DEFAULT("I'm not sure what you're saying sir\nType 'help' if you need it\n"),

        /** Error when task description is missing */
        MISSING_TASK("You're missing your task Sir\n(Eg: todo CLEAN THE BATMOBILE)\n"),

        /** Error when date/time format cannot be parsed */
        UNREADABLE_DATE("I can't read your date Sir\n(Eg: 1999-02-26 1801)\n"),

        /** Error when deadline command is malformed */
        DEADLINE("I didn't get your deadline Sir\n(Eg: deadline CLEAN THE BATMOBILE /by yyyy-MM-dd HHmm)\n"),

        /** Error when event command is malformed */
        EVENT("I didn't get your event timing Sir\n(Eg: event CLEAN THE BATMOBILE /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm)\n"),

        /** Error when mark command has invalid task index */
        MARK("Check the list to choose which task to mark Sir\n(Eg: mark #)\n"),

        /** Error when unmark command has invalid task index */
        UNMARK("Check the list to choose which task to unmark Sir\n(Eg: unmark #)\n"),

        /** Error when find command is missing keyword or has too many arguments */
        FIND("You need to tell me a single keyword you want to find Sir\n(Eg: find batmobile)\n"),

        /** Error when delete command has invalid task index */
        DELETE("Check the list to choose which task to delete Sir\n(Eg: delete #)\n"),

        /** Error when event's start date is after its end date */
        INVALID_DATE_ORDER("Your event's start date cannot be after its end date Sir\n");

        private final String message;

        /**
         * Creates an ErrorType with the specified error message.
         *
         * @param message the user-friendly error message
         */
        ErrorType(String message) {
            this.message = message;
        }

        /**
         * Returns the error message associated with this error type.
         *
         * @return the error message string
         */
        public String getMessage() {
            return this.message;
        }
    }

    /**
     * Creates a new Ui instance.
     * <p>
     * The UI manages all user-facing text and message formatting.
     */
    public Ui() {
    }

    /**
     * Returns the response for a given action.
     * <p>
     * Currently, this method simply returns the action string as-is.
     * This serves as a pass-through for command execution results.
     *
     * @param action the action result to process (must not be {@code null})
     * @return the response string
     * @throws NullPointerException if action is null
     */
    public String getResponse(String action) {
        if (action == null) {
            throw new NullPointerException("Action cannot be null");
        }
        return action;
    }

    /**
     * Retrieves the error message for a specific error type.
     * <p>
     * The error message includes a description of what went wrong and
     * an example of correct usage.
     *
     * @param errorType the type of error (must not be {@code null})
     * @return the corresponding user-friendly error message
     * @throws NullPointerException if errorType is null
     */
    public String getErrorMsg(ErrorType errorType) {
        if (errorType == null) {
            throw new NullPointerException("ErrorType cannot be null");
        }
        return errorType.getMessage();
    }

    /**
     * Returns the greeting message displayed when the application starts.
     * <p>
     * The greeting welcomes the user and prompts them for input.
     *
     * @return the greeting message
     */
    @Override
    public String toString() {
        return GREETING;
    }

    /**
     * Compares this UI to another object for equality.
     * <p>
     * All UI instances are considered equal since they have no state.
     *
     * @param other the object to compare with
     * @return {@code true} if the other object is a Ui, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof Ui;
    }
}