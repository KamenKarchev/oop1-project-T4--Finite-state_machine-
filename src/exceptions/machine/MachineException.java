package exceptions.machine;

public class MachineException extends Exception {
    public MachineException(String message) {
        super(message);
    }

    public MachineException(String message, Throwable cause) {
        super(message, cause);
    }
}

