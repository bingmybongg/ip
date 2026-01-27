package Alfred;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Alfred {
    private final Ui ui;

    Alfred(String path) throws IOException {
        this.ui = new Ui(new TaskList(path));
    }

    /**
     * This is the main method that will run the show for the Alfred Chatbot
     * <p>
     * It will begin scanning for input from the user. This Chatbot has 7 commands
     * <p>
     * 1. todo (To add a TODO task for the Chatbot)
     * </p>
     *     Sample input:
     *     <p>
     *     Good morning Master!
     *     What do you need from me?
     *     </p>
     *     todo Clean the batmobile
     * <p>
     * 2. deadline (To add a DEADLINE task when the user has a deadline)
     * </p>
     *     Sample input:
     *     <p>
     *     Good morning Master!
     *     What do you need from me?
     *     </p>
     *     deadline Clean the batmobile /by 2026-01-28 2359
     * <p>
     * 3. event (To add a EVENT task when the user has an event from a certain time to a certain time)
     * </p>
     *     Sample input:
     *     <p>
     *     Good morning Master!
     *     What do you need from me?
     *     </p>
     *     event Clean the batmobile /from 2026-01-28 2300 /to 2026-01-28 2359
     * <p>
     * 4. list (To list all your tasks)
     * </p>
     * 5. mark (To mark a task based on the index)
     * <p>
     *     Sample input:
     *     </p>
     *     Good morning Master!
     *     What do you need from me?
     *     <p>
     *     mark 1
     * </p>
     * 6. unmark (To unmark a task based on the index)
     * <p>
     * 7. delete (To delete a task based on the index)
     * </p>
     * 8. bye (To exit the program)
     */

    public void run() throws IOException {
        System.out.println(this.ui);
        Scanner input = new Scanner(System.in);

        while (input.hasNextLine()) {
            String readInput = input.nextLine().trim();

            boolean res = this.ui.readInput(readInput);

            if (!res) {
                input.close();
                break;
            }

            System.out.println("What else do you need?\n");
        }

        System.out.println("Goodbye Sir!\n");
    }

    public static void main(String[] args) throws IOException {
        String path = System.getProperty("user.home") + File.separator + "data";
        new Alfred(path).run();
    }
}
