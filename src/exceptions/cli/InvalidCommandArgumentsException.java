package exceptions.cli;

public class InvalidCommandArgumentsException extends CliException {
    public InvalidCommandArgumentsException(String message) {
        super(message);
    }
}
