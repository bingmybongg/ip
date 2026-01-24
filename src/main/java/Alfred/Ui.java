package Alfred;

import java.io.IOException;

public class Ui {
    private final TaskList tasks;

    Ui(TaskList tasks) {
        this.tasks = tasks;
    }

    public boolean readInput(String input) throws IOException {
        Pair<String, Task> action = Parser.parse(input, this.tasks);

        switch (action.t()) {
            case ("add"): {
                System.out.println("Adding task for you Sir\n");
                this.tasks.add(action.u());

                int i = tasks.size();
                System.out.println("I have successfully added the task for you Sir:");
                System.out.println("   " + action.u());
                System.out.println("You currently have " + i + " task(s) at the moment\n");
                return true;
            }
            case ("delete"): {
                System.out.println("Deleting task for you Sir\n");

                this.tasks.delete(action.u());
                System.out.println("I have successfully deleted the task for you Sir:");
                System.out.println("   " + action.u());
                System.out.println("You currently have " + this.tasks.size() + " task(s) at the moment\n");

                return true;
            }
            case ("read"): {
                this.tasks.read();
                return true;
            }
            case ("mark"): {
                System.out.println("Marking task for you Sir\n");

                this.tasks.mark(action.u());
                System.out.println("I have successfully marked the task for you Sir:");
                System.out.println("   " + action.u());
                return true;
            }
            case ("unmark"): {
                System.out.println("Unmarking task for you Sir\n");
                this.tasks.unmark(action.u());
                System.out.println("I have successfully marked the task for you Sir:");
                System.out.println("   " + action.u());
                return true;
            }
            case ("exit"): {
                this.tasks.save();
                return false;
            }
            default: {
                this.tasks.unknown(action.t());
                return true;
            }
        }
    }

    public String toString() {
        return "Good morning Master!\nWhat do you need from me?\n";
    }
}
