import java.util.Scanner;
import java.util.ArrayList;

public class Alfred {
    public static void main(String[] args) {
        String initialGreeting = "Good morning Master!\nWhat do you need from me?\n";
        String endChat = "Good day Sir!\n";

        System.out.println(initialGreeting);
        ArrayList<String> list = new ArrayList<String>();

        while (true) {
            Scanner reader = new Scanner(System.in);
            String readInput = reader.nextLine();
            String separator = "What else do you need?\n";

            if (readInput.toLowerCase().contains("bye")) {
                reader.close();
                break;
            }

            if (readInput.equalsIgnoreCase("list")) {
                System.out.println("Here's your list Sir");
                list.forEach(x -> System.out.println(list.indexOf(x) + 1 + ". " + x));
                System.out.println("\n" + separator);
            } else {
                list.add(readInput);
                System.out.println("\nadded: " + readInput + "\n" + separator);
            }
        }
        System.out.println(endChat);
    }
}
