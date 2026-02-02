package exceptions.machine;

/**
 * <h2>Невалиден символ</h2>
 *
 * <p>
 * Хвърля се когато символ не принадлежи на азбуката \(\\Sigma\) на автомата, но се използва
 * при добавяне на преход или при разпознаване на дума.
 * </p>
 */
public class InvalidSymbolException extends MachineException {
    /**
     * @param message описание на проблема
     */
    public InvalidSymbolException(String message) {
        super(message);
    }
}

