import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private static CommandHandler instance;
    private static Map<Integer, ICommand> commands;

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
        IParameterizedCommand ipzc = System.out::printf;
        IParameterlessCommand iplc = () -> System.out.println("thing 2");
        commands.put(1, ipzc);
        commands.put(2, iplc);
    }

    public void executeCommand(int choice) {
        ICommand command = commands.getOrDefault(choice, (IParameterlessCommand)(() -> System.out.println("Invalid choice. Please choose again.")));
        ((IParameterlessCommand)command).execute();
    }

    public void executeCommand(int choice, String parameter) {
        ICommand command = commands.getOrDefault(choice, (IParameterlessCommand)(() -> System.out.println("Invalid choice. Please choose again.")));
        ((IParameterizedCommand)command).execute(parameter);
    }
}
