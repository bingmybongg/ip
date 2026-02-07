package Alfred;

import java.io.IOException;

public class Ui {
    Ui() {
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

    @Override
    public String toString() {
        return "Good morning Master!\nWhat do you need from me?\n";
    }
}
