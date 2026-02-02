package exceptions.machine;

/**
 * <h2>Невалидно състояние</h2>
 *
 * <p>
 * Хвърля се когато се подаде невалидно/несъществуващо състояние при операция върху автомат
 * (например стартово/крайно състояние, или преход с възел, който не е част от множеството \(S\)).
 * </p>
 */
public class InvalidStateException extends MachineException {
    /**
     * @param message описание на проблема
     */
    public InvalidStateException(String message) {
        super(message);
    }
}

