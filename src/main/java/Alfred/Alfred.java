package Alfred;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Alfred {
    private final Ui ui;

    Alfred(String path) throws IOException {
        this.ui = new Ui(new TaskList(path));
    }

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
