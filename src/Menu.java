import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private static Map<Integer, Runnable> commands = new HashMap<>();

    public static void initializeCommands() {
        commands.put(1, () -> System.out.println("thing 1"));
        commands.put(2, () -> System.out.println("thing 2"));
    }

    public static void printMenu() {
        System.out.println("Menu:");
        for (Map.Entry<Integer, Runnable> entry : commands.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
        commands.forEach((k,v)->System.out.println(k + ". " + v));
    }

    public static void executeCommand(int choice) {
        Runnable command = commands.getOrDefault(choice, () -> System.out.println("Invalid choice. Please choose again."));
        command.run();
    }
}
