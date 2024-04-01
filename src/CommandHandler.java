import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private static CommandHandler instance;
    private static Map<Integer, Runnable> commands;

    private CommandHandler() {
        commands = new HashMap<>();
    }

    public static CommandHandler getInstance() {
        if (instance == null) {
            instance = new CommandHandler();
        }
        return instance;
    }

    public void initializeCommands() {
        commands.put(1, () -> System.out.println("thing 1"));
        commands.put(2, () -> System.out.println("thing 2"));
    }

    public void executeCommand(int choice) {
        Runnable command = commands.getOrDefault(choice, () -> System.out.println("Invalid choice. Please choose again."));
        command.run();
    }
}
