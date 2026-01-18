import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Alfred {
    private static void addTask(ArrayList<String> list, String x) {
        list.add(x);
        System.out.println("Adding task Master");
        System.out.println("added: " + x + "\n");
    }

    private static void markTask(ArrayList<String> list, int i) {
        if (!list.get(i).contains("#marked#")) {
            System.out.println("I have marked task " + (i + 1) + " effective immediately\n");
            list.set(i, list.get(i) + "#marked#");
        } else {
            System.out.println("The task has already been marked Master\n");
        }
    }

    private static void unmarkTask(ArrayList<String> list, int i) {
        if (list.get(i).contains("#marked#")) {
            System.out.println("I have unmarked task " + (i + 1) + " effective immediately\n");
            list.set(i, list.get(i).replace("#marked#", ""));
        } else {
            System.out.println("The task wasn't marked Master\n");
        }
    }

    private static void printList(ArrayList<String> list) {
        System.out.println("Here's your list Sir");
        list.forEach(x -> {
            String marked = "[ ] ";

            if (x.contains("#marked#")) {
                marked = "[X] ";
            }

            System.out.println(list.indexOf(x) +
                    1 +
                    "." +
                    marked +
                    x.replace("#marked#", ""));
        });
        System.out.println("\n");
    }

    public static void main(String[] args) {
        String initialGreeting = "Good morning Master!\nWhat do you need from me?\n";
        String endChat = "Good day Sir!\n";
        String separator = "What else do you need?\n";

        System.out.println(initialGreeting);
        ArrayList<String> list = new ArrayList<String>();

        while (true) {
            Scanner reader = new Scanner(System.in);
            String task = reader.nextLine();
            if (task.isBlank()) {
                System.out.println("Waiting for your command Sir\n");
                continue;
            }

            if (task.toLowerCase().contains("bye")) {
                reader.close();
                break;
            }
            if (task.equalsIgnoreCase("list")) {
                printList(list);
            } else if (task.toLowerCase().contains("mark")) {
                try {
                    String[] wholeRequest = task.split(" ");

                    if (Arrays.asList(wholeRequest).size() > 2) {
                        addTask(list,task);
                        continue;
                    }

                    String mainReq = wholeRequest[0].strip();
                    int i = Integer.parseInt(wholeRequest[1]) - 1;

                    if (mainReq.equals("mark")) {
                        markTask(list, i);
                    } else {
                        unmarkTask(list, i);
                    }
                }
                catch (NumberFormatException c) {
                    String[] wholeRequest = task.split(" ");

                    if (wholeRequest[0].equals("mark")) {
                        System.out.println("That's an invalid index Sir\n");
                    } else {
                        addTask(list, task);
                    }
                }
                catch (IndexOutOfBoundsException i) {
                    System.out.println("There isn't a task with that number Sir\n");
                }
            }
            else {
                addTask(list, task);
            }
            System.out.println(separator);
        }
        System.out.println(endChat);
    }
}
