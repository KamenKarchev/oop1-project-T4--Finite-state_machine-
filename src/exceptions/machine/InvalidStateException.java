package exceptions.machine;

public class InvalidStateException extends MachineException {
    public InvalidStateException(String message) {
        super(message);
    }
}

