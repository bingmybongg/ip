import java.util.Scanner;

public class Alfred {
    public static void main(String[] args) {
        String initialGreeting = "Good morning Master!\nWhat do you need from me?\n";
        String endChat = "Good day Sir!\n";

        System.out.println(initialGreeting);

        while (true) {
            Scanner reader = new Scanner(System.in);
            String readInput = reader.nextLine();
            String separator = "What ese do you need?\n";

            if (readInput.toLowerCase().contains("bye")) {
                reader.close();
                break;
            }
            System.out.println("\n" + readInput + "\n" + separator);
        }
        System.out.println(endChat);

    }
}
