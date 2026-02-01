package Alfred;

import java.io.IOException;

public class Ui {
    private final TaskList tasks;

    Ui(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * This method will read the input from the user, parse the input into an actionable
     * command and run different methods based on the input. Afterwhich, it will return true
     * if the chatbot should continue running and false if the user wants to exit
     * @param input
     * @return True if the program should not exit False if the program should exit
     */
    public String getResponse(String input) throws IOException {
        Pair<String, Task> action = Parser.parse(input, this.tasks);
        String res = "";
        switch (action.t()) {
        case ("add"): {
            res += "Adding task for you Sir\n\n";
            this.tasks.add(action.u());

            int i = tasks.size();
            res += "I have successfully added the task for you Sir:\n";
            res += "   " + action.u() + "\n";
            res += "You currently have " + i + " task(s) at the moment\n\n";
            return res;
        }
        case ("delete"): {
            res += "Deleting task for you Sir\n\n";

            this.tasks.delete(action.u());
            res += "I have successfully deleted the task for you Sir:\n";
            res += "   " + action.u() + "\n";
            res += "You currently have " + this.tasks.size() + " task(s) at the moment\n\n";

            return res;
        }
        case ("read"): {
            res += "Here's your list Sir\n";
            res += this.tasks + "\n";
            return res;
        }
        case ("mark"): {
            res += "Marking task for you Sir\n\n";
            Task newTask = this.tasks.mark(action.u());
            res += "I have successfully marked the task for you Sir:\n";
            res += "   " + newTask + "\n";
            return res;
        }
        case ("unmark"): {
            res += "Unmarking task for you Sir\n\n";
            Task newTask = this.tasks.unmark(action.u());
            res += "I have successfully marked the task for you Sir:\n";
            res += "   " + newTask + "\n";
            return res;
        }
        case("find"): {
            String keyword = action.u().getTask();
            res += "Finding the tasks containing '" + keyword + "' for you Sir\n\n";
            TaskList found = this.tasks.find(keyword);
            res += "Here are the matching tasks in your list Sir:\n";
            res += found + "\n";
            return res;
        }
        case ("exit"): {
            this.tasks.save();
            return "Goodbye Sir!\n";
        }
        default: {
            return this.tasks.unknown(action.t());
        }
        }
    }

    @Override
    public String toString() {
        return "Good morning Master!\nWhat do you need from me?\n";
    }
}
