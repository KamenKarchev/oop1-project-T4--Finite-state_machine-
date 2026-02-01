package exceptions.cli;

public class UnknownCommandTokenException extends CliException {
    public UnknownCommandTokenException(String message) {
        super(message);
    }
}
